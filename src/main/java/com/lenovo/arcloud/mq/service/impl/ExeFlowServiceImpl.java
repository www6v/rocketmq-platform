/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.service.impl;

import com.lenovo.arcloud.mq.model.FlowObj;
import com.lenovo.arcloud.mq.model.RECExecutionHistory;
import com.lenovo.arcloud.mq.service.ExeFlowService;
import com.lenovo.arcloud.mq.util.RequestUrl;
import com.lenovo.arcloud.mq.util.RequestUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/***
 * Description
 *
 *
 * @since 2017/3/22
 *
 */

@Service
public class ExeFlowServiceImpl implements ExeFlowService {
    private static Logger logger = LoggerFactory.getLogger(ExeFlowService.class);
    private HttpResponse<JsonNode> response;

    public Object Fetchflows(String projectName) {
        try {
            response = RequestUtils.get(RequestUrl.MANAGER).queryString("ajax", "fetchprojectflows")
                .queryString("project", projectName).asJson();

        }
        catch (UnirestException e) {
            logger.error("network exception!");
            throw new IllegalArgumentException("network exception!", e);
        }
        return response.getBody().getObject().get("flows");
    }

    @Override
    public JSONObject ExecuteFlow(FlowObj obj, Map<String, String> flowProps) {
        try {
            HttpRequest request = RequestUtils.get(RequestUrl.EXECUTOR)
                .queryString("ajax", "executeFlow")
                .queryString("project", obj.getProjectName())
                .queryString("flow", obj.getFlowName())
                .queryString("disabled", obj.getDisabled())
                .queryString("successEmails", obj.getSuccessEmails())
                .queryString("failureEmails", obj.getFailureEmails())
                .queryString("successEmailsOverride", obj.isSuccessOverride())
                .queryString("failureEmailsOverride", obj.isFailOverride())
                .queryString("notifyFailUreFirst", obj.isNotifyFailFirst())
                .queryString("notifyFailureLast", obj.isNotifyFailLast())
                .queryString("failureAction", obj.getFailAction())
                .queryString("concurrentOption", obj.getConcurrentOption());
            for (String key : flowProps.keySet()) {
                request.queryString("flowOverride[" + key + "]", flowProps.get(key));
            }
            response = request.asJson();
        }
        catch (UnirestException e) {
            logger.error("compute failure");
            throw new IllegalArgumentException("compute failure", e);
        }
        return response.getBody().getObject();
    }

    /**
     * Fetch all job info of executing flow
     * the dependency of job is included in "in" field of json
     */
    @Override
    public RECExecutionHistory fetchFlowExecution(String execId) {
        HttpResponse<String> response;
        try {
            response = RequestUtils.get(RequestUrl.EXECUTOR)
                .queryString("execid", execId)
                .queryString("ajax", "fetchexecflow")
                .asString();
        }
        catch (UnirestException e) {
            logger.error("fetch job failure", e);
            throw new IllegalStateException("fetch job failure", e);
        }
        String responseData = response.getBody();

        //parse json
        RECExecutionHistory recExecutionHistory = new RECExecutionHistory();
        recExecutionHistory.setJsonObject(new JSONObject(responseData));

        return recExecutionHistory;
    }
}