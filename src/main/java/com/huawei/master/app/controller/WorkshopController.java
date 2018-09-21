package com.huawei.master.app.controller;

import com.google.common.collect.Lists;
import com.huawei.master.app.controller.dto.reponse.WorkshopDetailResp;
import com.huawei.master.app.controller.dto.request.WorkshopDetailReq;
import com.huawei.master.app.controller.dto.request.WorkshopQueryReq;
import com.huawei.master.app.controller.dto.request.WorkshopRelationReq;
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
@Api(value = "讨论会业务", description = "讨论会业务")
@RequestMapping(value = "/workshop")
public class WorkshopController extends AbstractController {

    @Autowired
    private WorkshopService workshopService;

    @Autowired
    private WorkshopRepository workshopRepository;

    // 章节关联讨论会
    @ApiOperation(value = "讨论会关联章节", notes = "讨论会关联章节")
    @PostMapping("/relation")
    @ApiImplicitParam(name = "relation", value = "关联信息", required = true, dataType = "WorkshopRelationReq")
    public Object relate(@RequestBody WorkshopRelationReq relation, ModelMap modelMap) {

        Assert.notNull(relation.getWorkshopId(), "WORKSHOP_ID");
        Assert.notEmpty(relation.getChapterIds(), "CHAPTER_ID");

        workshopService.relate(relation.getWorkshopId(), relation.getChapterIds());
        return setSuccessModelMap(modelMap);
    }

    // 讨论会查询
    @ApiOperation(value = "讨论会查询", notes = "讨论会查询")
    @PostMapping("/query")
    @ApiImplicitParam(name = "workshopQueryReq", value = "讨论会IDs", required = true, dataType = "WorkshopQueryReq")
    public Object query(@RequestBody WorkshopQueryReq workshopQueryReq, ModelMap modelMap) {

        Assert.notNull(workshopQueryReq.getPage().getPage(), "PAGE");
        Assert.max(workshopQueryReq.getPage().getRows(), 20, "ROWS");

        if(workshopQueryReq.getName() == null)
        {
            workshopQueryReq.setName("");
        }

        List<Workshop> workshops = workshopService.query(workshopQueryReq);
        List<WorkshopDetailResp> workshopDetails = workshops.stream().map(w -> new WorkshopDetailResp(w)).collect(Collectors.toList());
        return setSuccessModelMap(modelMap, workshopDetails);
    }

    // 讨论会详情
    @ApiOperation(value = "讨论会详情", notes = "讨论会详情")
    @PostMapping("/detail")
    @ApiImplicitParam(name = "workshopDetailReq", value = "讨论会IDs", required = true, dataType = "WorkshopDetailReq")
    public Object detail(@RequestBody WorkshopDetailReq workshopDetailReq, ModelMap modelMap) {

        Assert.notEmpty(workshopDetailReq.getWorkshopIds(), "WORKSHOP_IDS");

        List<Workshop> workshops = Lists.newArrayList(workshopRepository.findAllById(workshopDetailReq.getWorkshopIds()));
        List<WorkshopDetailResp> workshopDetails = workshops.stream().map(w -> new WorkshopDetailResp(w)).collect(Collectors.toList());
        return setSuccessModelMap(modelMap, workshopDetails);
    }


}
