/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Table;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/29
 *
 */
public interface TableFactory {
    Table getTable(TableName tableName);

    void releaseTable(final Table table);
}