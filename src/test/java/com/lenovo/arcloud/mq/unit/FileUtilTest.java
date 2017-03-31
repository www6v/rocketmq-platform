/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.unit;

import com.lenovo.arcloud.mq.util.FileUtils;
import org.junit.Test;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/31
 *
 */
public class FileUtilTest {

    @Test
    public void testFileCRC(){
        String filePath = "d:/hbase-server-1.2.4.jar";
        int crc16 = FileUtils.getCRC16ForFile(filePath);
    }

}
