package com.lenovo.arcloud.mq.model;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/27
 *
 */
public class RECExecutionHistory extends RECExecutionSince {
    /*
    数据：
        执行流的信息
        nodes:对象数组，存放执行流中执行的所有job的执行结果
           {
              "project" : "Job-Test",
              "updateTime" : 1472729400134,
              "type" : null,
              "attempt" : 0,
              "execid" : 27,
              "submitTime" : 1472729907098,
              "nodes" : [ {
                "nestedId" : "bar",
                "in" : [ "foo" ],
                "startTime" : 1472729344346,
                "updateTime" : 1472729400064,
                "id" : "bar",
                "endTime" : 1472729400053,
                "type" : "command",
                "attempt" : 0,
                "status" : "KILLED"
              }, {
                "nestedId" : "foo",
                "startTime" : 1472729324309,
                "updateTime" : 1472729344345,
                "id" : "foo",
                "endTime" : 1472729344339,
                "type" : "command",
                "attempt" : 0,
                "status" : "SUCCEEDED"
              } ],
              "nestedId" : "bar",
              "submitUser" : "azkaban",
              "startTime" : 1472729324295,
              "id" : "bar",
              "endTime" : 1472729400070,
              "projectId" : 1,
              "flowId" : "bar",
              "flow" : "bar",
              "status" : "KILLED"
           }

     */

    public String getProject() {
        if (null == getJsonObject()) {
            return null;
        }
        String project = getJsonObject().getString("project");
        return project;
    }
    public String getType(){
        if(null == getJsonObject()){
            return null;
        }
        String type = getJsonObject().getString("type");
        return type;
    }
    public long getSubmitTime(){
        if(null == getJsonObject()){
            return 0L;
        }
        long time = getJsonObject().getLong("submitTime");
        return time;
    }

    public String getExecId(){
        if(null == getJsonObject()){
            return null;
        }
        String execId = getJsonObject().getString("execid");
        return execId;
    }

    public String getProjectId(){
        if(null == getJsonObject()){
            return null;
        }
        String projectId = getJsonObject().getString("projectId");
        return projectId;
    }

    public String getFlowId(){
        if(null == getJsonObject()){
            return null;
        }
        String flowId = getJsonObject().getString("flowId");
        return flowId;
    }
}
