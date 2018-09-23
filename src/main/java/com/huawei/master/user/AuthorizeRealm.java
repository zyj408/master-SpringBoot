package com.huawei.master.user;

import com.huawei.master.user.dao.UserRepository;
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
public class AuthorizeRealm extends AuthorizingRealm {

    /**
     * 日志服务
     */
    private static Logger logger = LoggerFactory.getLogger(AuthorizeRealm.class);

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
        Long userId = userService.getCurrentUser();
        authorizeService.queryPermissionByUserId(userId);
        List<String> permissions = new ArrayList<String>();
        info.addStringPermissions(permissions);
        return info;
    }

    // 登录验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        /**
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("enable", 1);
        params.put("account", token.getUsername());
        List<?> list = sysUserService.queryList(params);
        if (list.size() == 1) {
            SysUser user = (SysUser)list.get(0);
            StringBuilder sb = new StringBuilder(100);
            for (int i = 0; i < token.getPassword().length; i++) {
                sb.append(token.getPassword()[i]);
            }
            if (user.getPassword().equals(SecurityUtil.encryptPassword(sb.toString()))) {
                ShiroUtil.saveCurrentUser(user.getId());
                saveSession(user.getAccount(), token.getHost());
                AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getAccount(), sb.toString(),
                        user.getUserName());
                return authcInfo;
            }
            logger.warn("USER [{}] PASSWORD IS WRONG: {}", token.getUsername(), sb.toString());
            return null;
        } else {
            logger.warn("No user: {}", token.getUsername());
            return null;
        }

         **/
        return null;
    }
}
