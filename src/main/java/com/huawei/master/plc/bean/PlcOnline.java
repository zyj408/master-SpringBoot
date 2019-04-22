package com.huawei.master.plc.bean;

import com.huawei.master.plc.domain.Plc;

public class PlcOnline {

    private Plc plc;

    private long lastTime;

    private long upCount;

    private long downCount;

    private long x;

    private long y;

    public PlcOnline(Plc plc) {
        this.plc = plc;
        lastTime = System.currentTimeMillis();
        upCount = 0;
    }

    public Plc getPlc() {
        return plc;
    }

    public void setPlc(Plc plc) {
        this.plc = plc;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public long getUpCount() {
        return upCount;
    }

    public void setUpCount(long upCount) {
        this.upCount = upCount;
    }

    public long getDownCount() {
        return downCount;
    }

    public void setDownCount(long downCount) {
        this.downCount = downCount;
    }

    public void up(int x)
    {
        this.upCount++;
        this.x = x;
    }

    public void down()
    {
        this.downCount++;
        this.lastTime = System.currentTimeMillis();
    }

}
