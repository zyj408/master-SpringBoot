package com.huawei.master.plc.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class PlcRecent {

    private long timestamp;

    private int up;

    private int down;

    private int online;

    private Map<String, AtomicInteger> upDetail = new HashMap<String, AtomicInteger>();

    private Map<String, AtomicInteger> downDetail = new HashMap<String, AtomicInteger>();

    public PlcRecent() {
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public Map<String, AtomicInteger> getUpDetail() {
        return upDetail;
    }

    public void setUpDetail(Map<String, AtomicInteger> upDetail) {
        this.upDetail = upDetail;
    }

    public Map<String, AtomicInteger> getDownDetail() {
        return downDetail;
    }

    public void setDownDetail(Map<String, AtomicInteger> downDetail) {
        this.downDetail = downDetail;
    }

    public void up(String plcName)
    {
        up++;
        AtomicInteger i = upDetail.computeIfAbsent(plcName, k -> new AtomicInteger(0));
        i.incrementAndGet();
    }

    public void down(String plcName)
    {
        down++;
        AtomicInteger i = downDetail.computeIfAbsent(plcName, k -> new AtomicInteger(0));
        i.incrementAndGet();
    }
}
