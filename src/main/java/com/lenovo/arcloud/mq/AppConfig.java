package com.lenovo.arcloud.mq;

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
@ConfigurationProperties(prefix = "arCompute")
public class AppConfig {
    private String serverAddr;

    private String user;

    private String password;


}
