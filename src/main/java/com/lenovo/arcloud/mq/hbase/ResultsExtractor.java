package com.lenovo.arcloud.mq.hbase;

import org.apache.hadoop.hbase.client.ResultScanner;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
public interface ResultsExtractor<T> {

    T extractData(ResultScanner results) throws Exception;
}
