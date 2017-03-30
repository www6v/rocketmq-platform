package com.lenovo.arcloud.mq.dao;

import com.lenovo.arcloud.mq.model.ImageObj;

import java.util.List;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
public interface ImageDao {

    void insert(ImageObj image);

    void insert(List<ImageObj> imageObjList);
}
