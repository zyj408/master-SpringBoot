package com.huawei.master.user.service.impl;

import com.huawei.master.core.config.Resources;
import com.huawei.master.core.constant.Constants;
import com.huawei.master.core.system.exception.BusinessException;
import com.huawei.master.user.controller.dto.request.UserEnableReq;
import com.huawei.master.user.dao.UserRepository;
import com.huawei.master.user.domain.User;
import com.huawei.master.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
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
            throw new BusinessException(Resources.getMessage("ACCOUNT_LOCKED"));
        } catch (DisabledAccountException e) {
            logger.info("account [{}] disabled...", account);
            throw new BusinessException(Resources.getMessage("ACCOUNT_DISABLED"));
        } catch (ExpiredCredentialsException e) {
            logger.info("account [{}] expired...", account);
            throw new BusinessException(Resources.getMessage("CREDENTIAL_EXPIRED"));
        } catch (Exception e) {
            throw new BusinessException(Resources.getMessage(e.getMessage()));
        }
    }


    @Override
    public void register(User user) {
        User oldUser = userRepository.findByAccount(user.getAccount());
        if (oldUser != null) {
            throw new BusinessException(Resources.getMessage("USER_IS_EXISTED"));
        }
        user.setPassword(DigestUtils.sha1Hex(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public Long getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            try {
                Session session = currentUser.getSession();
                if (null != session) {
                    return (Long) session.getAttribute("CURRENT_USER");
                }
            } catch (InvalidSessionException e) {
                logger.error(ExceptionUtils.getStackTrace(e));
            }
        }
        return null;
    }

    @Override
    public void saveCurrentUser(Object user) {
        setSession(Constants.CURRENT_USER, user);
    }

    @Override
    public void enable(UserEnableReq userEnableReq) {
        User oldUser = userRepository.findByAccount(userEnableReq.getAccount());
        if (oldUser == null) {
            throw new BusinessException(Resources.getMessage("USER_NOT_EXISTED"));
        }

        oldUser.setEnable(userEnableReq.getEnable());
        userRepository.save(oldUser);
    }

    private void setSession(Object key, Object value) {
        Subject currentUser = SecurityUtils.getSubject();
        if (null != currentUser) {
            Session session = currentUser.getSession();
            if (null != session) {
                session.setAttribute(key, value);
            }
        }
    }
}
