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
package org.apache.hadoop.hbase.client;

import java.util.Comparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.yetus.audience.InterfaceAudience;

/**
 * Has a row.
 */
@InterfaceAudience.Public
public interface Row extends Comparable<Row> {
  Comparator<Row> COMPARATOR = (v1, v2) -> Bytes.compareTo(v1.getRow(), v2.getRow());

  /** Returns The row. */
  byte[] getRow();

  /**
   * Compare this row to another row.
   * @deprecated As of release 2.0.0, this will be removed in HBase 3.0.0. Use
   *             {@link Row#COMPARATOR} instead
   */
  @Deprecated
  @Override
  int compareTo(Row var1);
}
