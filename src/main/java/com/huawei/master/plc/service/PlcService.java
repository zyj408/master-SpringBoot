package com.huawei.master.plc.service;

import com.huawei.master.plc.bean.PlcStatus;

public interface PlcService {

    boolean register(String uuid, String account, String password);

    boolean cancel(String uuid);

    String handle(String uuid, String data);

    PlcStatus getPlcStatus();



}
