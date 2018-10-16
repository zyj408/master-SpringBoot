package com.huawei.master.measure.controller.dto;

import java.util.List;

public class ReportResultReq {

    private String procedure;

    private Float flow;

    private Float volume;

    private List<MeterResult> meterResults;

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
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

    public List<MeterResult> getMeterResults() {
        return meterResults;
    }

    public void setMeterResults(List<MeterResult> meterResults) {
        this.meterResults = meterResults;
    }

    public static class MeterResult {
        private String no;

        private Float start;

        private Float end;

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
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
}
