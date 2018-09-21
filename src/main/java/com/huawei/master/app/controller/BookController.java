package com.huawei.master.app.controller;

import com.google.common.collect.Lists;
import com.huawei.master.app.controller.dto.reponse.BookDetailResp;
import com.huawei.master.app.controller.dto.reponse.WorkshopDetailResp;
import com.huawei.master.app.controller.dto.request.BookDetailReq;
import com.huawei.master.app.controller.dto.request.BookRelationReq;
import com.huawei.master.app.controller.dto.request.BookScoreReq;
import com.huawei.master.app.dao.BookRepository;
import com.huawei.master.app.domain.Book;
import com.huawei.master.app.service.BookService;
import com.huawei.master.core.common.AbstractController;
import com.huawei.master.core.utils.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "书籍管理", description = "书籍管理")
@RequestMapping(value = "/book")
public class BookController extends AbstractController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    // 书籍关联章节
    @ApiOperation(value = "书籍关联章节", notes = "书籍关联章节")
    @PostMapping("/relation")
    @ApiImplicitParam(name = "relation", value = "关联信息", required = true, dataType = "BookRelationReq")
    public Object relate(@RequestBody BookRelationReq relation, ModelMap modelMap) {

        Assert.notNull(relation.getBookId(), "BOOK_ID");
        Assert.notEmpty(relation.getChapterIds(), "CHAPTER_ID");

        bookService.relate(relation.getBookId(), relation.getChapterIds());
        return setSuccessModelMap(modelMap);
    }

    // 讨论会详情
    @ApiOperation(value = "书籍详情", notes = "书籍详情")
    @PostMapping("/detail")
    @ApiImplicitParam(name = "bookDetailReq", value = "书籍IDs", required = true, dataType = "BookDetailReq")
    public Object detail(@RequestBody BookDetailReq bookDetailReq, ModelMap modelMap) {
        Assert.notEmpty(bookDetailReq.getBookIds(), "BOOK_IDS");
        ArrayList<Book> books = Lists.newArrayList(bookRepository.findAllById(bookDetailReq.getBookIds()));

        List<BookDetailResp> bookDetails = books.stream().map(b -> new BookDetailResp(b)).collect(Collectors.toList());
        return setSuccessModelMap(modelMap, bookDetails);
    }

    // 书籍评分
    @ApiOperation(value = "书籍评分", notes = "书籍评分")
    @PostMapping("/score")
    @ApiImplicitParam(name = "score", value = "评分信息", required = true, dataType = "BookScoreReq")
    public Object score(@RequestBody BookScoreReq score, ModelMap modelMap) {

        Assert.notNull(score.getBookId(), "BOOK_ID");
        Assert.max(score.getScore(), 5, "BOOK_SCORE");

        bookService.addScore(score.getBookId(), score.getScore());
        return setSuccessModelMap(modelMap);
    }
}
