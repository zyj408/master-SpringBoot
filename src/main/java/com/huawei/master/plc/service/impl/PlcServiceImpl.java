package com.huawei.master.plc.service.impl;

import com.huawei.master.core.utils.JacksonMapper;
import com.huawei.master.plc.bean.*;
import com.huawei.master.plc.dao.PlcRepository;
import com.huawei.master.plc.domain.Plc;
import com.huawei.master.plc.service.PlcService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service("plcService")
public class PlcServiceImpl implements PlcService {

    private Map<String, PlcOnline> online = new ConcurrentHashMap<String, PlcOnline>();

    private Deque<PlcRecent> recentDeque = new ArrayDeque<PlcRecent>();

    /**
     * 当前统计
     */
    private PlcRecent recent = new PlcRecent();


    @Autowired
    private PlcRepository plcRepository;

    @Override
    public boolean register(String uuid, String account, String password) {
        Plc plc = plcRepository.findByName(account);
        if (plc == null || StringUtils.equals(plc.getPassword(), password)) {
            return false;
        }
        online.put(uuid, new PlcOnline(plc));
        return true;
    }

    @Override
    public boolean cancel(String uuid) {
        PlcOnline plcOnline = online.get(uuid);
        if (plcOnline == null) {
            return false;
        } else {
            online.remove(uuid);
            return true;
        }

    }

    @Override
    public String handle(String uuid, String data) {
        PlcOnline plcOnline = online.get(uuid);
        if (plcOnline == null) {
            PlcAuth plcAuth = JacksonMapper.deserialize(data, PlcAuth.class);
            if (register(uuid, plcAuth.getAccount(), plcAuth.getToken())) {
                Plc plc = plcRepository.findByName(plcAuth.getAccount());
                return "{\"rule\":\"" + plc.getRule() + "\"}";
            }
        } else {
            PlcIO plcIO = JacksonMapper.deserialize(data, PlcIO.class);
            plcOnline.up(plcIO.getX());
            recent.up(plcOnline.getPlc().getName());







            plcOnline.down();
            recent.down(plcOnline.getPlc().getName());
            return "{\"y\":" + 1243 + ",\"time\":" + System.currentTimeMillis() + "}";
        }


        return null;
    }

    @Override
    public PlcStatus getPlcStatus() {
        PlcStatus status = new PlcStatus();


        status.setOnline(online.size());
        status.setTotal(new Long(plcRepository.count()).intValue());

        int totalUp = 0;
        int totalDown = 0;
        for(Map.Entry<String, PlcOnline> entry : online.entrySet())
        {
            PlcOnline online = entry.getValue();
            totalUp += online.getUpCount();
            totalDown += online.getDownCount();

        }

        status.setUp(totalUp);
        status.setDown(totalDown);



        return status;
    }


}
