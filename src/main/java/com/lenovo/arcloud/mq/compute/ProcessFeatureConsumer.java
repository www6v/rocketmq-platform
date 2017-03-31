/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.compute;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.lenovo.arcloud.mq.config.ArComputeConfig;
import com.lenovo.arcloud.mq.config.RocketMqConfig;
import com.lenovo.arcloud.mq.model.FlowObj;
import com.lenovo.arcloud.mq.service.ExeFlowService;
import com.lenovo.arcloud.mq.util.ConstantUtil;
import org.apache.log4j.Logger;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/23
 *
 */
@Service
public class ProcessFeatureConsumer extends DefaultMQPushConsumer {
    private static Logger logger = Logger.getLogger(ProcessFeatureConsumer.class);

    @Resource
    private RocketMqConfig rocketMqConfig;

    @Resource
    private ArComputeConfig arComputeConfig;

    @Resource
    private ExeFlowService exeFlowService;

    @PostConstruct
    public void init() {
        this.setNamesrvAddr(rocketMqConfig.getNamesrvAddr());
        this.setConsumerGroup(rocketMqConfig.getDefaultConsumerGroup());
        try {
            this.subscribe(rocketMqConfig.getCalctopic(), rocketMqConfig.getProcessFeature());
            this.registerMessageListener(new ProcessFeatureListener());
            this.start();
        }
        catch (MQClientException e) {
            e.printStackTrace();
        }

    }

    @PreDestroy
    public void close() {
        this.shutdown();
    }

    class ProcessFeatureListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
            ConsumeConcurrentlyContext consumeConcurrentlyContext) {
            MessageExt messageExt = list.get(0);
            try {
                String message = new String(messageExt.getBody(), "UTF-8");
                JSONObject json = JSONObject.parseObject(message);
                String videoPath = json.getString(ConstantUtil.VIDEO_PATH);
                processFeature(videoPath);
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

    private void processFeature(String videoPath) {
        FlowObj downloadObj = new FlowObj();
        downloadObj.setProjectName(arComputeConfig.getExtractFeaturePrj());
        downloadObj.setFlowName(arComputeConfig.getExtractFeatureFlow());

        Map<String, String> flowProps = Maps.newHashMapWithExpectedSize(1);
        flowProps.put(ConstantUtil.VIDEO_PATH, videoPath);
        Object o = exeFlowService.ExecuteFlow(downloadObj, flowProps);

        logger.info(o.toString());

    }
}