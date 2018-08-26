package com.huawei.master.core.user.dao;

import com.huawei.master.core.user.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {

    User findByAccount(String account);

}