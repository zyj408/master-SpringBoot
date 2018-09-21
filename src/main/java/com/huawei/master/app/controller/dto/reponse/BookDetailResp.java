package com.huawei.master.app.controller.dto.reponse;

import com.huawei.master.app.domain.Book;
import com.huawei.master.app.domain.Chapter;
import com.huawei.master.app.domain.Score;

import java.util.List;

public class BookDetailResp {

    private String id;

    private String name;

    private String picUrl;

    private String description;

    private Float price;

    private List<Chapter> chapters;

    private Score score;

    public BookDetailResp() {
    }

    public BookDetailResp(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.picUrl = book.getPicUrl();
        this.description = book.getDescription();
        this.price = book.getPrice();
        this.chapters = book.getChapters();
        this.score = book.getScore();
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
