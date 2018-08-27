package com.huawei.master.core.user.controller;

import com.huawei.master.core.common.AbstractController;
import com.huawei.master.core.user.domain.User;
import com.huawei.master.core.user.service.UserService;
import com.huawei.master.core.utils.Assert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Api(value = "用户管理", description = "用户管理")
@RequestMapping(value = "/user")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation(value = "创建用户", notes = "根据User对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    public Object postUser(ModelMap modelMap, @RequestBody User user) {

        Assert.isNotBlank(user.getAccount(), "ACCOUNT");
        Assert.length(user.getAccount(), 3, 15, "ACCOUNT");

        User savedUser = userService.save(user);
        return setSuccessModelMap(modelMap);
    }

    @GetMapping
    @ApiOperation(value = "获取用户列表", notes = "获取所有用户列表")
    public Object getUsers(ModelMap modelMap) {
        List<User> users = userService.getUsers();
        return setSuccessModelMap(modelMap, users);
    }

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        return null;
    }

    @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable Long id, @RequestBody User user) {
        return "success";
    }

    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        return "success";
    }

}