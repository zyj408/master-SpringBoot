package com.huawei.master.plc.controller;

import com.huawei.master.core.common.AbstractController;
import com.huawei.master.plc.bean.PlcStatus;
import com.huawei.master.plc.controller.dto.HomeResp;
import com.huawei.master.plc.service.PlcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "PLC业务", description = "PLC业务")
@RequestMapping(value = "/plc")
public class PlcController extends AbstractController {


    @Autowired
    private PlcService plcService;

    @ApiOperation(value = "获取PLC首页", notes = "获取PLC首页")
    @GetMapping("/home")
    public HomeResp home() {

        PlcStatus plcStatus = plcService.getPlcStatus();

        HomeResp homeResp = new HomeResp();
        homeResp.setOnline(plcStatus.getOnline());
        homeResp.setTotal(plcStatus.getTotal());
        homeResp.setUp(plcStatus.getUp());
        homeResp.setDown(plcStatus.getDown());
        return homeResp;

    }
}
