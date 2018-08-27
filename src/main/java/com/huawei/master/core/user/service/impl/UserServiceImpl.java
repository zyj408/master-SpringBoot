package com.huawei.master.core.user.service.impl;

import com.huawei.master.core.system.exception.BusinessException;
import com.huawei.master.core.user.dao.UserRepository;
import com.huawei.master.core.user.domain.User;
import com.huawei.master.core.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        User oldUser = userRepository.findByAccount(user.getAccount());
        if (oldUser != null) {
            logger.info("user existed...");
            throw new BusinessException("USER_IS_EXISTED");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

}
