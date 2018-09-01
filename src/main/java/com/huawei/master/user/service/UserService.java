package com.huawei.master.user.service;

import com.huawei.master.user.domain.User;

public interface UserService {

    boolean login(String account, String s, String clientIp);

    void register(User user);

    Long getCurrentUser();
}
