package com.huawei.master.app.controller.dto.request;

public class BookScoreReq {

    private String bookId;

    private Integer score;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}