package com.huawei.master.app.service;

import com.huawei.master.app.controller.dto.request.BookQueryReq;
import com.huawei.master.app.domain.Book;

import java.util.List;

public interface BookService {

    void relate(String bookId, List<String> chapterIds);

    void addScore(String bookId, Integer value);

    List<Book> query(BookQueryReq bookQueryReq);
}
