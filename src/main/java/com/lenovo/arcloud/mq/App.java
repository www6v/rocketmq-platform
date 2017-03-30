package com.lenovo.arcloud.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/30
 *
 */
@EnableAutoConfiguration
@SpringBootApplication
public class App {
    public static void main(String[] args){
        SpringApplication.run(App.class,args);
    }
}
