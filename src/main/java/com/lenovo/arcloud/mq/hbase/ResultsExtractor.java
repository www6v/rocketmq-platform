/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.hbase;

import org.apache.hadoop.hbase.client.ResultScanner;

/***
 * Description
 *
 *
 * @since 2017/3/29
 *
 */
public interface ResultsExtractor<T> {

    T extractData(ResultScanner results) throws Exception;
}