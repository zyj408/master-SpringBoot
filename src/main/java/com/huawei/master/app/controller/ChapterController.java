package com.huawei.master.app.controller;

import com.huawei.master.app.controller.dto.request.ChapterScoreReq;
import com.huawei.master.app.service.ChapterService;
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
@Api(value = "章节业务", description = "章节业务")
@RequestMapping(value = "/chapter")
public class ChapterController extends AbstractController {

    @Autowired
    private ChapterService chapterService;

    // 书籍关联章节
    @ApiOperation(value = "章节评分", notes = "章节评分")
    @PostMapping("/score")
    @ApiImplicitParam(name = "score", value = "评分信息", required = true, dataType = "ChapterScoreReq")
    public Object score(@RequestBody ChapterScoreReq score, ModelMap modelMap) {

        Assert.notNull(score.getChapterId(), "CHAPTER_ID");
        Assert.max(score.getScore(), 5, "CHAPTER_SCORE");

        chapterService.addScore(score.getChapterId(), score.getScore());
        return setSuccessModelMap(modelMap);
    }
}
