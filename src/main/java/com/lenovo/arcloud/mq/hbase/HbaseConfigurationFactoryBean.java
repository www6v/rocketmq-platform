package com.lenovo.arcloud.mq.hbase;

import com.lenovo.arcloud.mq.config.HbaseConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Enumeration;
import java.util.Properties;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
@Service
public class HbaseConfigurationFactoryBean implements InitializingBean,FactoryBean<Configuration>{
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
        return (configuration != null) ?configuration.getClass():Configuration.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        configuration = (hadoopConfig != null) ? HBaseConfiguration.create(hadoopConfig):HBaseConfiguration.create();
        initHbaseProperties(); 
    }
    
    private void initHbaseProperties(){
        //Properties properties = new Properties();
        configuration.set("hbase.zookeeper.quorum",hbaseConfig.getClientHost());
        configuration.set("hbase.zookeeper.property.clientPort",hbaseConfig.getClientPort()); 
        configuration.set("hbase.ipc.client.tcpnodelay",hbaseConfig.getTcpnodelay());
        configuration.set("hbase.rpc.timeout",hbaseConfig.getRpcTimeout());
        configuration.set("hbase.client.operation.timeout",hbaseConfig.getOpTimeout());
        configuration.set("hbase.ipc.client.socket.timeout.read",hbaseConfig.getReadTimeout());
        configuration.set("hbase.ipc.client.socket.timeout.write",hbaseConfig.getWriteTimeout());
        configuration.set("hbase.client.async.enable",hbaseConfig.getEnableAsync());
        configuration.set("hbase.client.async.in.queuesize",hbaseConfig.getAsyncQueueSize());
        configuration.set("hbase.tablemultiplexer.flush.period.ms",hbaseConfig.getFlushPeriod());
        configuration.set("hbase.client.max.retries.in.queue",hbaseConfig.getMaxRetries());
        //addProperties(properties);
    }
    
    /*private void addProperties(Properties properties){
        Assert.notNull(configuration, "A non-null configuration is required");
        if(properties != null){
            Enumeration<?> enumeration = properties.propertyNames();
            while(enumeration.hasMoreElements()){
                String key = enumeration.nextElement().toString();
                configuration.set(key,properties.getProperty(key));
            }
        }
    }*/
}
