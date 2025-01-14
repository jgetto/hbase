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
package org.apache.hadoop.hbase.ipc;

import org.apache.hadoop.hbase.util.Pair;
import org.apache.yetus.audience.InterfaceAudience;

@InterfaceAudience.Private
public interface MetricsHBaseServerWrapper {
  long getTotalQueueSize();

  int getGeneralQueueLength();

  int getReplicationQueueLength();

  int getPriorityQueueLength();

  int getMetaPriorityQueueLength();

  int getNumOpenConnections();

  int getActiveRpcHandlerCount();

  int getActiveGeneralRpcHandlerCount();

  int getActivePriorityRpcHandlerCount();

  int getActiveReplicationRpcHandlerCount();

  int getActiveMetaPriorityRpcHandlerCount();

  long getNumGeneralCallsDropped();

  long getNumLifoModeSwitches();

  int getWriteQueueLength();

  int getReadQueueLength();

  int getScanQueueLength();

  int getActiveWriteRpcHandlerCount();

  int getActiveReadRpcHandlerCount();

  int getActiveScanRpcHandlerCount();

  long getNettyDmUsage();

  /**
   * These two metrics are calculated together, so we want to return them in one call
   * @return pair containing total (first) and max (second) pending outbound bytes.
   */
  Pair<Long, Long> getTotalAndMaxNettyOutboundBytes();
}
