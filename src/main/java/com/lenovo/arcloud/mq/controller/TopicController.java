/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.controller;

import com.alibaba.fastjson.JSONObject;
import com.lenovo.arcloud.mq.compute.CommonProducer;
import com.lenovo.arcloud.mq.config.RocketMqConfig;
import com.lenovo.arcloud.mq.model.SendTopicMsgRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/23
 *
 */
@Controller
@RequestMapping("/topic")
public class TopicController {

    private Logger logger = LoggerFactory.getLogger(TopicController.class);

    @Resource
    private CommonProducer downVideoProducer;

    @Resource
    private CommonProducer processFeatureProducer;

    @Resource
    private CommonProducer dumpFeatureProducer;

    @Resource
    private RocketMqConfig rocketMqConfig;

    @RequestMapping(value = "/downVideo.ar", method = {RequestMethod.POST})
    @ResponseBody
    public Object sendDownVideoMsg(SendTopicMsgRequest sendTopicMsgRequest) {
        logger.info("start send download video message1");
        sendTopicMsgRequest.setTopic(rocketMqConfig.getCalctopic());
        sendTopicMsgRequest.setTag(rocketMqConfig.getDownVideo());
        return downVideoProducer.sendTopicMessageRequest(sendTopicMsgRequest);
    }

    @RequestMapping(value = "/processFeature.ar", method = {RequestMethod.POST})
    @ResponseBody
    public Object sendProcessFeatureMsg(SendTopicMsgRequest sendTopicMsgRequest) {
        sendTopicMsgRequest.setTopic(rocketMqConfig.getCalctopic());
        sendTopicMsgRequest.setTag(rocketMqConfig.getProcessFeature());
        return processFeatureProducer.sendTopicMessageRequest(sendTopicMsgRequest);
    }

    @RequestMapping(value = "/dumpFeature.ar", method = {RequestMethod.POST})
    @ResponseBody
    public Object sendDumpFeatureMsg(SendTopicMsgRequest sendTopicMsgRequest) {
        sendTopicMsgRequest.setTopic(rocketMqConfig.getCalctopic());
        sendTopicMsgRequest.setTag(rocketMqConfig.getDumpFeature());
        return dumpFeatureProducer.sendTopicMessageRequest(sendTopicMsgRequest);
    }

    @RequestMapping(value = "/test.ar", method = {RequestMethod.GET})
    @ResponseBody
    public Object test(@RequestParam String topic) {
        return "{topic:"+topic+"}";
    }

    @RequestMapping(value = "/test1.ar", method = {RequestMethod.GET})
    @ResponseBody
    public Object test1() {
        return "hello world";
    }

    @RequestMapping(value = "/test2.ar", method = {RequestMethod.POST})
    @ResponseBody
    public Object test2() {
        return "hello world";
    }
    @RequestMapping(value = "/test3.ar", method = {RequestMethod.POST})
    @ResponseBody
    public Object test3(@RequestBody SendTopicMsgRequest sendTopicMsgRequest) {
        if (sendTopicMsgRequest != null) {
            logger.info("test3 sendTopicMsgRequest json >>>" + JSONObject.toJSONString(sendTopicMsgRequest));
        }
        return sendTopicMsgRequest;
    }

    @RequestMapping(value = "/test4.ar", method = {RequestMethod.POST})
    @ResponseBody
    public Object test4(SendTopicMsgRequest sendTopicMsgRequest) {
        logger.info("start send download video message2");
        if (sendTopicMsgRequest != null) {
            logger.info("test4 sendTopicMsgRequest json >>>" + JSONObject.toJSONString(sendTopicMsgRequest));
        }
        sendTopicMsgRequest.setTopic(rocketMqConfig.getCalctopic());
        sendTopicMsgRequest.setTag(rocketMqConfig.getDownVideo());
        return downVideoProducer.sendTopicMessageRequest(sendTopicMsgRequest);
    }
}