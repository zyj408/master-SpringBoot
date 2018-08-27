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
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        User oldUser = userRepository.findByAccount(user.getAccount());
        if (oldUser != null) {
            throw new BusinessException("USER_IS_EXISTED");
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        Optional<User> optional = userRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public User update(User user) {
        Optional<User> optional = userRepository.findById(user.getId());
        if(!optional.isPresent())
        {
            throw new BusinessException("USER_NOT_EXISTED");
        }
        return null;
    }

}
