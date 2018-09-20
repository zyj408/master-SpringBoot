package com.huawei.master.app.controller.dto.request;

import java.util.List;

public class BookRelation {
    private String bookId;

    private List<String> chapterIds;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public List<String> getChapterIds() {
        return chapterIds;
    }

    public void setChapterIds(List<String> chapterIds) {
        this.chapterIds = chapterIds;
    }
}
