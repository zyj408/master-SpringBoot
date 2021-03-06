package com.huawei.master.measure.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huawei.master.core.bean.Entity;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

public class Procedure extends Entity {

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

    /**
     * 最近上报时间
     */
    private Long lastTime;
    /**
     * 测试结果
     */
    @DBRef
    @JsonIgnore
    private List<FlowResult> results;

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

    public List<FlowResult> getResults() {
        return results;
    }

    public void setResults(List<FlowResult> results) {
        this.results = results;
    }

    public Long getLastTime() {
        return lastTime;
    }

    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }
}
