package com.huawei.master.user.service;

import com.huawei.master.user.controller.dto.request.RoleRelateReq;

public interface RoleService {
    void addRole(RoleRelateReq roleRelateReq);

    void removeRole(RoleRelateReq roleRelateReq);
}
