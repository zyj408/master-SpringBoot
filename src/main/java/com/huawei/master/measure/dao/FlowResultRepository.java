package com.huawei.master.measure.dao;

import com.huawei.master.measure.domain.FlowResult;
import com.huawei.master.measure.domain.Procedure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlowResultRepository extends MongoRepository<FlowResult, String> {

    FlowResult findByNo(String no);

    Page<FlowResult> findByProcedure(Procedure procedure, Pageable pageRequest);
}
