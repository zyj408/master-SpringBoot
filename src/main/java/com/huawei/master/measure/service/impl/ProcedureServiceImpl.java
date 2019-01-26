package com.huawei.master.measure.service.impl;

import com.huawei.master.core.system.exception.BusinessException;
import com.huawei.master.core.utils.ValueUtils;
import com.huawei.master.measure.controller.dto.DeleteProcedureReq;
import com.huawei.master.measure.controller.dto.FinishProcedureReq;
import com.huawei.master.measure.controller.dto.QueryProcedureReq;
import com.huawei.master.measure.controller.dto.StartProcedureReq;
import com.huawei.master.measure.dao.ProcedureRepository;
import com.huawei.master.measure.domain.Procedure;
import com.huawei.master.measure.service.ProcedureService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("procedureService")
public class ProcedureServiceImpl implements ProcedureService {

    @Autowired
    private ProcedureRepository procedureRepository;


    @Override
    public Page<Procedure> query(QueryProcedureReq queryProcedureReq) {

        String name = queryProcedureReq.getName();
        String status = queryProcedureReq.getStatus();
        name = ValueUtils.fillIfEmpty(name);
        status = ValueUtils.fillIfEmpty(status);

        PageRequest pageRequest = PageRequest.of(queryProcedureReq.getPage().getPage() - 1, queryProcedureReq.getPage().getRows());
        return procedureRepository.findByNameAndStatus(name, status, pageRequest);
    }

    @Override
    public String start(StartProcedureReq startProcedureReq) {

        List<Procedure> procedures = procedureRepository.findByName(startProcedureReq.getName());
        if (CollectionUtils.isNotEmpty(procedures)) {
            throw new BusinessException("PROCEDURE_EXISTED");
        }
        Procedure procedure = new Procedure();
        procedure.setName(startProcedureReq.getName());
        procedure.setRecord(0L);
        procedure.setNationalQualified(0L);
        procedure.setFactoryQualified(0L);
        procedure.setStartTime(System.currentTimeMillis());
        procedure.setStatus("running");

        procedure = procedureRepository.save(procedure);

        return procedure.getId();
    }

    @Override
    public void finish(FinishProcedureReq finishProcedureReq) {
        List<Procedure> procedures = procedureRepository.findByNameAndStatus(finishProcedureReq.getName(), "running");
        if (CollectionUtils.isEmpty(procedures)) {
            throw new BusinessException("PROCEDURE_NOT_EXISTED");
        }

        procedures.stream().forEach(p -> {
            p.setStatus("finish");
            p.setEndTime(System.currentTimeMillis());
            procedureRepository.save(p);
        });

    }

    @Override
    public void restart(StartProcedureReq startProcedureReq) {
        List<Procedure> procedures = procedureRepository.findByNameAndStatus(startProcedureReq.getName(), "finish");
        if (CollectionUtils.isEmpty(procedures)) {
            throw new BusinessException("PROCEDURE_NOT_EXISTED");
        }

        procedures.stream().forEach(p -> {
            p.setStatus("running");
            p.setEndTime(System.currentTimeMillis());
            procedureRepository.save(p);
        });
    }

    @Override
    public void delete(DeleteProcedureReq deleteProcedureReq) {
        List<Procedure> procedures = procedureRepository.findByNameAndStatus(deleteProcedureReq.getName(), "running");
        if (CollectionUtils.isEmpty(procedures)) {
            throw new BusinessException("PROCEDURE_NOT_EXISTED");
        }
        procedures.stream().forEach(p -> {
            procedureRepository.delete(p);
        });

    }
}
