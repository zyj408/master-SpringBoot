package com.huawei.master.measure.controller.dto;

import java.util.List;
import java.util.Map;

public class ReportResultReq {

    /**
     * 测量过程
     */
    private String procedure;

    /**
     * 测量参数
     */
    private List<MeasureParameter> measureParameters;

    /**
     * 测量结果
     */
    private List<MeterResult> meterResults;

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public List<MeasureParameter> getMeasureParameters() {
        return measureParameters;
    }

    public void setMeasureParameters(List<MeasureParameter> measureParameters) {
        this.measureParameters = measureParameters;
    }

    public List<MeterResult> getMeterResults() {
        return meterResults;
    }

    public void setMeterResults(List<MeterResult> meterResults) {
        this.meterResults = meterResults;
    }

    public static class MeterResult {

        private String meterNo;

        private String parameter;

        private Float start;

        private Float end;

        public String getMeterNo() {
            return meterNo;
        }

        public void setMeterNo(String meterNo) {
            this.meterNo = meterNo;
        }

        public String getParameter() {
            return parameter;
        }

        public void setParameter(String parameter) {
            this.parameter = parameter;
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

    /**
     * 测量参数
     */
    public static class MeasureParameter {

        private String id;

        private Float flow;

        private Float volume;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
    }
}
