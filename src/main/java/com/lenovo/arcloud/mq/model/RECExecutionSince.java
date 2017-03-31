/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.model;

import org.json.JSONArray;
import org.json.JSONObject;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/27
 *
 */
public class RECExecutionSince {
    private JSONObject jsonObject;

    /*
        {
          "nodes" : [ {
            "startTime" : 1472722654417,
            "updateTime" : 1472722764002,
            "id" : "bar",
            "endTime" : 1472722763986,
            "attempt" : 0,
            "status" : "SUCCEEDED"
          }, {
            "startTime" : 1472722634383,
            "updateTime" : 1472722654427,
            "id" : "foo",
            "endTime" : 1472722654410,
            "attempt" : 0,
            "status" : "SUCCEEDED"
          } ],
          "startTime" : 1472722634378,
          "updateTime" : 1472722764007,
          "id" : "bar",
          "endTime" : 1472722764004,
          "attempt" : 0,
          "flow" : "bar",
          "status" : "SUCCEEDED"
        }
         */

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public JSONArray getNodes() {
        if (null == jsonObject) {
            return null;
        }
        JSONArray jsonArray = jsonObject.getJSONArray("nodes");
        return jsonArray;
    }

    public long getStartTime() {
        if (null == jsonObject) {
            return 0L;
        }
        long timeMls = jsonObject.getLong("startTime");
        return timeMls;
    }

    public long getEndTime() {
        if (null == jsonObject) {
            return 0L;
        }
        long timeMls = jsonObject.getLong("endTime");
        return timeMls;
    }

    public long getUpdateTime() {
        if (null == jsonObject) {
            return 0L;
        }
        long timeMls = jsonObject.getLong("updateTime");
        return timeMls;
    }

    public String getId() {
        if (null == jsonObject) {
            return null;
        }
        String id = jsonObject.getString("id");
        return id;
    }

    public long getAttempt() {
        if (null == jsonObject) {
            return 0L;
        }
        long attempt = jsonObject.getLong("attempt");
        return attempt;
    }

    /**
     * return flow name
     */
    public String getFlow() {
        if (null == jsonObject) {
            return null;
        }

        String flowName = jsonObject.getString("flow");
        return flowName;
    }

    public String getStatus() {
        if (null == jsonObject) {
            return null;
        }
        String status = jsonObject.getString("status");
        return status;
    }
}