package com.huawei.master.measure.dao;

import com.huawei.master.measure.domain.FlowEnvironment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlowEnvironmentRepository extends MongoRepository<FlowEnvironment, String> {
}
