package com.huawei.master.plc;

public interface PlcService {

    boolean register(String uuid, String account, String password);

    boolean cancel(String uuid);

    String handle(String uuid, String data);
}
