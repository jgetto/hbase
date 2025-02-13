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
package org.apache.hadoop.hbase.io;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseClassTestRule;
import org.apache.hadoop.hbase.HBaseCommonTestingUtility;
import org.apache.hadoop.hbase.io.crypto.tls.X509Util;
import org.apache.hadoop.hbase.testclassification.IOTests;
import org.apache.hadoop.hbase.testclassification.SmallTests;
import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This file has been copied from the Apache ZooKeeper project.
 * @see <a href=
 *      "https://github.com/apache/zookeeper/blob/391cb4aa6b54e19a028215e1340232a114c23ed3/zookeeper-server/src/test/java/org/apache/zookeeper/common/FileChangeWatcherTest.java">Base
 *      revision</a>
 */
@Category({ IOTests.class, SmallTests.class })
public class TestFileChangeWatcher {

  @ClassRule
  public static final HBaseClassTestRule CLASS_RULE =
    HBaseClassTestRule.forClass(TestFileChangeWatcher.class);

  private static File tempDir;
  private static File tempFile;

  private static final Logger LOG = LoggerFactory.getLogger(TestFileChangeWatcher.class);
  private static final HBaseCommonTestingUtility UTIL = new HBaseCommonTestingUtility();

  private static final long FS_TIMEOUT = 30000L;

  @BeforeClass
  public static void createTempFile() throws IOException {
    tempDir = new File(UTIL.getDataTestDir(TestFileChangeWatcher.class.getSimpleName()).toString())
      .getCanonicalFile();
    FileUtils.forceMkdir(tempDir);
    tempFile = File.createTempFile("zk_test_", "", tempDir);
  }

  @AfterClass
  public static void cleanupTempDir() {
    UTIL.cleanupTestDir();
  }

  @Test
  public void testEnableCertFileReloading() throws IOException {
    Configuration myConf = new Configuration();
    String sharedPath = "/tmp/foo.jks";
    myConf.set(X509Util.TLS_CONFIG_KEYSTORE_LOCATION, sharedPath);
    myConf.set(X509Util.TLS_CONFIG_TRUSTSTORE_LOCATION, sharedPath);
    AtomicReference<FileChangeWatcher> keystoreWatcher = new AtomicReference<>();
    AtomicReference<FileChangeWatcher> truststoreWatcher = new AtomicReference<>();
    X509Util.enableCertFileReloading(myConf, keystoreWatcher, truststoreWatcher, () -> {
    });
    assertNotNull(keystoreWatcher.get());
    assertThat(keystoreWatcher.get().getWatcherThreadName(), Matchers.endsWith("-foo.jks"));
    assertNull(truststoreWatcher.get());

    keystoreWatcher.getAndSet(null).stop();
    truststoreWatcher.set(null);

    String truststorePath = "/tmp/bar.jks";
    myConf.set(X509Util.TLS_CONFIG_TRUSTSTORE_LOCATION, truststorePath);
    X509Util.enableCertFileReloading(myConf, keystoreWatcher, truststoreWatcher, () -> {
    });

    assertNotNull(keystoreWatcher.get());
    assertThat(keystoreWatcher.get().getWatcherThreadName(), Matchers.endsWith("-foo.jks"));
    assertNotNull(truststoreWatcher.get());
    assertThat(truststoreWatcher.get().getWatcherThreadName(), Matchers.endsWith("-bar.jks"));

    keystoreWatcher.getAndSet(null).stop();
    truststoreWatcher.getAndSet(null).stop();
  }

