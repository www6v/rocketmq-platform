/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Put;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
public class DisabledHbaseAsyncOperation implements HbaseAsyncOperation {

    static final DisabledHbaseAsyncOperation INSTANCE = new DisabledHbaseAsyncOperation();

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public boolean put(TableName tableName, Put put) {
        return false;
    }

    @Override
    public List<Put> put(TableName tableName, List<Put> puts) {
        return puts;
    }

    @Override
    public Long getOpsCount() {
        return -1L;
    }

    @Override
    public Long getOpsRejectedCount() {
        return -1L;
    }

    @Override
    public Long getCurrentOpsCount() {
        return -1L;
    }

    @Override
    public Long getOpsFailedCount() {
        return -1L;
    }

    @Override
    public Long getOpsAverageLatency() {
        return -1L;
    }

    @Override
    public Map<String, Long> getCurrentOpsCountForEachRegionServer() {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, Long> getOpsFailedCountForEachRegionServer() {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, Long> getOpsAverageLatencyForEachRegionServer() {
        return Collections.emptyMap();
    }
}