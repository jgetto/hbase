/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hbase.security;

import java.io.IOException;
import java.net.InetAddress;
import java.security.PrivilegedExceptionAction;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.exceptions.ConnectionClosedException;
import org.apache.hadoop.hbase.ipc.FallbackDisallowedException;
import org.apache.hadoop.hbase.security.provider.SaslClientAuthenticationProvider;
import org.apache.hadoop.hbase.util.NettyFutureUtils;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.security.token.TokenIdentifier;
import org.apache.yetus.audience.InterfaceAudience;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.hbase.thirdparty.io.netty.buffer.ByteBuf;
import org.apache.hbase.thirdparty.io.netty.channel.ChannelHandlerContext;
import org.apache.hbase.thirdparty.io.netty.channel.ChannelPipeline;
import org.apache.hbase.thirdparty.io.netty.channel.SimpleChannelInboundHandler;
import org.apache.hbase.thirdparty.io.netty.util.concurrent.Promise;

/**
 * Implement SASL logic for netty rpc client.
 * @since 2.0.0
 */
@InterfaceAudience.Private
public class NettyHBaseSaslRpcClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

  private static final Logger LOG = LoggerFactory.getLogger(NettyHBaseSaslRpcClientHandler.class);

  public static final String HANDLER_NAME = "SaslRpcClientHandler";

  private final Promise<Boolean> saslPromise;

  private final UserGroupInformation ugi;

  private final NettyHBaseSaslRpcClient saslRpcClient;

  private final Configuration conf;

  // flag to mark if Crypto AES encryption is enable
  private boolean needProcessConnectionHeader = false;

  /**
   * @param saslPromise {@code true} if success, {@code false} if server tells us to fallback to
   *                    simple.
   */
  public NettyHBaseSaslRpcClientHandler(Promise<Boolean> saslPromise, UserGroupInformation ugi,
    SaslClientAuthenticationProvider provider, Token<? extends TokenIdentifier> token,
    InetAddress serverAddr, SecurityInfo securityInfo, boolean fallbackAllowed, Configuration conf)
    throws IOException {
    this.saslPromise = saslPromise;
    this.ugi = ugi;
    this.conf = conf;
    this.saslRpcClient = new NettyHBaseSaslRpcClient(conf, provider, token, serverAddr,
      securityInfo, fallbackAllowed, conf.get("hbase.rpc.protection",
        SaslUtil.QualityOfProtection.AUTHENTICATION.name().toLowerCase()));
  }

  private void writeResponse(ChannelHandlerContext ctx, byte[] response) {
    LOG.trace("Sending token size={} from initSASLContext.", response.length);
    NettyFutureUtils.safeWriteAndFlush(ctx,
      ctx.alloc().buffer(4 + response.length).writeInt(response.length).writeBytes(response));
  }

  private void tryComplete(ChannelHandlerContext ctx) {
    if (!saslRpcClient.isComplete()) {
      return;
    }

    saslRpcClient.setupSaslHandler(ctx.pipeline(), HANDLER_NAME);
    removeHandlers(ctx);

    setCryptoAESOption();

    saslPromise.setSuccess(true);
  }

  private void removeHandlers(ChannelHandlerContext ctx) {
    ChannelPipeline p = ctx.pipeline();
    p.remove(SaslChallengeDecoder.class);
    p.remove(this);
  }

  private void setCryptoAESOption() {
    boolean saslEncryptionEnabled = SaslUtil.QualityOfProtection.PRIVACY.getSaslQop()
      .equalsIgnoreCase(saslRpcClient.getSaslQOP());
    needProcessConnectionHeader =
      saslEncryptionEnabled && conf.getBoolean("hbase.rpc.crypto.encryption.aes.enabled", false);
  }

  public boolean isNeedProcessConnectionHeader() {
    return needProcessConnectionHeader;
  }

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) {
    // dispose the saslRpcClient when the channel is closed, since saslRpcClient is final, it is
    // safe to reference it in lambda expr.
    NettyFutureUtils.addListener(ctx.channel().closeFuture(), f -> saslRpcClient.dispose());
    try {
      byte[] initialResponse = ugi.doAs(new PrivilegedExceptionAction<byte[]>() {

        @Override
        public byte[] run() throws Exception {
          return saslRpcClient.getInitialResponse();
        }
      });
      if (initialResponse != null) {
        writeResponse(ctx, initialResponse);
      }
      tryComplete(ctx);
    } catch (Exception e) {
      // the exception thrown by handlerAdded will not be passed to the exceptionCaught below
      // because netty will remove a handler if handlerAdded throws an exception.
      exceptionCaught(ctx, e);
    }
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
    int len = msg.readInt();
    if (len == SaslUtil.SWITCH_TO_SIMPLE_AUTH) {
      saslRpcClient.dispose();
      if (saslRpcClient.fallbackAllowed) {
        saslPromise.trySuccess(false);
      } else {
        saslPromise.tryFailure(new FallbackDisallowedException());
      }
      // When we switch to simple auth, we should also remove SaslChallengeDecoder and
      // NettyHBaseSaslRpcClientHandler.
      removeHandlers(ctx);
      return;
    }
    LOG.trace("Reading input token size={} for processing by initSASLContext", len);
    final byte[] challenge = new byte[len];
    msg.readBytes(challenge);
    byte[] response = ugi.doAs(new PrivilegedExceptionAction<byte[]>() {

      @Override
      public byte[] run() throws Exception {
        return saslRpcClient.evaluateChallenge(challenge);
      }
    });
    if (response != null) {
      writeResponse(ctx, response);
    }
    tryComplete(ctx);
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    saslPromise.tryFailure(new ConnectionClosedException("Connection closed"));
    ctx.fireChannelInactive();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    saslPromise.tryFailure(cause);
  }
}