  @Test
  public void testCallbackWorksOnFileChanges() throws IOException, InterruptedException {
    FileChangeWatcher watcher = null;
    try {
      final List<WatchEvent<?>> events = new ArrayList<>();
      watcher = new FileChangeWatcher(tempDir.toPath(), "test", event -> {
        LOG.info("Got an update: {} {}", event.kind(), event.context());
        // Filter out the extra ENTRY_CREATE events that are
        // sometimes seen at the start. Even though we create the watcher
        // after the file exists, sometimes we still get a create event.
        if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
          return;
        }
        synchronized (events) {
          events.add(event);
          events.notifyAll();
        }
      });
      watcher.start();
      watcher.waitForState(FileChangeWatcher.State.RUNNING);
      Thread.sleep(1000L); // TODO hack
      for (int i = 0; i < 3; i++) {
        LOG.info("Modifying file, attempt {}", (i + 1));
        FileUtils.writeStringToFile(tempFile, "Hello world " + i + "\n", StandardCharsets.UTF_8,
          true);
        synchronized (events) {
          if (events.size() < i + 1) {
            events.wait(FS_TIMEOUT);
          }
          assertEquals("Wrong number of events", i + 1, events.size());
          WatchEvent<?> event = events.get(i);
          assertEquals(StandardWatchEventKinds.ENTRY_MODIFY, event.kind());
          assertEquals(tempFile.getName(), event.context().toString());
        }
      }
    } finally {
      if (watcher != null) {
        watcher.stop();
        watcher.waitForState(FileChangeWatcher.State.STOPPED);
      }
    }
  }

  @Test
  public void testCallbackWorksOnFileTouched() throws IOException, InterruptedException {
    FileChangeWatcher watcher = null;
    try {
      final List<WatchEvent<?>> events = new ArrayList<>();
      watcher = new FileChangeWatcher(tempDir.toPath(), "test", event -> {
        LOG.info("Got an update: {} {}", event.kind(), event.context());
        // Filter out the extra ENTRY_CREATE events that are
        // sometimes seen at the start. Even though we create the watcher
        // after the file exists, sometimes we still get a create event.
        if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
          return;
        }
        synchronized (events) {
          events.add(event);
          events.notifyAll();
        }
      });
      watcher.start();
      watcher.waitForState(FileChangeWatcher.State.RUNNING);
      Thread.sleep(1000L); // TODO hack
      LOG.info("Touching file");
      FileUtils.touch(tempFile);
      synchronized (events) {
        if (events.isEmpty()) {
          events.wait(FS_TIMEOUT);
        }
        assertFalse(events.isEmpty());
        WatchEvent<?> event = events.get(0);
        assertEquals(StandardWatchEventKinds.ENTRY_MODIFY, event.kind());
        assertEquals(tempFile.getName(), event.context().toString());
      }
    } finally {
      if (watcher != null) {
        watcher.stop();
        watcher.waitForState(FileChangeWatcher.State.STOPPED);
      }
    }
  }

  @Test
  public void testCallbackWorksOnFileAdded() throws IOException, InterruptedException {
    FileChangeWatcher watcher = null;
    try {
      final List<WatchEvent<?>> events = new ArrayList<>();
      watcher = new FileChangeWatcher(tempDir.toPath(), "test", event -> {
        LOG.info("Got an update: {} {}", event.kind(), event.context());
        synchronized (events) {
          events.add(event);
          events.notifyAll();
        }
      });
      watcher.start();
      watcher.waitForState(FileChangeWatcher.State.RUNNING);
      Thread.sleep(1000L); // TODO hack
      File tempFile2 = File.createTempFile("zk_test_", "", tempDir);
      tempFile2.deleteOnExit();
      synchronized (events) {
        if (events.isEmpty()) {
          events.wait(FS_TIMEOUT);
        }
        assertFalse(events.isEmpty());
        WatchEvent<?> event = events.get(0);
        assertEquals(StandardWatchEventKinds.ENTRY_CREATE, event.kind());
        assertEquals(tempFile2.getName(), event.context().toString());
      }
    } finally {
      if (watcher != null) {
        watcher.stop();
        watcher.waitForState(FileChangeWatcher.State.STOPPED);
      }
    }
  }

  @Test
  public void testCallbackWorksOnFileDeleted() throws IOException, InterruptedException {
    FileChangeWatcher watcher = null;
    try {
      final List<WatchEvent<?>> events = new ArrayList<>();
      watcher = new FileChangeWatcher(tempDir.toPath(), "test", event -> {
        LOG.info("Got an update: {} {}", event.kind(), event.context());
        // Filter out the extra ENTRY_CREATE events that are
        // sometimes seen at the start. Even though we create the watcher
        // after the file exists, sometimes we still get a create event.
        if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
          return;
        }
        synchronized (events) {
          events.add(event);
          events.notifyAll();
        }
      });
      watcher.start();
      watcher.waitForState(FileChangeWatcher.State.RUNNING);
      Thread.sleep(1000L); // TODO hack
      tempFile.delete();
      synchronized (events) {
        if (events.isEmpty()) {
          events.wait(FS_TIMEOUT);
        }
        assertFalse(events.isEmpty());
        WatchEvent<?> event = events.get(0);
        assertEquals(StandardWatchEventKinds.ENTRY_DELETE, event.kind());
        assertEquals(tempFile.getName(), event.context().toString());
      }
    } finally {
      if (watcher != null) {
        watcher.stop();
        watcher.waitForState(FileChangeWatcher.State.STOPPED);
      }
    }
  }

  @Test
  public void testCallbackErrorDoesNotCrashWatcherThread()
    throws IOException, InterruptedException {
    FileChangeWatcher watcher = null;
    try {
      final AtomicInteger callCount = new AtomicInteger(0);
      watcher = new FileChangeWatcher(tempDir.toPath(), "test", event -> {
        LOG.info("Got an update: {} {}", event.kind(), event.context());
        int oldValue;
        synchronized (callCount) {
          oldValue = callCount.getAndIncrement();
          callCount.notifyAll();
        }
        if (oldValue == 0) {
          throw new RuntimeException("This error should not crash the watcher thread");
        }
      });
      watcher.start();
      watcher.waitForState(FileChangeWatcher.State.RUNNING);
      Thread.sleep(1000L); // TODO hack
      LOG.info("Modifying file");
      FileUtils.writeStringToFile(tempFile, "Hello world\n", StandardCharsets.UTF_8, true);
      synchronized (callCount) {
        while (callCount.get() == 0) {
          callCount.wait(FS_TIMEOUT);
        }
      }
      LOG.info("Modifying file again");
      FileUtils.writeStringToFile(tempFile, "Hello world again\n", StandardCharsets.UTF_8, true);
      synchronized (callCount) {
        if (callCount.get() == 1) {
          callCount.wait(FS_TIMEOUT);
        }
      }
      // The value of callCount can exceed 1 only if the callback thread
      // survives the exception thrown by the first callback.
      assertTrue(callCount.get() > 1);
    } finally {
      if (watcher != null) {
        watcher.stop();
        watcher.waitForState(FileChangeWatcher.State.STOPPED);
      }
    }
  }
}
