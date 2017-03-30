package com.lenovo.arcloud.mq.hbase;

import org.apache.hadoop.hbase.client.Result;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
public interface RowMapper<T> {
    T mapRow(Result result, int rowNum) throws Exception;
}
