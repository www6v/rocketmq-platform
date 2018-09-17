/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.service;

import com.lenovo.arcloud.mq.model.FlowObj;
import com.lenovo.arcloud.mq.model.RECExecutionHistory;
import org.json.JSONObject;

import java.util.Map;

/***
 * Description
 *
 *
 * @since 2017/3/22
 *
 */
public interface ExeFlowService {

    Object Fetchflows(String projectName);

    JSONObject ExecuteFlow(FlowObj obj, Map<String, String> flowProps);

    RECExecutionHistory fetchFlowExecution(String execId);
}