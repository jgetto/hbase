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
package org.apache.hadoop.hbase.master;

import static org.apache.hadoop.hbase.regionserver.storefiletracker.StoreFileTrackerFactory.TRACKER_IMPL;
import static org.apache.hadoop.hbase.regionserver.storefiletracker.StoreFileTrackerFactory.Trackers.FILE;
import static org.junit.Assert.assertEquals;

import org.apache.hadoop.hbase.HBaseClassTestRule;
import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.testclassification.MasterTests;
import org.apache.hadoop.hbase.testclassification.MediumTests;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TestName;

/**
 * Test the master filesystem in a local cluster with Store File Tracking explicitly set in global
 * config
 */
@Category({ MasterTests.class, MediumTests.class })
public class TestMasterFileSystemWithStoreFileTracking {

  @ClassRule
  public static final HBaseClassTestRule CLASS_RULE =
    HBaseClassTestRule.forClass(TestMasterFileSystemWithStoreFileTracking.class);

  @Rule
  public TestName name = new TestName();

  private static final HBaseTestingUtility UTIL = new HBaseTestingUtility();

  @BeforeClass
  public static void setupTest() throws Exception {
    UTIL.getConfiguration().set(TRACKER_IMPL, FILE.name());
    UTIL.startMiniCluster();
  }

  @AfterClass
  public static void teardownTest() throws Exception {
    UTIL.shutdownMiniCluster();
  }

  @Test
  public void tesMetaDescriptorHasSFTConfig() throws Exception {
    TableDescriptor descriptor = UTIL.getAdmin().getDescriptor(TableName.META_TABLE_NAME);
    assertEquals(FILE.name(), descriptor.getValue(TRACKER_IMPL));
  }
}
