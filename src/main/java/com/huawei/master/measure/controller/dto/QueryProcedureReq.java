package com.huawei.master.measure.controller.dto;

import com.huawei.master.core.bean.Page;

public class QueryProcedureReq {

    private String status;

    private String name;

    private Page page;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
