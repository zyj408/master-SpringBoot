package com.huawei.master.core.user.service;

import com.huawei.master.core.user.domain.User;

import java.util.List;

public interface UserService {
    void save(User user);

    List<User> getUsers();
}
