package com.huawei.master.app.dao;

import com.huawei.master.app.domain.Workshop;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkshopRepository extends MongoRepository<Workshop, String> {
}
