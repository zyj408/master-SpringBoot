package com.huawei.master.measure.controller;

import com.huawei.master.core.common.AbstractController;
import com.huawei.master.core.utils.Assert;
import com.huawei.master.measure.controller.dto.*;
import com.huawei.master.measure.domain.Procedure;
import com.huawei.master.measure.service.ProcedureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "测量过程业务", description = "测量过程业务")
@RequestMapping(value = "/procedure")
public class ProcedureController extends AbstractController {

    @Autowired
    private ProcedureService procedureService;

    @ApiOperation(value = "查询测量过程", notes = "查询测量过程")
    @PostMapping("/query")
    @ApiImplicitParam(name = "queryProcedureReq", value = "查询条件", required = true, dataType = "QueryProcedureReq")
    //@RequiresAuthentication
    public Object query(@RequestBody QueryProcedureReq queryProcedureReq, ModelMap modelMap) {
        Assert.notNull(queryProcedureReq.getPage().getPage(), "PAGE");
        Assert.max(queryProcedureReq.getPage().getRows(), 50, "ROWS");

        Page<Procedure> procedures = procedureService.query(queryProcedureReq);

        return setSuccessModelMap(modelMap, procedures);
    }

    @ApiOperation(value = "启动测量过程", notes = "启动测量过程")
    @PostMapping("/start")
    @ApiImplicitParam(name = "startProcedureReq", value = "测量过程请求", required = true, dataType = "StartProcedureReq")
    //@RequiresAuthentication
    public Object start(@RequestBody StartProcedureReq startProcedureReq, ModelMap modelMap) {

        Assert.notNull(startProcedureReq.getName(), "NAME");

        String procedureId = procedureService.start(startProcedureReq);

        return setSuccessModelMap(modelMap, new StartProcedureResp(procedureId));
    }

    @ApiOperation(value = "结束测量过程", notes = "结束测量过程")
    @PostMapping("/finish")
    @ApiImplicitParam(name = "finishProcedureReq", value = "测量过程请求", required = true, dataType = "FinishProcedureReq")
    //@RequiresAuthentication
    public Object finish(@RequestBody FinishProcedureReq finishProcedureReq, ModelMap modelMap) {

        Assert.notNull(finishProcedureReq.getName(), "NAME");

        procedureService.finish(finishProcedureReq);
        return setSuccessModelMap(modelMap);
    }

}
