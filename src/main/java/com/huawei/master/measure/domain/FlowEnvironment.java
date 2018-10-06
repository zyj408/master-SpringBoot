package com.huawei.master.measure.domain;

import org.springframework.data.annotation.Id;

public class FlowEnvironment {

    @Id
    private String id;

    /**
     * 型号规格
     */
    private String model;

    /**
     * Q3
     */
    private Float q3;

    /**
     * Q31
     */
    private Float q31;

    /**
     * Q21
     */
    private Float q21;

    /**
     * 设备型号
     */
    private String device;

    /**
     * 室温
     */
    private Float indoorTemperature;

    /**
     * 水温
     */
    private Float waterTemperature;

    /**
     * 水压
     */
    private Float waterPressure;

    /**
     * 备注
     */
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Float getQ3() {
        return q3;
    }

    public void setQ3(Float q3) {
        this.q3 = q3;
    }

    public Float getQ31() {
        return q31;
    }

    public void setQ31(Float q31) {
        this.q31 = q31;
    }

    public Float getQ21() {
        return q21;
    }

    public void setQ21(Float q21) {
        this.q21 = q21;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Float getIndoorTemperature() {
        return indoorTemperature;
    }

    public void setIndoorTemperature(Float indoorTemperature) {
        this.indoorTemperature = indoorTemperature;
    }

    public Float getWaterTemperature() {
        return waterTemperature;
    }

    public void setWaterTemperature(Float waterTemperature) {
        this.waterTemperature = waterTemperature;
    }

    public Float getWaterPressure() {
        return waterPressure;
    }

    public void setWaterPressure(Float waterPressure) {
        this.waterPressure = waterPressure;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
