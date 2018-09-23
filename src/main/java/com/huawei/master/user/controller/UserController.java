package com.huawei.master.user.controller;

import com.huawei.master.core.common.AbstractController;
import com.huawei.master.core.config.Resources;
import com.huawei.master.core.system.HttpCode;
import com.huawei.master.core.system.exception.LoginException;
import com.huawei.master.user.controller.dto.Login;
import com.huawei.master.user.domain.User;
import com.huawei.master.user.service.UserService;
import com.huawei.master.core.utils.Assert;
import com.huawei.master.core.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(value = "登陆业务", description = "登陆业务")
@RequestMapping(value = "/user")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    // 登录
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    @ApiImplicitParam(name = "login", value = "登陆信息", required = true, dataType = "Login")
    public Object login(@RequestBody Login login, ModelMap modelMap,
                        HttpServletRequest request) {
        Assert.notNull(login.getAccount(), "ACCOUNT");
        Assert.notNull(login.getPassword(), "PASSWORD");

        String clientIp = WebUtils.getIPAddress(request);
        if (userService.login(login.getAccount(), DigestUtils.sha1Hex(login.getPassword()), clientIp)) {
            request.setAttribute("msg", "[" + login.getAccount() + "]登录成功.");
            return setSuccessModelMap(modelMap);
        }
        request.setAttribute("msg", "[" + login.getAccount() + "]登录失败.");
        throw new LoginException(Resources.getMessage("LOGIN_FAIL"));
    }

    // 登出
    @ApiOperation(value = "用户登出", notes = "用户登出")
    @PostMapping("/logout")
    public Object logout(HttpServletRequest request, ModelMap modelMap) {
        SecurityUtils.getSubject().logout();
        return setSuccessModelMap(modelMap);
    }

    //注册
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping("/register")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    public Object register(@RequestBody User user, ModelMap modelMap) {
        Assert.notNull(user.getAccount(), "ACCOUNT");
        Assert.notNull(user.getPassword(), "PASSWORD");

        userService.register(user);

        return setSuccessModelMap(modelMap);
    }

    // 没有登录
    @ApiIgnore
    @RequestMapping(value = "/unauthorized", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
    public Object unauthorized(ModelMap modelMap) throws Exception {
        return setModelMap(modelMap, HttpCode.UNAUTHORIZED);
    }

    // 没有权限
    @ApiIgnore
    @RequestMapping(value = "/forbidden", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
    public Object forbidden(ModelMap modelMap) {
        return setModelMap(modelMap, HttpCode.FORBIDDEN);
    }
}
