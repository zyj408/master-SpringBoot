package com.huawei.master.user.controller.dto.request;

public class EnableReq {
    private String account;

    private Boolean enable;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
