package com.huawei.master.app.dao;

import com.huawei.master.app.domain.Workshop;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WorkshopRepository extends MongoRepository<Workshop, String> {

    List<Workshop> findByNameLike(String name, PageRequest pageRequest);
}
