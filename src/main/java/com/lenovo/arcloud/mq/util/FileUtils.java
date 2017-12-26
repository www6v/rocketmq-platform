/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.util;

import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/30
 *
 */
public class FileUtils {
    public static int getCRC16ForFile(String localFilePath) {
        try {
            File file = new File(localFilePath);
            FileChannel in = new FileInputStream(file).getChannel();
            long size = in.size();
            MappedByteBuffer byteBuffer = in.map(FileChannel.MapMode.READ_ONLY, 0, size);
            int crc16 = Checksum.crc16(Checksum.CRC16_X25, byteBuffer);
            in.close();
            return crc16;
        }
        catch (Exception e) {
            throw new ArSystemException("calculate file crc16 failure", e);
        }
    }

    public static int getCRC16ForHttp(String urlPath) {
        try {
            URL url = new URL(urlPath);
            InputStream inputStream = url.openStream();
            ByteBuffer byteBuffer = ByteBuffer.wrap(ByteStreams.toByteArray(inputStream));
            int crc16 = Checksum.crc16(Checksum.CRC16_X25, byteBuffer);
            inputStream.close();
            return crc16;
        }
        catch (Exception e) {
            throw new ArSystemException("calculate http file crc16 failure", e);
        }
    }

    public static int getCRC16(byte[] byteArr) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArr);
        return Checksum.crc16(Checksum.CRC16_X25, byteBuffer);
    }

    public static String getFileExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1, filePath.length());
    }

    public static String getFileNameByUrl(String url){
        return url.substring(url.lastIndexOf("/") + 1,url.length());
    }

}