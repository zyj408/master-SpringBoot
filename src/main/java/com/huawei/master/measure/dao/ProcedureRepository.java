package com.huawei.master.measure.dao;

import com.huawei.master.measure.domain.Procedure;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProcedureRepository extends MongoRepository<Procedure, String> {

    List<Procedure> findByNameAndStatus(String name, String status);

    List<Procedure> findByNameAndStatus(String name, String status, PageRequest pageRequest);

    Procedure findByName(String name);
}
