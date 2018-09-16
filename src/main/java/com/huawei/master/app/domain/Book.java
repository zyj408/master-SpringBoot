package com.huawei.master.app.domain;

import org.springframework.data.annotation.Id;

public class Book {

    @Id
    private String id;

    private String picUrl;

    private String description;

    private String[] chapters;

    private Double score;

    private Double price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getChapters() {
        return chapters;
    }

    public void setChapters(String[] chapters) {
        this.chapters = chapters;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}