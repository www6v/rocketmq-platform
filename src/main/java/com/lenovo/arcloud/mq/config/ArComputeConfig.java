/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/***
 * Description
 *
 * @since 2017/3/30
 *
 */
@Configuration
@ConfigurationProperties(prefix = "arCompute")
public class ArComputeConfig {
    @Setter @Getter
    private String address;

    @Setter @Getter
    private String user;

    @Setter @Getter
    private String password;

    @Setter @Getter
    private String period;

    @Setter @Getter
    private String downloadVideoPrj;

    @Setter @Getter
    private String downloadVideoFlow;

    @Setter @Getter
    private String extractFeaturePrj;

    @Setter @Getter
    private String extractFeatureFlow;
}