package com.huawei.master.measure.dao;

import com.huawei.master.measure.domain.MeasureEnvironment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeasureEnvironmentRepository extends MongoRepository<MeasureEnvironment, String> {
}
