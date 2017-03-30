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
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/22
 *
 */

@Service
public class ExeFlowServiceImpl implements ExeFlowService {
    private static Logger logger = Logger.getLogger(ExeFlowService.class);
    private HttpResponse<JsonNode> response;

    public Object Fetchflows(String projectName) {
        try {
            response = RequestUtils.get(RequestUrl.MANAGER).queryString("ajax", "fetchprojectflows")
                    .queryString("project", projectName).asJson();

        } catch (UnirestException e) {
            logger.error("网络异常！");
            throw new IllegalArgumentException("网络异常！", e);
        }
        return response.getBody().getObject().get("flows");
    }

    @Override
    public JSONObject ExecuteFlow(FlowObj obj,Map<String,String> flowProps) {
        try{
            HttpRequest request = RequestUtils.get(RequestUrl.EXECUTOR)
                    .queryString("ajax","executeFlow")
                    .queryString("project",obj.getProjectName())
                    .queryString("flow",obj.getFlowName())
                    .queryString("disabled",obj.getDisabled())
                    .queryString("successEmails",obj.getSuccessEmails())
                    .queryString("failureEmails",obj.getFailureEmails())
                    .queryString("successEmailsOverride",obj.isSuccessOverride())
                    .queryString("failureEmailsOverride",obj.isFailOverride())
                    .queryString("notifyFailUreFirst",obj.isNotifyFailFirst())
                    .queryString("notifyFailureLast",obj.isNotifyFailLast())
                    .queryString("failureAction",obj.getFailAction())
                    .queryString("concurrentOption",obj.getConcurrentOption());
            for (String key : flowProps.keySet()) {
                request.queryString("flowOverride["+key+"]",flowProps.get(key));
            }
            response = request.asJson();
        }catch (UnirestException e){
            logger.error("计算已开始");
            throw new IllegalArgumentException("计算已开始",e);
        }
        return response.getBody().getObject();
    }

    /**
     * 获取执行流中所有的job信息
     * (job执行的依赖关系存放在json的in字段中)
     */
    @Override
    public RECExecutionHistory fetchFlowExecution(String execId) {
        HttpResponse<String> response;
        try {
            response = RequestUtils.get(RequestUrl.EXECUTOR)
                    .queryString("execid",execId)
                    .queryString("ajax","fetchexecflow")
                    .asString();
        } catch (UnirestException e) {
            logger.error("查询执行流中的job列表失败", e);
            throw new IllegalStateException("查询执行流中的job列表失败", e);
        }
        String responseData = response.getBody();


        //返回复杂的json数据
        RECExecutionHistory recExecutionHistory = new RECExecutionHistory();
        recExecutionHistory.setJsonObject(new JSONObject(responseData));

        return recExecutionHistory;
    }
}
