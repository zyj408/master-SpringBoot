package com.huawei.master.measure.dao;

import com.huawei.master.measure.domain.Procedure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProcedureRepository extends MongoRepository<Procedure, String> {

    List<Procedure> findByNameAndStatus(String name, String status);

    @Query("{'name':{$regex:?0}, 'status':{$regex:?1}}")
    Page<Procedure> findByNameAndStatus(String name, String status, Pageable pageRequest);

    List<Procedure> findByName(String name);
}
