package com.huawei.master.measure.controller.dto;

import com.huawei.master.core.bean.Page;

public class QueryResultReq {

    private String procedure;

    private Page page;

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
