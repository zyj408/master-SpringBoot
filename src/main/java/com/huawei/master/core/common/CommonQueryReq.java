package com.huawei.master.core.common;

import java.util.HashMap;
import java.util.Map;

public class CommonQueryReq {
    private Map<String, String> condition = new HashMap<String, String>();

    private Page page;

    public Map<String, String> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, String> condition) {
        this.condition = condition;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
