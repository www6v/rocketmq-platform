/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/23
 *
 */
public class TestProducer {

    public static void main(String[] args) throws MQClientException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("10.240.212.164:9876");

        producer.start();

        for (int i = 0; i < 10000000; i++)
            try {
                {
                    Message msg = new Message("BenchmarkTest",
                            "TagA",
                            "OrderID188",
                            "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                    SendResult sendResult = producer.send(msg);
                    System.out.printf("%s%n", sendResult);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        producer.shutdown();
    }

}