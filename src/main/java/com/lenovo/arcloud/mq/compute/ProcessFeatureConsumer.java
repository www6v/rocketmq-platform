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
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/23
 *
 */
@Service
public class ProcessFeatureConsumer extends DefaultMQPushConsumer {
    private static Logger logger = LoggerFactory.getLogger(ProcessFeatureConsumer.class);

    @Resource
    private RocketMqConfig rocketMqConfig;

    @Resource
    private ArComputeConfig arComputeConfig;

    @Resource
    private ExeFlowService exeFlowService;

    @PostConstruct
    public void init() {
        logger.info("init Process feature consumer");
        this.setNamesrvAddr(rocketMqConfig.getNamesrvAddr());
        this.setConsumerGroup(rocketMqConfig.getProcessFeatureConsumerGroup());
        try {
            this.subscribe(rocketMqConfig.getCalctopic(), rocketMqConfig.getProcessFeature());
            this.registerMessageListener(new ProcessFeatureListener());
            this.start();
        }
        catch (MQClientException e) {
            logger.error("init ProcessFeature Consumer failure>>>"+e.getMessage());
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
            logger.info("ProcessFeature consume>>>"+messageExt.toString());
            try {
                String message = new String(messageExt.getBody(), "UTF-8");
                JSONObject json = JSONObject.parseObject(message);
                processFeature(json);
            }
            catch (UnsupportedEncodingException e) {
                logger.error("process feature failure>>>"+e.getMessage());
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        private void processFeature(JSONObject json) {
            String prjName = json.getString(ConstantUtil.ALGORITHM_NAME);
            String flowName = json.getString(ConstantUtil.FLOW_NAME);
            FlowObj downloadObj = new FlowObj();

            if(!StringUtils.isEmpty(prjName) && !StringUtils.isEmpty(flowName)){
                downloadObj.setProjectName(prjName);
                downloadObj.setFlowName(flowName);
            }else {
                downloadObj.setProjectName(arComputeConfig.getExtractFeaturePrj());
                downloadObj.setFlowName(arComputeConfig.getExtractFeatureFlow());
            }

            Map<String, String> flowProps = Maps.newHashMapWithExpectedSize(json.keySet().size());
            for (String key : json.keySet()) {
                if(flowProps.containsKey(key)){
                    continue;
                }
                flowProps.put(key,json.getString(key));
            }
            Object o = exeFlowService.ExecuteFlow(downloadObj, flowProps);
            logger.info(o.toString());

        }
    }


}