package com.huawei.master.measure.controller.dto;

import com.huawei.master.measure.domain.Procedure;

public class QueryProcedureResp {

    /**
     * 名称
     */
    private String name;

    /**
     * 起始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 测量状态
     */
    private String status;

    /**
     * 记录总数
     */
    private Long record;

    /**
     * 合格总数
     */
    private Long nationalQualified;

    /**
     * 合格总数
     */
    private Long factoryQualified;

    public QueryProcedureResp(Procedure procedure) {
        this.name = procedure.getName();
        this.startTime = procedure.getStartTime();
        this.endTime = procedure.getEndTime();
        this.status = procedure.getStatus();
        this.record = procedure.getRecord();
        this.nationalQualified = procedure.getNationalQualified();
        this.factoryQualified = procedure.getFactoryQualified();
    }

    public QueryProcedureResp() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRecord() {
        return record;
    }

    public void setRecord(Long record) {
        this.record = record;
    }

    public Long getNationalQualified() {
        return nationalQualified;
    }

    public void setNationalQualified(Long nationalQualified) {
        this.nationalQualified = nationalQualified;
    }

    public Long getFactoryQualified() {
        return factoryQualified;
    }

    public void setFactoryQualified(Long factoryQualified) {
        this.factoryQualified = factoryQualified;
    }
}
