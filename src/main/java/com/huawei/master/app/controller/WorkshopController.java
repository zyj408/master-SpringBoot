package com.huawei.master.app.controller;

import com.google.common.collect.Lists;
import com.huawei.master.app.controller.dto.reponse.WorkshopDetail;
import com.huawei.master.app.controller.dto.request.WorkshopRelation;
import com.huawei.master.app.dao.WorkshopRepository;
import com.huawei.master.app.domain.Workshop;
import com.huawei.master.app.service.WorkshopService;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "讨论会管理", description = "讨论会管理")
@RequestMapping(value = "/workshop")
public class WorkshopController extends AbstractController {

    @Autowired
    private WorkshopService workshopService;

    @Autowired
    private WorkshopRepository workshopRepository;

    // 章节关联讨论会
    @ApiOperation(value = "讨论会关联章节", notes = "讨论会关联章节")
    @PostMapping("/relation")
    @ApiImplicitParam(name = "relation", value = "关联信息", required = true, dataType = "WorkshopRelation")
    public Object relate(@RequestBody WorkshopRelation relation, ModelMap modelMap) {

        Assert.notNull(relation.getWorkshopId(), "WORKSHOP_ID");
        Assert.notEmpty(relation.getChapterIds(), "CHAPTER_ID");
        workshopService.relate(relation.getWorkshopId(), relation.getChapterIds());
        return setSuccessModelMap(modelMap);
    }

    // 讨论会详情
    @ApiOperation(value = "讨论会详情", notes = "讨论会详情")
    @PostMapping("/detail")
    @ApiImplicitParam(name = "workshopIds", value = "讨论会IDs", required = true, dataType = "List")
    public Object detail(@RequestBody List<String> workshopIds, ModelMap modelMap) {
        Assert.notEmpty(workshopIds, "WORKSHOP_IDS");
        List<Workshop> workshops = Lists.newArrayList(workshopRepository.findAllById(workshopIds));

        List<WorkshopDetail> workshopDetails = workshops.stream().map(w -> new WorkshopDetail(w)).collect(Collectors.toList());
        return setSuccessModelMap(modelMap, workshopDetails);
    }

}
