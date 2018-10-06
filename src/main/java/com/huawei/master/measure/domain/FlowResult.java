package com.huawei.master.measure.domain;

import org.springframework.data.annotation.Id;

public class FlowResult {

    @Id
    private String id;

    /**
     * 水表编号
     */
    private String no;

    /**
     * 流量大小
     */
    private Float flow;

    /**
     * 用水量
     */
    private Float volume;

    /**
     * 记录时间
     */
    private Long time;

    /**
     * 起始值
     */
    private Float start;

    /**
     * 结束值
     */
    private Float end;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Float getFlow() {
        return flow;
    }

    public void setFlow(Float flow) {
        this.flow = flow;
    }

    public Float getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        this.volume = volume;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Float getStart() {
        return start;
    }

    public void setStart(Float start) {
        this.start = start;
    }

    public Float getEnd() {
        return end;
    }

    public void setEnd(Float end) {
        this.end = end;
    }
}
