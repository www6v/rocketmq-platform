package com.lenovo.arcloud.mq.hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.util.Bytes;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
public class HbaseTables {

    public static final TableName IMAGE = TableName.valueOf("Image");
    //colfamily
    public static final byte[] IMAGE_META_INFO = Bytes.toBytes("ImgMeta");
    public static final byte[] IMAGE_META_INFO_NAME = Bytes.toBytes("name");
    public static final byte[] IMAGE_META_INFO_EXT = Bytes.toBytes("ext");
    public static final byte[] IMAGE_META_INFO_PATH = Bytes.toBytes("path");
    public static final byte[] IMAGE_META_INFO_SIZE = Bytes.toBytes("size");
    public static final byte[] IMAGE_META_INFO_CKSUM = Bytes.toBytes("checkSum");

    //colfamily
    public static final byte[] IMAGE_MOB = Bytes.toBytes("ImgMob");
}
