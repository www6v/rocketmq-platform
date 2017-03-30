package com.lenovo.arcloud.mq.compute;

import com.google.common.base.Throwables;
import com.lenovo.arcloud.mq.config.RocketMqConfig;
import com.lenovo.arcloud.mq.model.SendTopicMsgRequest;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/24
 *
 */
@Service
public class CommonProducer extends DefaultMQProducer {


    @Resource
    private RocketMqConfig rocketMqConfig;

    @PostConstruct
    public void init() {
        try {
            this.setNamesrvAddr(rocketMqConfig.getNamesrvAddr());
            this.setProducerGroup(rocketMqConfig.getDefaultProducerGroup());
            this.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void close(){
        this.shutdown();
    }


    public SendResult sendTopicMessageRequest(SendTopicMsgRequest request) {
        Message msg = new Message(request.getTopic(),
                request.getTag(),
                request.getMessageBody().getBytes()
        );
        try {
            return this.send(msg);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }

    }



}
