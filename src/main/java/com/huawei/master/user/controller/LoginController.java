package com.huawei.master.user.controller;

import com.huawei.master.core.common.AbstractController;
import com.huawei.master.core.config.Resources;
import com.huawei.master.core.system.exception.LoginException;
import com.huawei.master.user.controller.dto.Login;
import com.huawei.master.user.service.UserService;
import com.huawei.master.core.utils.Assert;
import com.huawei.master.core.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(value = "登陆管理", description = "登陆管理")
@RequestMapping(value = "/user")
public class LoginController extends AbstractController {

    @Autowired
    private UserService userService;

    // 登录
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public Object login(@ApiParam(required = true, value = "登录帐号和密码") @RequestBody Login user, ModelMap modelMap,
                        HttpServletRequest request) {
        Assert.notNull(user.getAccount(), "ACCOUNT");
        Assert.notNull(user.getPassword(), "PASSWORD");

        String clientIp = WebUtils.getIPAddress(request);
        if (userService.login(user.getAccount(), DigestUtils.sha1Hex(user.getPassword()), clientIp)) {
            request.setAttribute("msg", "[" + user.getAccount() + "]登录成功.");
            return setSuccessModelMap(modelMap);
        }
        request.setAttribute("msg", "[" + user.getAccount() + "]登录失败.");
        throw new LoginException(Resources.getMessage("LOGIN_FAIL"));
    }
}
