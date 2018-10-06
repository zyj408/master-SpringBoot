package com.huawei.master.user.controller;

import com.huawei.master.core.common.AbstractController;
import com.huawei.master.core.utils.Assert;
import com.huawei.master.user.controller.dto.request.AddRoleReq;
import com.huawei.master.user.service.RoleService;
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
@Api(value = "角色业务", description = "角色业务")
@RequestMapping(value = "/role")
public class RoleController extends AbstractController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "用户关联角色", notes = "关联角色")
    @PostMapping("/add")
    @ApiImplicitParam(name = "addRoleReq", value = "关联角色信息", required = true, dataType = "AddRoleReq")
    public Object addRole(@RequestBody AddRoleReq addRoleReq, ModelMap modelMap) {
        Assert.notNull(addRoleReq.getAccount(), "ACCOUNT");
        Assert.notNull(addRoleReq.getRole(), "ROLE");

        roleService.addRole(addRoleReq);

        return setSuccessModelMap(modelMap);
    }


}
