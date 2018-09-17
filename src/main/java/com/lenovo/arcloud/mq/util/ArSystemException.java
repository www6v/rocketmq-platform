/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.util;

/***
 * Description
 *
 *
 * @since 2017/3/30
 *
 */
public class ArSystemException extends RuntimeException {
    public ArSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArSystemException() {

    }

    public ArSystemException(String message) {
        super(message);
    }
}