package com.huawei.master.measure.controller;

import com.huawei.master.core.common.AbstractController;
import com.huawei.master.core.utils.Assert;
import com.huawei.master.measure.controller.dto.QueryProcedureReq;
import com.huawei.master.measure.controller.dto.QueryProcedureResp;
import com.huawei.master.measure.controller.dto.QueryResultReq;
import com.huawei.master.measure.domain.FlowResult;
import com.huawei.master.measure.domain.Procedure;
import com.huawei.master.measure.service.ResultService;
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

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@Api(value = "测量结果业务", description = "测量结果业务")
@RequestMapping(value = "/result")
public class ResultController extends AbstractController {

    @Autowired
    private ResultService resultService;

    @ApiOperation(value = "查询测量过程下的测量结果", notes = "查询测量过程下的测量结果")
    @PostMapping("/query")
    @ApiImplicitParam(name = "queryResultReq", value = "查询条件", required = true, dataType = "QueryResultReq")
    //@RequiresAuthentication
    public Object query(@RequestBody QueryResultReq queryResultReq, ModelMap modelMap) {
        Assert.notNull(queryResultReq.getProcedure(), "PROCEDURE_NAME");
        Assert.notNull(queryResultReq.getPage().getPage(), "PAGE");
        Assert.max(queryResultReq.getPage().getRows(), 2000, "ROWS");

        Page<FlowResult> flowResults = resultService.query(queryResultReq);

        return setSuccessModelMap(modelMap, flowResults);
    }
}
