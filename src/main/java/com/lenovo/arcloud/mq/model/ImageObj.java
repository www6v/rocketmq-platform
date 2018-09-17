/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.model;

import lombok.Getter;
import lombok.Setter;

/***
 * Description
 *
 *
 * @since 2017/3/29
 *
 */
public class ImageObj {

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String ext;

    @Getter @Setter
    private String path;

    @Getter @Setter
    private long size;

    @Getter @Setter
    private int checkSum;

    @Getter @Setter
    private long createTime;

    @Getter @Setter
    private Long executionId;

    @Getter @Setter
    private Long imageId;

}