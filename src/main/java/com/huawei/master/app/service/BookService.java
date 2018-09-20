package com.huawei.master.app.service;

import java.util.List;

public interface BookService {

    void relate(String bookId, List<String> chapterIds);

    void addScore(String bookId, Integer value);
}
