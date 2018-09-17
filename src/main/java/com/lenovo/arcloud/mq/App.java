/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***
 * Description
 *
 *
 * @since 2017/3/30
 *
 */
@EnableAutoConfiguration
@SpringBootApplication
public class App {
    /**
     * Bootstrap Entry
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}