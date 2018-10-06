package com.huawei.master.measure.controller;

import com.huawei.master.core.common.AbstractController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "测量业务", description = "测量业务")
@RequestMapping(value = "/measure")
public class MeasureController extends AbstractController {

    // 上传流量测量数据
    @ApiOperation(value = "上传流量测量数据", notes = "上传流量测量数据")
    @PostMapping("/upload")
    @ApiImplicitParam(name = "relation", value = "关联信息", required = true, dataType = "BookRelationReq")
    public Object upload()
    {

    }



}
