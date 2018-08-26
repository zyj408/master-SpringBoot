package com.huawei.master.core.user.utils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroUtils {

    /**
     * 日志服务
     */
    private static Logger logger = LoggerFactory.getLogger(ShiroUtils.class);


    public static final Long getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            try {
                Session session = currentUser.getSession();
                if (null != session) {
                    return (Long)session.getAttribute("CURRENT_USER");
                }
            } catch (InvalidSessionException e) {
                logger.error(ExceptionUtils.getStackTrace(e));
            }
        }
        return null;
    }
}
