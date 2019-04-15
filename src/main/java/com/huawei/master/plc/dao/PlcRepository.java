package com.huawei.master.plc.dao;

import com.huawei.master.plc.domain.Plc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlcRepository extends MongoRepository<Plc, String> {

    Plc findByName(String name);
}
