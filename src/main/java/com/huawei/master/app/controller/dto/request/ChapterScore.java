package com.huawei.master.app.controller.dto.request;

public class ChapterScore {

    private String chapterId;

    private Integer score;

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
