package com.huawei.master.measure.dao;

import com.huawei.master.measure.domain.FlowMeasure;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlowMeasureRepository extends MongoRepository<FlowMeasure, String> {
}
