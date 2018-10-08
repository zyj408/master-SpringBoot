package com.huawei.master.measure.service.impl;

import com.google.common.collect.Lists;
import com.huawei.master.core.system.exception.BusinessException;
import com.huawei.master.measure.controller.dto.ReportResultReq;
import com.huawei.master.measure.dao.ProcedureRepository;
import com.huawei.master.measure.domain.FlowResult;
import com.huawei.master.measure.domain.Procedure;
import com.huawei.master.measure.service.MeasureService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service("measureService")
public class MeasureServiceImpl implements MeasureService {

    @Autowired
    private ProcedureRepository procedureRepository;


    /**
     * 在线设备
     */
    private ConcurrentHashMap<String, String> onlineUsers = new ConcurrentHashMap<String, String>();

    /**
     * 缓存
     */
    private ConcurrentHashMap<Procedure, List<FlowResult>> resultCache = new ConcurrentHashMap<Procedure, List<FlowResult>>();


    @Override
    public void register(String userId) {

    }

    @Override
    public void report(ReportResultReq reportResultReq) {
        Procedure procedure = procedureRepository.findByName(reportResultReq.getProcedure());
        if (procedure == null) {
            throw new BusinessException("PROCEDURE_NOT_EXISTED");
        }

        List<FlowResult> results = resultCache.computeIfAbsent(procedure, v -> Lists.newArrayList());
        FlowResult result = results.stream().filter(r -> StringUtils.equals(reportResultReq.getNo(), r.getNo())).findFirst().get();

        if (result == null) {
            result = initFlowResult(reportResultReq);
            results.add(result);
        } else {
            updateFlowResult(reportResultReq, result);
        }

    }

    private void updateFlowResult(ReportResultReq reportResultReq, FlowResult result) {
        if(result.getQ2() == null)
        {
            result.setQ2(new FlowResult.ResultCell(reportResultReq.getFlow(), reportResultReq.getVolume(), reportResultReq.getStart(), reportResultReq.getEnd()));
        }
        else if(result.getQ3() == null)
        {
            result.setQ3(new FlowResult.ResultCell(reportResultReq.getFlow(), reportResultReq.getVolume(), reportResultReq.getStart(), reportResultReq.getEnd()));
        }
    }

    private FlowResult initFlowResult(ReportResultReq reportResultReq) {
        FlowResult result;
        result = new FlowResult();
        result.setNo(reportResultReq.getNo());
        result.setQ1(new FlowResult.ResultCell(reportResultReq.getFlow(), reportResultReq.getVolume(), reportResultReq.getStart(), reportResultReq.getEnd()));
        return result;
    }
}
