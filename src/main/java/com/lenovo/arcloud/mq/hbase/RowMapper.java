/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.hbase;

import org.apache.hadoop.hbase.client.Result;

/***
 * Description
 *
 *
 * @since 2017/3/29
 *
 */
public interface RowMapper<T> {
    T mapRow(Result result, int rowNum) throws Exception;
}