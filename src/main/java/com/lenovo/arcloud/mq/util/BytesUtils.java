/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.util;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
public class BytesUtils {
    public static final int LONG_BYTE_LENGTH = 8;

    public static int writeBytes(final byte[] buffer, int bufferOffset, final byte[] srcBytes) {
        if (srcBytes == null) {
            throw new NullPointerException("srcBytes must not be null");
        }
        return writeBytes(buffer, bufferOffset, srcBytes, 0, srcBytes.length);
    }

    public static int writeBytes(final byte[] buffer, final int bufferOffset, final byte[] srcBytes,
        final int srcOffset, final int srcLength) {
        if (buffer == null) {
            throw new NullPointerException("buffer must not be null");
        }
        if (srcBytes == null) {
            throw new NullPointerException("stringBytes must not be null");
        }
        if (bufferOffset < 0) {
            throw new IndexOutOfBoundsException("negative bufferOffset:" + bufferOffset);
        }
        if (srcOffset < 0) {
            throw new IndexOutOfBoundsException("negative srcOffset offset:" + srcOffset);
        }
        System.arraycopy(srcBytes, srcOffset, buffer, bufferOffset, srcLength);
        return bufferOffset + srcLength;
    }

    public static int writeLong(final long value, final byte[] buf, int offset) {
        if (buf == null) {
            throw new NullPointerException("buf must not be null");
        }
        if (offset < 0) {
            throw new IndexOutOfBoundsException("negative offset:" + offset);
        }
        if (buf.length < offset + LONG_BYTE_LENGTH) {
            throw new IndexOutOfBoundsException("buf.length is too small. buf.length:" + buf.length + " offset:" + (offset + 8));
        }
        buf[offset++] = (byte)(value >> 56);
        buf[offset++] = (byte)(value >> 48);
        buf[offset++] = (byte)(value >> 40);
        buf[offset++] = (byte)(value >> 32);
        buf[offset++] = (byte)(value >> 24);
        buf[offset++] = (byte)(value >> 16);
        buf[offset++] = (byte)(value >> 8);
        buf[offset++] = (byte)value;
        return offset;
    }
}