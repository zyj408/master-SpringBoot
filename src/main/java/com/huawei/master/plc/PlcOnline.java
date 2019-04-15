package com.huawei.master.plc;

import com.huawei.master.plc.domain.Plc;

public class PlcOnline {

    private Plc plc;

    private long lastTime;

    private long updateCount;

    private long x;

    private long y;

    public PlcOnline(Plc plc) {
        this.plc = plc;
        lastTime = System.currentTimeMillis();
        updateCount = 0;
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

    public long getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(long updateCount) {
        this.updateCount = updateCount;
    }
}
