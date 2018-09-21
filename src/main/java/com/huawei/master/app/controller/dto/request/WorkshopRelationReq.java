package com.huawei.master.app.controller.dto.request;

import java.util.List;

public class WorkshopRelationReq {
    private String workshopId;

    private List<String> chapterIds;

    public String getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(String workshopId) {
        this.workshopId = workshopId;
    }

    public List<String> getChapterIds() {
        return chapterIds;
    }

    public void setChapterIds(List<String> chapterIds) {
        this.chapterIds = chapterIds;
    }
}
