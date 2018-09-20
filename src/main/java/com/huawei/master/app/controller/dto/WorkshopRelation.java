package com.huawei.master.app.controller.dto;

import java.util.List;

public class WorkshopRelation {
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
