package com.huawei.master.core.user.controller;

import com.huawei.master.core.common.AbstractController;
import com.huawei.master.core.user.domain.User;
import com.huawei.master.core.user.service.UserService;
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
@Api(value = "登陆管理", description = "登陆管理")
@RequestMapping(value = "/login")
public class LoginController extends AbstractController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation(value = "用户注册", notes = "向系统注册新用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    public Object register(ModelMap modelMap, @RequestBody User user) {




        return setSuccessModelMap(modelMap);
    }
}
