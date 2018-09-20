package com.huawei.master.app.controller;

import com.huawei.master.app.controller.dto.Relation;
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

@RestController
@Api(value = "书籍管理", description = "书籍管理")
@RequestMapping(value = "/book")
public class BookController extends AbstractController {

    @Autowired
    private BookService bookService;

    // 书籍关联章节
    @ApiOperation(value = "书籍关联章节", notes = "书籍关联章节")
    @PostMapping("/relation")
    @ApiImplicitParam(name = "relation", value = "关联信息", required = true, dataType = "Relation")
    public Object login(@RequestBody Relation relation, ModelMap modelMap) {

        Assert.notNull(relation.getBookId(), "BOOK_ID");
        Assert.notEmpty(relation.getChapterIds(), "CHAPTER_ID");

        bookService.relate(relation.getBookId(), relation.getChapterIds());
        return setSuccessModelMap(modelMap);
    }
}
