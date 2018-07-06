/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.unit;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.util.Date;
import org.junit.Test;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/4/7
 *
 */
public class DownVideoTest {


    /**
     * video.url 扫描的压缩包url地址
     * record.id 可选 对应扫描结果库记录id，为计算完结果回传提供支持
     * alg.name 对应计算模块的库名
     * flow.name 对应计算模块的入口方法
     */
    @Test /// from UserGuide.md
    public void testSendMsg() {
        try {
            //vpn转发地址：http://114.215.181.127:8780/sendMsg
            //// 10.240.212.164 没有这个机器 /// 'developerId' added
            ///// http://10.4.65.35:8080/hdpSoftware/20170405094622-20170405094548.zip
            ///// http://120.55.162.42:8080/hdpSoftware/20170405094622-20170405094548.zip

//            String body = Unirest.post("http://120.55.162.42:8103/topic/downVideo.ar").header("accept", "application/json")
//                    .field("messageBody", "{'video.url':'http://120.55.162.42:8080/hdpSoftware/20180420181143.zip','record.id':'123','alg.name':'ImageFeature_ORB','flow.name':'Reconstruction', 'developerId':'321'}").asString().getBody();
//            System.out.println(body);

            /// 20180420181143.zip  杨永恒
            /// trainingData.zip  建冲

            /// 阿里云 42
            for(int i=0; i<1; i++) {
                String body = Unirest.post("http://120.55.162.42:8103/topic/downVideo.ar").header("accept", "application/json")
                        .field("messageBody", "{'video.url':'http://repo.shai.cloud:18080/api/app/fs/contents/20180420181143.zip','record.id':'11111','alg.name':'ImageFeature_ORB','flow.name':'Reconstruction', 'developerId':'149'}").asString().getBody();
                System.out.println(body);
                Thread.sleep(1000*10);
            }

            /// test env
//            for(int i=0; i<1; i++) {
//                String body = Unirest.post("http://10.4.65.69:8103/topic/downVideo.ar").header("accept", "application/json")
//                        .field("messageBody", "{'video.url':'http://repo.shai.cloud:18080/api/app/fs/contents/20180420181143.zip','record.id':'764','alg.name':'ImageFeature_ORB','flow.name':'Reconstruction', 'developerId':'149'}").asString().getBody();
//                System.out.println(body);
//            }

            ///  龙超
//            for(int i=0; i<1; i++) {
//                String body = Unirest.post("http://10.4.65.226:8103/topic/downVideo.ar").header("accept", "application/json")
//                        .field("messageBody", "{'video.url':'http://repo.shai.cloud:18080/api/app/fs/contents/20180420181143.zip','record.id':'764','alg.name':'ImageFeature_ORB','flow.name':'Reconstruction', 'developerId':'149'}").asString().getBody();
//                System.out.println(body);
//            }

            /// deeplearning 151
//            for(int i=0; i<1; i++) {
//                String body = Unirest.post("http://10.4.65.151:8103/topic/downVideo.ar").header("accept", "application/json")
//                        .field("messageBody", "{'video.url':'http://repo.shai.cloud:18080/api/app/fs/contents/20180420181143.zip','record.id':'11111','alg.name':'ImageFeature_ORB','flow.name':'Reconstruction', 'developerId':'149'}").asString().getBody();
//                System.out.println(body);
//            }

            /// 'video.url':'http://120.55.162.42:8080/hdpSoftware/20180420181143.zip'
            ///http://repo.shai.cloud:8180/api/app/fs/contents/20180516164032-cupww.zip
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * video.url 扫描的压缩包url地址
     * record.id 可选 对应扫描结果库记录id，为计算完结果回传提供支持
     * alg.name 对应计算模块的库名
     * flow.name 对应计算模块的入口方法
     */
//    @Test
    public void testSendAliyunMsg() {
        try {
            String body = Unirest.post("http://120.55.162.42:8103/topic/downVideo.ar").header("accept", "application/json")
                .field("messageBody", "{\"alg.name\":\"ImageFeature_ORB\",\"developerId\":1,\"flow.name\":\"Reconstruction\",\"record.id\":340,\"video.url\":\"http://106.15.45.70:8180/api/dev/fs/contents/20170707091702-20170707091604toll.zip\"}").asString().getBody();
            System.out.println(body);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @Test
    public void testSendDevMsg() {
        try {
            String body = Unirest.post("http://10.240.212.164:8103/topic/downVideo.ar").header("accept", "application/json")
                .field("messageBody", "{'video.url':'http://10.240.212.164:9271/20170405094622-20170405094548.zip','developerId':'1','record.id':'123','alg.name':'ImageFeature_ORB','flow.name':'Reconstruction'}").asString().getBody();
            System.out.println(body);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


//    @Test
    public void testSendVpnMsg() {
        try {
            HttpResponse<String> response = Unirest.post("http://114.215.181.127:8780/sendMsg").header("accept","application/json")
                .field("messageBody", "{'video.url':'http://10.240.212.164:9271/20170509203142-20170509203138.zip','developerId':'1','record.id':'123','alg.name':'ImageFeature_ORB','flow.name':'Reconstruction'}").asString();
            String body = response.getBody();
            System.out.println(body);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
//    @Test
    public void testSendVpnMsgInter() {
        try {


            HttpResponse<String> response = Unirest.post("http://10.4.64.60:8780/sendMsg").header("accept","application/json")
                .field("messageBody", "{'video.url':'http://10.4.65.35:8080/hdpSoftware/20170405094622-20170405094548.zip','record.id':'123','alg.name':'ImageFeature_ORB','flow.name':'Reconstruction'}").asString();
            String body = response.getBody();
            System.out.println(body);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @Test
    public void testJson(){
        String a = "{'video.name':'targetDB.zip','record.id':123,'alg.name':'ImageFeature_ORB','flow.name':'sparse'}";
        JSONObject json = JSONObject.parseObject(a);
        System.out.println(json.toJSONString());
    }

//    @Test
    public void testSql(){
        int max =119;
        for(int i=0;i<=max;i++){
            String result = String.format("%03d", i);
            String sql = "insert into image(data) select pg_read_binary_file('image/"+result+".jpg');";
            System.out.println(sql);
        }
    }

    public static void main(){
        Date now = new Date();
        System.out.println();
    }
}
