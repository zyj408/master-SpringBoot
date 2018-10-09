package com.huawei.master.measure.service.impl;

import com.huawei.master.core.system.exception.BusinessException;
import com.huawei.master.measure.controller.dto.QueryResultReq;
import com.huawei.master.measure.dao.FlowResultRepository;
import com.huawei.master.measure.dao.ProcedureRepository;
import com.huawei.master.measure.domain.FlowResult;
import com.huawei.master.measure.domain.Procedure;
import com.huawei.master.measure.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service("resultService")
public class ResultServiceImpl implements ResultService {

    @Autowired
    private FlowResultRepository flowResultRepository;

    @Autowired
    private ProcedureRepository procedureRepository;

    @Override
    public Page<FlowResult> query(QueryResultReq queryResultReq) {
        List<Procedure> procedures = procedureRepository.findByName(queryResultReq.getProcedure());
        if(CollectionUtils.isEmpty(procedures))
        {
            throw new BusinessException("PROCEDURE_NOT_EXISTED");
        }
        Procedure procedure = procedures.get(0);

        PageRequest pageRequest = PageRequest.of(queryResultReq.getPage().getPage() - 1, queryResultReq.getPage().getRows());

        return flowResultRepository.findByProcedure(procedure, pageRequest);
    }
}
