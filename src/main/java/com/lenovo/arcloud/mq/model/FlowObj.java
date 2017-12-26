/*
 * Copyright 2009-2017 Lenovo Software, Inc. All rights reserved.
 */
package com.lenovo.arcloud.mq.model;

/***
 * Description
 *
 * @author zhulc1@lenovo.com
 * @since 2017/3/22
 *
 */
public class FlowObj {
    public FlowObj() {
    }

    public FlowObj(String projectName, Long projectId, String flowName, String scheduleTime, String scheduleDate) {
        this.projectName = projectName;
        this.projectId = projectId;
        this.flowName = flowName;
        this.scheduleTime = scheduleTime;
        this.scheduleDate = scheduleDate;
    }

    private String projectName;
    private Long projectId;
    private String flowName;
    private String scheduleTime;
    private String scheduleDate;
    private String isRecurring;
    private String period;
    private Integer periodVal;

    private String[] disabled;
    private String successEmails;
    private String failureEmails;
    private boolean successOverride;
    private boolean failOverride;
    private boolean notifyFailFirst;
    private boolean notifyFailLast;
    private String failAction;
    private String concurrentOption;

    public String[] getDisabled() {
        return disabled;
    }

    public void setDisabled(String[] disabled) {
        this.disabled = disabled;
    }

    public String getSuccessEmails() {
        return successEmails;
    }

    public void setSuccessEmails(String successEmails) {
        this.successEmails = successEmails;
    }

    public String getFailureEmails() {
        return failureEmails;
    }

    public void setFailureEmails(String failureEmails) {
        this.failureEmails = failureEmails;
    }

    public boolean isSuccessOverride() {
        return successOverride;
    }

    public void setSuccessOverride(boolean successOverride) {
        this.successOverride = successOverride;
    }

    public boolean isFailOverride() {
        return failOverride;
    }

    public void setFailOverride(boolean failOverride) {
        this.failOverride = failOverride;
    }

    public boolean isNotifyFailFirst() {
        return notifyFailFirst;
    }

    public void setNotifyFailFirst(boolean notifyFailFirst) {
        this.notifyFailFirst = notifyFailFirst;
    }

    public boolean isNotifyFailLast() {
        return notifyFailLast;
    }

    public void setNotifyFailLast(boolean notifyFailLast) {
        this.notifyFailLast = notifyFailLast;
    }

    public String getFailAction() {
        return failAction;
    }

    public void setFailAction(String failAction) {
        this.failAction = failAction;
    }

    public String getConcurrentOption() {
        return concurrentOption;
    }

    public void setConcurrentOption(String concurrentOption) {
        this.concurrentOption = concurrentOption;
    }

    public String getProjectName() {
        return projectName;
    }

    public FlowObj setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public Long getProjectId() {
        return projectId;
    }

    public FlowObj setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getFlowName() {
        return flowName;
    }

    public FlowObj setFlowName(String flowName) {
        this.flowName = flowName;
        return this;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public FlowObj setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
        return this;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public FlowObj setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
        return this;
    }

    public String getIsRecurring() {
        return isRecurring;
    }

    public FlowObj setIsRecurring() {
        this.isRecurring = "on";
        return this;
    }

    public String getPeriod() {
        return period;
    }

    public FlowObj setPeriod(String period) {
        this.period = period;
        return this;
    }

    public Integer getPeriodVal() {
        return periodVal;
    }

    public FlowObj setPeriodVal(Integer periodVal) {
        this.periodVal = periodVal;
        return this;
    }

    @Override
    public String toString() {
        return "FlowObj{" +
            "projectName='" + projectName + '\'' +
            ", projectId=" + projectId +
            ", flowName='" + flowName + '\'' +
            ", scheduleTime='" + scheduleTime + '\'' +
            ", scheduleDate='" + scheduleDate + '\'' +
            ", is_recurring='" + isRecurring + '\'' +
            ", period='" + period + '\'' +
            '}';
    }
}