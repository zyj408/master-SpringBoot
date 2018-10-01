package com.huawei.master.app.controller.dto.request;

import com.huawei.master.core.bean.Page;

public class WorkshopQueryReq {

    private String name;

    private Page page;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
