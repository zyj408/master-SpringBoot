package com.huawei.master.user.dao;

import com.huawei.master.user.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByAccount(String account);

}