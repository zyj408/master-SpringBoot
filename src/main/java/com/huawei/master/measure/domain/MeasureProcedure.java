package com.huawei.master.measure.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

public class MeasureProcedure {

    @Id
    private String id;

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
    private Long standard;

    /**
     * 测试结果
     */
    @DBRef
    private List<FlowResult> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Long getStandard() {
        return standard;
    }

    public void setStandard(Long standard) {
        this.standard = standard;
    }

    public List<FlowResult> getResults() {
        return results;
    }

    public void setResults(List<FlowResult> results) {
        this.results = results;
    }
}
