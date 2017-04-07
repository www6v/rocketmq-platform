/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.unit;

import com.mashape.unirest.http.Unirest;
import org.junit.Test;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/4/7
 *
 */
public class DownVideoTest {

    @Test
    public void testSendMsg(){
        try {
            String body = Unirest.post("http://10.240.212.164:8103/topic/test4.ar").header("accept", "application/json")
                .field("messageBody", "{'video.url':'http://10.4.65.35:8080/hdpSoftware/targetDB.zip','projectId':'123'}").asString().getBody();
            System.out.println(body);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
