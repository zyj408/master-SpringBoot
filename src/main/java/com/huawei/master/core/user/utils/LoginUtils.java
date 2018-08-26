package com.huawei.master.core.user.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginUtils {

    /**
     * 日志服务
     */
    private static Logger logger = LoggerFactory.getLogger(LoginUtils.class);

    private LoginUtils() {
    }

    public static final Boolean login(String account, String password, String host) {
        UsernamePasswordToken token = new UsernamePasswordToken(account, password, host);
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
