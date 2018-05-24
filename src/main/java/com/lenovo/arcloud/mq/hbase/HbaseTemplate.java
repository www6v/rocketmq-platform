/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.hbase;

import com.google.common.collect.Lists;
import com.lenovo.arcloud.mq.config.HbaseConfig;
import com.lenovo.arcloud.mq.util.ArThreadFactory;
import com.lenovo.arcloud.mq.util.ExecutorFactory;
import com.lenovo.arcloud.mq.util.StopWatch;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Durability;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Increment;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
//@Service
public class HbaseTemplate extends AbstractHbaseTemplate implements HbaseOperations, InitializingBean, DisposableBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final long DEFAULT_DESTORY_TIMEOUT = 2000;

    private final AtomicBoolean isClose = new AtomicBoolean(false);

    @Resource
    private HbaseConfig hbaseConfig;

    private ExecutorService executorService;

    private HbaseAsyncOperation asyncOperation = DisabledHbaseAsyncOperation.INSTANCE;

    public void setAsyncOperation(HbaseAsyncOperation asyncOperation) {
        if (asyncOperation == null) {
            throw new NullPointerException("asyncOperation");
        }
        this.asyncOperation = asyncOperation;
    }

    private void assertAccessAvailable() {
        if (isClose.get()) {
            throw new HbaseAccessException("hbase already close");
        }
    }

    private Put createPut(byte[] rowName, byte[] familyName, Long timestamp, byte[] qualifier, byte[] value) {
        Put put = new Put(rowName);
        if (familyName != null) {
            if (timestamp == null) {
                put.addColumn(familyName, qualifier, value);
            }
            else {
                put.addColumn(familyName, qualifier, timestamp, value);
            }
        }
        return put;
    }

    private Table getTable(TableName tableName) {
        return getTableFactory().getTable(tableName);
    }

    @Override
    public <T> T get(TableName tableName, final String rowName, final String familyName, final String qualifier,
        final RowMapper<T> mapper) {
        assertAccessAvailable();
        return execute(tableName, new TableCallback<T>() {
            @Override
            public T doInTable(Table table) throws Throwable {
                Get get = new Get(rowName.getBytes(getCharset()));
                if (familyName != null) {
                    byte[] family = familyName.getBytes(getCharset());

                    if (qualifier != null) {
                        get.addColumn(family, qualifier.getBytes(getCharset()));
                    }
                    else {
                        get.addFamily(family);
                    }
                }
                Result result = table.get(get);
                return mapper.mapRow(result, 0);
            }
        });
    }

    @Override
    public <T> T get(TableName tableName, final Get get, final RowMapper<T> mapper) {
        assertAccessAvailable();
        return execute(tableName, new TableCallback<T>() {
            @Override
            public T doInTable(Table table) throws Throwable {
                Result result = table.get(get);
                return mapper.mapRow(result, 0);
            }
        });
    }

    @Override
    public void put(TableName tableName, final byte[] rowName, final byte[] familyName, final byte[] qualifier,
        final Long timestamp, final byte[] value) {
        assertAccessAvailable();
        execute(tableName, new TableCallback() {
            @Override
            public Object doInTable(Table table) throws Throwable {
                Put put = createPut(rowName, familyName, timestamp, qualifier, value);
                table.put(put);
                return null;
            }
        });
    }

    @Override
    public void put(TableName tableName, final Put put) {
        assertAccessAvailable();
        execute(tableName, new TableCallback() {
            @Override
            public Object doInTable(Table table) throws Throwable {
                table.put(put);
                return null;
            }
        });
    }

    @Override
    public void put(TableName tableName, final List<Put> puts) {
        assertAccessAvailable();
        execute(tableName, new TableCallback() {
            @Override
            public Object doInTable(Table table) throws Throwable {
                table.put(puts);
                return null;
            }
        });
    }

    @Override
    public List<Put> asyncPut(TableName tableName, List<Put> puts) {
        assertAccessAvailable();
        if (asyncOperation.isAvailable()) {
            return asyncOperation.put(tableName, puts);
        }
        else {
            put(tableName, puts);
            return Collections.emptyList();
        }
    }

    @Override
    public void delete(TableName tableName, final List<Delete> deletes) {
        assertAccessAvailable();
        execute(tableName, new TableCallback() {
            @Override
            public Object doInTable(Table table) throws Throwable {
                table.delete(deletes);
                return null;
            }
        });
    }

    @Override
    public <T> List<T> find(TableName tableName, final List<Scan> scans, final ResultsExtractor<T> action) {
        assertAccessAvailable();
        return execute(tableName, new TableCallback<List<T>>() {
            @Override
            public List<T> doInTable(Table table) throws Throwable {
                List<T> result = new ArrayList<>(scans.size());
                for (Scan scan : scans) {
                    final ResultScanner scanner = table.getScanner(scan);
                    try {
                        T t = action.extractData(scanner);
                        result.add(t);
                    }
                    finally {
                        scanner.close();
                    }
                }
                return result;
            }
        });
    }

    @Override
    public <T> List<T> findParallel(final TableName tableName, List<Scan> scans, final ResultsExtractor<T> action) {
        assertAccessAvailable();
        if (!hbaseConfig.isEnableParallelScan() || scans.size() == 1) {
            return find(tableName, scans, action);
        }
        List<T> results = new ArrayList<>(scans.size());
        List<Callable<T>> callables = new ArrayList<>(scans.size());
        for (final Scan scan : scans) {
            callables.add(new Callable<T>() {
                @Override
                public T call() throws Exception {
                    return execute(tableName, new TableCallback<T>() {
                        @Override
                        public T doInTable(Table table) throws Throwable {
                            final ResultScanner scanner = table.getScanner(scan);
                            try {
                                return action.extractData(scanner);
                            }
                            finally {
                                scanner.close();
                            }
                        }
                    });
                }
            });
        }
        List<List<Callable<T>>> callablePartitions = Lists.partition(callables, hbaseConfig.getMaxThreadNumPerScan());
        for (List<Callable<T>> callablePartition : callablePartitions) {
            try {
                List<Future<T>> futures = this.executorService.invokeAll(callablePartition);
                for (Future<T> future : futures) {
                    results.add(future.get());
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("interrupted while findParallel [{}].", tableName);
                return Collections.emptyList();
            }
            catch (ExecutionException e) {
                logger.warn("findParallel [{}], error : {}", tableName, e);
                return Collections.emptyList();
            }
        }
        return results;
    }

    @Override
    public Result increment(TableName tableName, final Increment increment) {
        assertAccessAvailable();
        return execute(tableName, new TableCallback<Result>() {
            @Override
            public Result doInTable(Table table) throws Throwable {
                return table.increment(increment);
            }
        });
    }

    @Override
    public long incrementColumnValue(TableName tableName, final byte[] rowName, final byte[] familyName,
        final byte[] qualifier, final long amount, final boolean writeToWAL) {
        assertAccessAvailable();
        return execute(tableName, new TableCallback<Long>() {
            @Override
            public Long doInTable(Table table) throws Throwable {
                return table.incrementColumnValue(rowName, familyName, qualifier, amount, writeToWAL ? Durability.SKIP_WAL : Durability.USE_DEFAULT);
            }
        });
    }

    @Override
    public <T> T execute(TableName tableName, TableCallback<T> action) {
        Assert.notNull(action, "Callback object must not be null");
        Assert.notNull(tableName, "No table specified");
        assertAccessAvailable();

        Table table = getTable(tableName);
        try {
            T result = action.doInTable(table);
            return result;
        }
        catch (Throwable e) {
            if (e instanceof Error) {
                throw (Error)e;
            }
            if (e instanceof RuntimeException) {
                throw (RuntimeException)e;
            }
            throw new HbaseSystemException((Exception)e);
        }
        finally {
            releaseTable(table);
        }
    }

    private void releaseTable(Table table) {
        getTableFactory().releaseTable(table);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(getHbaseConfiguration(), "configuration is required");
        Assert.notNull(getTableFactory(), "TableFactory is required");

        ArThreadFactory parallelScannerThreadFactory = new ArThreadFactory("AR-HBase-Parallel-scanner");
        if (hbaseConfig.getMaxThreadNumPerScan() <= 1) {
            hbaseConfig.setEnableParallelScan(false);
            this.executorService = Executors.newSingleThreadExecutor(parallelScannerThreadFactory);
        }
        else {
            this.executorService = ExecutorFactory.newFixedThreadPool(hbaseConfig.getMaxParallelThreadNum(), 1024, parallelScannerThreadFactory);
        }
    }

    @Override
    public void destroy() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        if (isClose.compareAndSet(false, true)) {
            logger.info("HBaseTemplate destroy");
            final ExecutorService executor = this.executorService;
            if (executor != null) {
                executor.shutdown();
                try {
                    executor.awaitTermination(DEFAULT_DESTORY_TIMEOUT, TimeUnit.MILLISECONDS);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            long remainingTime = Math.max(DEFAULT_DESTORY_TIMEOUT - stopWatch.stop(), 100);
            awaitAsyncPutOpsCleared(remainingTime, 50);

        }
    }

    private boolean awaitAsyncPutOpsCleared(long waitTimeout, long checkUnitTime) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        while (true) {
            Long currentOpsCount = asyncOperation.getCurrentOpsCount();
            logger.warn("count====" + currentOpsCount);
            if (currentOpsCount <= 0) {
                return true;
            }
            if (stopWatch.stop() > waitTimeout) {
                return false;
            }
            try {
                Thread.sleep(checkUnitTime);
            }
            catch (InterruptedException e) {
            }
        }

    }
}