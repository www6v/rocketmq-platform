/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.unit;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
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
        /*try {
            HttpResponse<JsonNode> body = Unirest.post("http://10.240.212.164:8103/topic/downVideo.ar")
                .queryString("messageBody", "{'video.url':'http://10.4.65.35:8080/hdpSoftware/targetDB.zip','projectId':'123'}").asJson();
            System.out.println(body.toString());
        }
        catch (UnirestException e) {
            e.printStackTrace();
        }*/

    }
}
