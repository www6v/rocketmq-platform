package com.lenovo.arcloud.mq.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/30
 *
 */
@Configuration
@ConfigurationProperties(prefix = "hbase")
public class HbaseConfig {
    @Setter @Getter
    private String clientHost;

    @Setter @Getter
    private String clientPort;

    @Setter @Getter
    private String tcpnodelay;

    @Setter @Getter
    private String rpcTimeout;

    @Setter @Getter
    private String opTimeout;

    @Setter @Getter
    private String readTimeout;

    @Setter @Getter
    private String writeTimeout;

    @Setter @Getter
    private String enableAsync;

    @Setter @Getter
    private String asyncQueueSize;

    @Setter @Getter
    private String flushPeriod;

    @Setter @Getter
    private String maxRetries;

    @Setter @Getter
    private int maxThreadNum;

    @Setter @Getter
    private int threadPoolQueueSize;

    @Setter @Getter
    private boolean enablePrestart;

    @Setter @Getter
    private boolean enableParallelScan;

    @Setter @Getter
    private int maxParallelThreadNum;

    @Setter @Getter
    private int maxThreadNumPerScan;




}
