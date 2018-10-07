package com.huawei.master.measure.service.impl;

import com.huawei.master.measure.service.MeasureService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service("measureService")
public class MeasureServiceImpl implements MeasureService {

    /**
     * 在线设备
     */
    private ConcurrentHashMap<String, String> onlineUsers = new ConcurrentHashMap<String, String>();

    @Override
    public void register(String userId) {

    }
}
