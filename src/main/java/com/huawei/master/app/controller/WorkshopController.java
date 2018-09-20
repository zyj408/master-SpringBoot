package com.huawei.master.app.controller;

import com.huawei.master.app.controller.dto.WorkshopRelation;
import com.huawei.master.app.service.WorkshopService;
import com.huawei.master.core.common.AbstractController;
import com.huawei.master.core.common.Page;
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
@Api(value = "讨论会管理", description = "讨论会管理")
@RequestMapping(value = "/workshop")
public class WorkshopController extends AbstractController {

    @Autowired
    private WorkshopService workshopService;

    // 书籍关联章节
    @ApiOperation(value = "讨论会关联章节", notes = "讨论会关联章节")
    @PostMapping("/relation")
    @ApiImplicitParam(name = "relation", value = "关联信息", required = true, dataType = "WorkshopRelation")
    public Object relate(@RequestBody WorkshopRelation relation, ModelMap modelMap) {

        Assert.notNull(relation.getWorkshopId(), "WORKSHOP_ID");
        Assert.notEmpty(relation.getChapterIds(), "CHAPTER_ID");
        workshopService.relate(relation.getWorkshopId(), relation.getChapterIds());
        return setSuccessModelMap(modelMap);
    }

    // 书籍关联章节
    @ApiOperation(value = "查询讨论会", notes = "查询讨论会")
    @PostMapping("/query")
    public Object query(@RequestBody Page page, ModelMap modelMap) {

        Assert.notNull(page.getPage(), "PAGE");
        Assert.notNull(page.getRows(), "ROWS");


        return setSuccessModelMap(modelMap);
    }
}
