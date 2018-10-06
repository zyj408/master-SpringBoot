package com.huawei.master.measure.dao;

import com.huawei.master.measure.domain.FlowResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlowResultRepository extends MongoRepository<FlowResult, String> {
}
