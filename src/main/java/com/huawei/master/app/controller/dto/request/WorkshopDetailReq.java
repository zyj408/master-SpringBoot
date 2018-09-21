package com.huawei.master.app.controller.dto.request;

import java.util.List;

public class WorkshopDetailReq {

    private List<String> workshopIds;

    public List<String> getWorkshopIds() {
        return workshopIds;
    }

    public void setWorkshopIds(List<String> workshopIds) {
        this.workshopIds = workshopIds;
    }

}
