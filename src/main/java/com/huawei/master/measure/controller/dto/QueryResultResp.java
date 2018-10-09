package com.huawei.master.measure.controller.dto;

import com.huawei.master.measure.domain.FlowResult;

public class QueryResultResp {
    private String no;

    private Long time;

    private Cell q1;

    private Cell q2;

    private Cell q3;

    public QueryResultResp(FlowResult r) {
        this.no = r.getNo();
        this.time = r.getTime();
        this.q1 = r.getQ1() != null ? new Cell(r.getQ1()) : null;
        this.q2 = r.getQ2() != null ? new Cell(r.getQ2()) : null;
        this.q3 = r.getQ3() != null ? new Cell(r.getQ3()) : null;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Cell getQ1() {
        return q1;
    }

    public void setQ1(Cell q1) {
        this.q1 = q1;
    }

    public Cell getQ2() {
        return q2;
    }

    public void setQ2(Cell q2) {
        this.q2 = q2;
    }

    public Cell getQ3() {
        return q3;
    }

    public void setQ3(Cell q3) {
        this.q3 = q3;
    }

    public static class Cell
    {
        private Float flow;

        private Float volume;

        private Float start;

        private Float end;

        private Float deviation;

        public Cell(FlowResult.ResultCell c) {
            this.flow = c.getFlow();
            this.volume = c.getVolume();
            this.start = c.getStart();
            this.end = c.getEnd();
            this.deviation = c.getDeviation();
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

        public Float getDeviation() {
            return deviation;
        }

        public void setDeviation(Float deviation) {
            this.deviation = deviation;
        }
    }
}
