package com.lenovo.arcloud.mq.hbase;

import lombok.Getter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HTableMultiplexer;
import org.apache.hadoop.hbase.client.Put;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
public class HbaseAsyncTemplate implements HbaseAsyncOperation{
    private final HTableMultiplexer hTableMultiplexer;
    private final AtomicInteger opsCount = new AtomicInteger();
    private final AtomicInteger opsRejectCount = new AtomicInteger();


    public HbaseAsyncTemplate(Configuration config,int perRegionServerBufferQueueSize) throws IOException {
        this.hTableMultiplexer = new HTableMultiplexer(config,perRegionServerBufferQueueSize);
    }

    public HbaseAsyncTemplate(Connection connection, Configuration config, int perRegionServerBufferQueueSize) {
        this.hTableMultiplexer = new HTableMultiplexer(connection,config,perRegionServerBufferQueueSize);
    }


    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean put(TableName tableName, Put put) {
        opsCount.incrementAndGet();

        boolean success = hTableMultiplexer.put(tableName, put);
        if (!success) {
            opsRejectCount.incrementAndGet();
        }
        return success;
    }

    @Override
    public List<Put> put(TableName tableName, List<Put> puts) {
        opsCount.addAndGet(puts.size());

        List<Put> rejectPuts = hTableMultiplexer.put(tableName, puts);
        if (rejectPuts != null && rejectPuts.size() > 0) {
            opsRejectCount.addAndGet(rejectPuts.size());
        }
        return rejectPuts;
    }

    @Override
    public Long getOpsCount() {
        return opsCount.longValue();
    }

    @Override
    public Long getOpsRejectedCount() {
        return opsRejectCount.longValue();
    }

    @Override
    public Long getCurrentOpsCount() {
        return hTableMultiplexer.getHTableMultiplexerStatus().getTotalFailedCounter();
    }

    @Override
    public Long getOpsFailedCount() {
        return hTableMultiplexer.getHTableMultiplexerStatus().getTotalFailedCounter();
    }

    @Override
    public Long getOpsAverageLatency() {
        return hTableMultiplexer.getHTableMultiplexerStatus().getOverallAverageLatency();
    }

    @Override
    public Map<String, Long> getCurrentOpsCountForEachRegionServer() {
        return hTableMultiplexer.getHTableMultiplexerStatus().getBufferedCounterForEachRegionServer();
    }

    @Override
    public Map<String, Long> getOpsFailedCountForEachRegionServer() {
        return hTableMultiplexer.getHTableMultiplexerStatus().getFailedCounterForEachRegionServer();
    }

    @Override
    public Map<String, Long> getOpsAverageLatencyForEachRegionServer() {
        return hTableMultiplexer.getHTableMultiplexerStatus().getAverageLatencyForEachRegionServer();
    }
}
