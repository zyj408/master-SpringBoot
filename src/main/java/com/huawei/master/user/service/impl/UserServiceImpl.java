package com.huawei.master.user.service.impl;

import com.huawei.master.user.dao.UserRepository;
import com.huawei.master.user.service.UserService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean login(String account, String password, String clientIp) {
        UsernamePasswordToken token = new UsernamePasswordToken(account, password, clientIp);
        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
            return subject.isAuthenticated();
        } catch (LockedAccountException e) {
            logger.info("account [{}] locked...", account);
        } catch (DisabledAccountException e) {
            logger.info("account [{}] disabled...", account);
        } catch (ExpiredCredentialsException e) {
            logger.info("account [{}] expired...", account);
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return false;
    }
}
