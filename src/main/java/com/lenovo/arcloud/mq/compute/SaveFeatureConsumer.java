/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.compute;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.lenovo.arcloud.mq.config.RocketMqConfig;
import com.lenovo.arcloud.mq.dao.ImageDao;
import com.lenovo.arcloud.mq.model.ImageObj;
import com.lenovo.arcloud.mq.util.ConstantUtil;
import com.lenovo.arcloud.mq.util.FileUtils;
import org.apache.log4j.Logger;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/27
 *
 */

@Service
public class SaveFeatureConsumer extends DefaultMQPushConsumer {
    private static Logger logger = Logger.getLogger(SaveFeatureConsumer.class);

    @Resource
    private RocketMqConfig rocketMqConfig;

    @Resource
    private ImageDao hbaseImageDao;

    public void init() {
        this.setNamesrvAddr(rocketMqConfig.getNamesrvAddr());
        this.setConsumerGroup(rocketMqConfig.getDefaultConsumerGroup());
        try {
            this.subscribe(rocketMqConfig.getCalctopic(), rocketMqConfig.getDumpFeature());
            this.registerMessageListener(new SaveFeatureListener());
            this.start();
        }
        catch (MQClientException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        this.shutdown();
    }

    class SaveFeatureListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
            ConsumeConcurrentlyContext consumeConcurrentlyContext) {
            MessageExt messageExt = list.get(0);
            try {
                String message = new String(messageExt.getBody(), "UTF-8");
                JSONObject json = JSONObject.parseObject(message);
                String resultPath = json.getString(ConstantUtil.RESULT_PATH);
                String execId = json.getString(ConstantUtil.EXECUTION_ID);
                saveCalResult(resultPath, execId);
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

    private void saveCalResult(String resultPath, String execId) {

        logger.info("----save image------");
        File imageDir = new File(resultPath + File.separator + "images");
        if (imageDir.exists() && imageDir.isDirectory()) {
            File[] listFiles = imageDir.listFiles();
            Arrays.sort(listFiles, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    if (o1.isDirectory() && o2.isFile()) {
                        return -1;
                    }
                    if (o2.isDirectory() && o1.isFile()) {
                        return 1;
                    }
                    return o2.getName().compareTo(o1.getName());
                }
            });
            List<ImageObj> imageObjs = Lists.newArrayListWithCapacity(listFiles.length);
            long i = 0;
            for (File imageFile : listFiles) {
                if (imageFile.isFile()) {
                    ImageObj imageObj = new ImageObj();
                    imageObj.setImageId(i);
                    imageObj.setCreateTime(imageFile.lastModified());
                    imageObj.setExt(FileUtils.getFileExtension(imageFile.getPath()));
                    imageObj.setExecutionId(Long.valueOf(execId));
                    imageObj.setPath(imageFile.getAbsolutePath());
                    imageObj.setName(imageFile.getName());
                    imageObjs.add(imageObj);
                    i++;
                }
            }
            hbaseImageDao.insert(imageObjs);
        }

        logger.info("----save feature-----");

        logger.info("----save match-----");

        logger.info("----save sparse-----");

    }
}