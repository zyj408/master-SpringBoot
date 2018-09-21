package com.huawei.master.app.controller.dto.request;

import java.util.List;

public class WorkshopDetailReq {
    public List<String> getWorkshopIds() {
        return workshopIds;
    }

    public void setWorkshopIds(List<String> workshopIds) {
        this.workshopIds = workshopIds;
    }

    private List<String> workshopIds;


}
