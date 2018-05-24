/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.hbase;

import com.lenovo.arcloud.mq.config.HbaseConfig;
import com.lenovo.arcloud.mq.util.ExecutorFactory;
import lombok.Getter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
//@Service
public class PooledHTableFactory implements TableFactory, DisposableBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ExecutorService executorService;
    @Getter
    private Connection connection;

    @Resource
    private HbaseConfigurationFactoryBean hbaseConfiguration;

    @Resource
    private HbaseConfig hbaseConfig;

    @PostConstruct
    public void init() {
        this.executorService = createExecutorService(hbaseConfig.getMaxThreadNum(), hbaseConfig.getThreadPoolQueueSize(), hbaseConfig.isEnablePrestart());
        try {
            Configuration config = hbaseConfiguration.getObject();
            this.connection = ConnectionFactory.createConnection(config, executorService);
        }
        catch (Exception e) {
            throw new HbaseSystemException(e);
        }
    }

    private ExecutorService createExecutorService(int poolSize, int workQueueMaxSize, boolean prestartThreadPool) {
        logger.info("create HConnectionThreadPool poolSize:{},workerQueueMaxSize:{}", poolSize, workQueueMaxSize);
        ThreadPoolExecutor threadPoolExecutor = ExecutorFactory.newFixedThreadPool(poolSize, workQueueMaxSize, "AR-HBase", true);
        if (prestartThreadPool) {
            logger.info("prestart all hbase thread");
            threadPoolExecutor.prestartAllCoreThreads();
        }
        return threadPoolExecutor;
    }

    @Override
    public Table getTable(TableName tableName) {
        try {
            return connection.getTable(tableName, executorService);
        }
        catch (IOException e) {
            throw new HbaseSystemException(e);
        }
    }

    @Override
    public void releaseTable(Table table) {
        if (table == null) {
            return;
        }
        try {
            table.close();
        }
        catch (IOException e) {
            throw new HbaseSystemException(e);
        }

    }

    @Override
    public void destroy() throws Exception {
        logger.info("PooledHTableFactory destroy");

        if (connection != null) {
            try {
                this.connection.close();
            }
            catch (IOException e) {
                logger.warn("connection.close() error:" + e.getMessage(), e);
            }
        }

        if (this.executorService != null) {
            this.executorService.shutdown();
            boolean shutdown = false;
            try {
                shutdown = executorService.awaitTermination(5000, TimeUnit.MILLISECONDS);
                if (!shutdown) {
                    List<Runnable> discardTask = this.executorService.shutdownNow();
                    logger.warn("discard task size:{}", discardTask.size());
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}