package com.huawei.master.user.service;

import com.huawei.master.user.controller.dto.request.UserEnableReq;
import com.huawei.master.user.domain.User;

public interface UserService {

    boolean login(String account, String password, String clientIp);

    void register(User user);

    Long getCurrentUser();

    void saveCurrentUser(Object user);

    void enable(UserEnableReq userEnableReq);
}
