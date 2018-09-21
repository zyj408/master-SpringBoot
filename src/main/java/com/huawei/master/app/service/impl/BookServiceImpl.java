package com.huawei.master.app.service.impl;

import com.google.common.collect.Lists;
import com.huawei.master.app.controller.dto.request.BookQueryReq;
import com.huawei.master.app.dao.BookRepository;
import com.huawei.master.app.dao.ChapterRepository;
import com.huawei.master.app.dao.ScoreRepository;
import com.huawei.master.app.domain.Book;
import com.huawei.master.app.domain.Chapter;
import com.huawei.master.app.domain.Score;
import com.huawei.master.app.service.BookService;
import com.huawei.master.core.common.Page;
import com.huawei.master.core.system.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    /**
     * 书本&章节关联操作
     *
     * @param bookId
     * @param chapterIds
     */
    @Override
    public void relate(String bookId, List<String> chapterIds) {
        if (!bookRepository.existsById(bookId)) {
            throw new BusinessException("book is not existed...");
        }
        for (String chapterId : chapterIds) {
            if (!chapterRepository.existsById(chapterId)) {
                throw new BusinessException("chapter [ " + chapterId + " ] is not existed...");
            }
        }

        Book book = bookRepository.findById(bookId).get();
        ArrayList<Chapter> chapters = Lists.newArrayList(chapterRepository.findAllById(chapterIds));

        //书本关联章节
        book.setChapters(chapters);
        bookRepository.save(book);

        chapters.stream().forEach(c -> {
            c.setBook(book);
            chapterRepository.save(c);
        });
    }

    @Override
    public void addScore(String bookId, Integer value) {
        if (!bookRepository.existsById(bookId)) {
            throw new BusinessException("book is not existed...");
        }

        Book book = bookRepository.findById(bookId).get();
        Score score = book.getScore();
        if (score == null) {
            score = new Score(value, 1);
            scoreRepository.save(score);
            book.setScore(score);
            bookRepository.save(book);
        } else {
            score.setSum(score.getSum() + value);
            score.setTimes(score.getTimes() + 1);
            scoreRepository.save(score);
        }
    }

    @Override
    public List<Book> query(BookQueryReq bookQueryReq) {
        String name = bookQueryReq.getName();
        Page page = bookQueryReq.getPage();

        PageRequest pageRequest = PageRequest.of(page.getPage() - 1, page.getRows());
        return bookRepository.findByNameLike(name, pageRequest);
    }
}
