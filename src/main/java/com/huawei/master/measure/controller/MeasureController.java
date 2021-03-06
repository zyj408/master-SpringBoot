package com.huawei.master.measure.controller;

import com.huawei.master.core.common.AbstractController;
import com.huawei.master.core.utils.Assert;
import com.huawei.master.measure.controller.dto.ExportResultReq;
import com.huawei.master.measure.controller.dto.ReportResultReq;
import com.huawei.master.measure.service.MeasureService;
import com.huawei.master.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Api(value = "测量业务", description = "测量业务")
@RequestMapping(value = "/measure")
public class MeasureController extends AbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private MeasureService measureService;


    @ApiOperation(value = "上传流量测量数据", notes = "上传流量测量数据")
    @GetMapping("/register")
    //@RequiresAuthentication
    public Object register(ModelMap modelMap) {

        String userId = userService.getCurrentUserId();

        measureService.register(userId);

        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "上传流量测量数据", notes = "上传流量测量数据")
    @PostMapping("/report")
    @ApiImplicitParam(name = "reportResultReq", value = "上报结果", required = true, dataType = "ReportResultReq")
    //@RequiresAuthentication
    public Object report(@RequestBody ReportResultReq reportResultReq, ModelMap modelMap) {
        Assert.notNull(reportResultReq.getProcedure(), "NAME");
        Assert.notEmpty(reportResultReq.getMeasureParameters(), "PARAMETER");
        Assert.notEmpty(reportResultReq.getMeterResults(), "REPORT");

        measureService.report(reportResultReq);

        return setSuccessModelMap(modelMap);
    }

    @ApiOperation(value = "导出测量结果", notes = "导出测量结果")
    @GetMapping("/export/{name}")
    @ApiImplicitParam(name = "exportResultReq", value = "上报结果", required = true, dataType = "ExportResultReq")
    public void export(@PathVariable("name") String name, HttpServletResponse response) throws IOException {
        Assert.notNull(name, "NAME");
        measureService.export(name, response);
    }
}
