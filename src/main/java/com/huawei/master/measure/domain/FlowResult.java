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
     * 记录时间
     */
    private Long time;

    /**
     * 测量结果q1
     */
    private ResultCell q1;

    /**
     * 测量结果q2
     */
    private ResultCell q2;

    /**
     * 测量结果q3
     */
    private ResultCell q3;


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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public ResultCell getQ1() {
        return q1;
    }

    public void setQ1(ResultCell q1) {
        this.q1 = q1;
    }

    public ResultCell getQ2() {
        return q2;
    }

    public void setQ2(ResultCell q2) {
        this.q2 = q2;
    }

    public ResultCell getQ3() {
        return q3;
    }

    public void setQ3(ResultCell q3) {
        this.q3 = q3;
    }

    public static class ResultCell {
        /**
         * 流量大小
         */
        private Float flow;

        /**
         * 用水量
         */
        private Float volume;

        /**
         * 起始值
         */
        private Float start;

        /**
         * 结束值
         */
        private Float end;

        /**
         * 误差
         */
        private Float deviation;

        public ResultCell(Float flow, Float volume, Float start, Float end) {
            this.flow = flow;
            this.volume = volume;
            this.start = start;
            this.end = end;
            this.deviation = (end - start) / volume;
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
