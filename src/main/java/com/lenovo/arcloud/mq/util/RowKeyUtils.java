package com.lenovo.arcloud.mq.util;

import static com.lenovo.arcloud.mq.util.BytesUtils.LONG_BYTE_LENGTH;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
public class RowKeyUtils {

    public static byte[] concatFixedByteAndLong(byte[] fixedBytes, int maxFixedLength, long l) {
        if (fixedBytes == null) {
            throw new NullPointerException("fixedBytes must not null");
        }
        if (fixedBytes.length > maxFixedLength) {
            throw new IndexOutOfBoundsException("fixedBytes.length too big. length:" + fixedBytes.length);
        }
        byte[] rowKey = new byte[maxFixedLength + LONG_BYTE_LENGTH];
        BytesUtils.writeBytes(rowKey, 0, fixedBytes);
        BytesUtils.writeLong(l, rowKey, maxFixedLength);
        return rowKey;
    }
}
