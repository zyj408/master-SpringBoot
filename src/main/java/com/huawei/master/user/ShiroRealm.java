package com.huawei.master.user;

import com.huawei.master.core.system.exception.BusinessException;
import com.huawei.master.user.dao.UserRepository;
import com.huawei.master.user.domain.User;
import com.huawei.master.user.service.AuthorizeService;
import com.huawei.master.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
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
public class ShiroRealm extends AuthorizingRealm {

    /**
     * 日志服务
     */
    private static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizeService authorizeService;

    // 权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String userId = userService.getCurrentUserId();
        //authorizeService.queryPermissionByUserId(userId);
        List<String> permissions = new ArrayList<String>();
        info.addStringPermissions(permissions);
        return info;
    }

    // 登录验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        User user = userRepository.findByAccount(token.getUsername());

        if (user == null) {
            logger.warn("user [{}] not existed", token.getUsername());
            throw new AuthenticationException("USER_NOT_EXISTED");
        }
        else if (!user.getEnable())
        {
            logger.warn("user [{}] is not enable", token.getUsername());
            throw new AuthenticationException("USER_NOT_ENABLE");
        }

        return checkPassword(token, user);
    }

    private AuthenticationInfo checkPassword(UsernamePasswordToken token, User user) {
        String password = new String(token.getPassword());
        if (StringUtils.equals(password, user.getPassword())) {

            userService.saveCurrentUser(user.getId());
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
            return authenticationInfo;
        } else {
            logger.warn("user [{}] toke wrong password", token.getUsername());
            throw new AuthenticationException("PASSWORD_IS_WRONG");
        }
    }
}
