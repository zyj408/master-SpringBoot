package com.huawei.master.user;

import com.huawei.master.user.service.AuthorizeService;
import com.huawei.master.user.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Realm extends AuthorizingRealm {

    /**
     * 日志服务
     */
    private static Logger logger = LoggerFactory.getLogger(Realm.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizeService authorizeService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Long userId = userService.getCurrentUser();
        authorizeService.queryPermissionByUserId(userId);
        List<String> permissions = new ArrayList<String>();
        info.addStringPermissions(permissions);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;


        return null;
    }
}
