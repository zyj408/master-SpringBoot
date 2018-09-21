package com.huawei.master.app.controller.dto.request;

import java.util.List;

public class BookDetailReq {

    private List<String> bookIds;

    public List<String> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<String> bookIds) {
        this.bookIds = bookIds;
    }
}
