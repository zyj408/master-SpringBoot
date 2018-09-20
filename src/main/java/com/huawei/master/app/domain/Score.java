package com.huawei.master.app.domain;

import org.springframework.data.annotation.Id;

public class Score {

    @Id
    private String id;

    private Integer sum;

    private Integer times;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}
