package com.huawei.master.user.service.impl;

import com.google.common.collect.Lists;
import com.huawei.master.core.utils.Resources;
import com.huawei.master.core.system.exception.BusinessException;
import com.huawei.master.user.controller.dto.request.RoleRelateReq;
import com.huawei.master.user.dao.RoleRepository;
import com.huawei.master.user.dao.UserRepository;
import com.huawei.master.user.domain.Role;
import com.huawei.master.user.domain.User;
import com.huawei.master.user.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    private Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addRole(RoleRelateReq roleRelateReq) {
        User user = userRepository.findByAccount(roleRelateReq.getAccount());
        if (user == null) {
            throw new BusinessException(Resources.getMessage("USER_NOT_EXISTED"));
        }

        Role role = roleRepository.findByName(roleRelateReq.getRole());
        if (role == null) {
            throw new BusinessException(Resources.getMessage("ROLE_NOT_EXISTED"));
        }

        //增加角色
        List<Role> roles = user.getRoles();
        if (roles == null) {
            roles = Lists.newArrayList(role);
            user.setRoles(roles);
            userRepository.save(user);
        } else if (!roles.contains(role)) {
            roles.add(role);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

    @Override
    public void removeRole(RoleRelateReq roleRelateReq) {
        User user = userRepository.findByAccount(roleRelateReq.getAccount());
        if (user == null) {
            throw new BusinessException(Resources.getMessage("USER_NOT_EXISTED"));
        }

        Role role = roleRepository.findByName(roleRelateReq.getRole());
        if (role == null) {
            throw new BusinessException(Resources.getMessage("ROLE_NOT_EXISTED"));
        }

        //增加角色
        List<Role> roles = user.getRoles();
        if (roles != null && roles.contains(role)) {
            roles.remove(role);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
