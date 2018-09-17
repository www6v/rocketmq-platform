/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.model;

import lombok.Getter;
import lombok.Setter;

/***
 * Topic Msg
 *
 *
 * @since 2017/3/24
 *
 */
public class SendTopicMsgRequest {

    @Getter @Setter
    private String topic;

    @Getter @Setter
    private String key;

    @Getter @Setter
    private String tag;

    @Getter @Setter
    private String messageBody;

    public String toString(){
        return "topic>>>"+topic +">>>key>>>"+key+">>>tag>>>"+tag+">>>messageBody>>>"+messageBody;
    }
}