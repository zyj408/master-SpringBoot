package com.huawei.master.app.controller.dto.reponse;

import com.huawei.master.app.domain.Chapter;
import com.huawei.master.app.domain.Workshop;

import java.util.List;

public class WorkshopDetailResp {

    private String id;

    private String name;

    private String picUrl;

    private String description;

    private String time;

    private String address;

    private String host;

    private List<Chapter> chapters;

    public WorkshopDetailResp() {
    }

    public WorkshopDetailResp(Workshop workshop) {
        this.id = workshop.getId();
        this.name = workshop.getName();
        this.picUrl = workshop.getPicUrl();
        this.description = workshop.getDescription();
        this.time = workshop.getTime();
        this.address = workshop.getAddress();
        this.host = workshop.getHost();
        this.chapters = workshop.getChapters();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
