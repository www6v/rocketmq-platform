/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.hbase;

import com.lenovo.arcloud.mq.config.HbaseConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
@Service
public class HbaseConfigurationFactoryBean implements InitializingBean, FactoryBean<Configuration> {
    private Configuration configuration;

    @Setter @Getter
    private Configuration hadoopConfig;

    @Resource
    private HbaseConfig hbaseConfig;

    @Override
    public Configuration getObject() throws Exception {
        return configuration;
    }

    @Override
    public Class<?> getObjectType() {
        return (configuration != null) ? configuration.getClass() : Configuration.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        configuration = (hadoopConfig != null) ? HBaseConfiguration.create(hadoopConfig) : HBaseConfiguration.create();
        initHbaseProperties();
    }

    private void initHbaseProperties() {
        configuration.set("hbase.zookeeper.quorum", hbaseConfig.getClientHost());
        configuration.set("hbase.zookeeper.property.clientPort", hbaseConfig.getClientPort());
        configuration.set("hbase.ipc.client.tcpnodelay", hbaseConfig.getTcpnodelay());
        configuration.set("hbase.rpc.timeout", hbaseConfig.getRpcTimeout());
        configuration.set("hbase.client.operation.timeout", hbaseConfig.getOpTimeout());
        configuration.set("hbase.ipc.client.socket.timeout.read", hbaseConfig.getReadTimeout());
        configuration.set("hbase.ipc.client.socket.timeout.write", hbaseConfig.getWriteTimeout());
        configuration.set("hbase.client.async.enable", hbaseConfig.getEnableAsync());
        configuration.set("hbase.client.async.in.queuesize", hbaseConfig.getAsyncQueueSize());
        configuration.set("hbase.tablemultiplexer.flush.period.ms", hbaseConfig.getFlushPeriod());
        configuration.set("hbase.client.max.retries.in.queue", hbaseConfig.getMaxRetries());
    }

}