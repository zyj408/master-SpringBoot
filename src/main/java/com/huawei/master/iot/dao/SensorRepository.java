package com.huawei.master.iot.dao;

import com.huawei.master.iot.domain.Sensor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SensorRepository extends MongoRepository<Sensor, String> {
}
