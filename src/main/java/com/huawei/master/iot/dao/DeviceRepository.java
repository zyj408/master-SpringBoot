package com.huawei.master.iot.dao;

import com.huawei.master.iot.domain.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeviceRepository extends MongoRepository<Device, String> {
}
