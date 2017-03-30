package com.lenovo.arcloud.mq.compute;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.lenovo.arcloud.mq.config.ArComputeConfig;
import com.lenovo.arcloud.mq.config.RocketMqConfig;
import com.lenovo.arcloud.mq.model.FlowObj;
import com.lenovo.arcloud.mq.service.ExeFlowService;
import com.lenovo.arcloud.mq.util.ConstantUtil;
import lombok.Getter;
import lombok.Setter;
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
 * Download Video Consumer
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/23
 *
 */
@Service
public class DownVideoConsumer extends DefaultMQPushConsumer{
    private static Logger logger = Logger.getLogger(DownVideoConsumer.class);

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
            this.subscribe(rocketMqConfig.getCalctopic(),rocketMqConfig.getDownVideo());
            this.registerMessageListener(new DownVideoListener());
            this.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

    }

    @PostConstruct
    public void close(){
        this.shutdown();
    }

    class DownVideoListener implements MessageListenerConcurrently{
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
            MessageExt messageExt = list.get(0);
            try {
                String message = new String(messageExt.getBody(), "UTF-8");
                JSONObject json = JSONObject.parseObject(message);
                String videoUrl = json.getString(ConstantUtil.VIDEO_URL);
                download(videoUrl);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

    private void download(String videoUrl){
        FlowObj downloadObj = new FlowObj();
        downloadObj.setProjectName(arComputeConfig.getDownloadVideoPrj());
        downloadObj.setFlowName(arComputeConfig.getDownloadVideoFlow());

        Map<String,String> flowProps = Maps.newHashMapWithExpectedSize(1);
        flowProps.put(ConstantUtil.VIDEO_URL,videoUrl);
        Object o = exeFlowService.ExecuteFlow(downloadObj,flowProps);

        logger.info(o.toString());

    }




}
