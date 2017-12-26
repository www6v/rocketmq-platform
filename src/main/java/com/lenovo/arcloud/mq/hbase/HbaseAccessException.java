/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.hbase;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
public class HbaseAccessException extends RuntimeException {
    public HbaseAccessException() {
    }

    public HbaseAccessException(String message) {
        super(message);
    }

    public HbaseAccessException(Throwable cause) {
        super(cause);
    }

    public HbaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }

}