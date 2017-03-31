/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;

import java.util.List;
import java.util.Map;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
public interface HbaseAsyncOperation {
    boolean isAvailable();

    boolean put(TableName tableName, final Put put);

    List<Put> put(TableName tableName, final List<Put> puts);

    Long getOpsCount();

    Long getOpsRejectedCount();

    Long getCurrentOpsCount();

    Long getOpsFailedCount();

    Long getOpsAverageLatency();

    Map<String, Long> getCurrentOpsCountForEachRegionServer();

    Map<String, Long> getOpsFailedCountForEachRegionServer();

    Map<String, Long> getOpsAverageLatencyForEachRegionServer();
}