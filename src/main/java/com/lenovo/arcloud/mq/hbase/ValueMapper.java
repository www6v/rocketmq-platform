/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.hbase;

/***
 * Description
 *
 *
 * @since 2017/3/29
 *
 */
public interface ValueMapper<T> {
    byte[] mapValue(T value);
}