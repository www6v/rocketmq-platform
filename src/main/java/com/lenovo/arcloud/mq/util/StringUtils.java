/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.util;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/22
 *
 */
public class StringUtils {
    public static boolean isEmpty(Object str) {
        return str == null || "".equals(str);
    }
}