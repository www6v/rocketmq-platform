package com.lenovo.arcloud.mq.hbase;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
public class HbaseSystemException extends RuntimeException{
    public HbaseSystemException(Exception cause) {
        super(cause.getMessage(), cause);
    }
}
