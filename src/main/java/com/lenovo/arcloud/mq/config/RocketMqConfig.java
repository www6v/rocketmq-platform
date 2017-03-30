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
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMqConfig {
    @Setter @Getter
    private String namesrvAddr;

    @Setter @Getter
    private String defaultProducerGroup;

    @Setter @Getter
    private String defaultConsumerGroup;

    @Setter @Getter
    private String calctopic;

    @Setter @Getter
    private String downVideo;

    @Setter @Getter
    private String processFeature;

    @Setter @Getter
    private String dumpFeature;
}
