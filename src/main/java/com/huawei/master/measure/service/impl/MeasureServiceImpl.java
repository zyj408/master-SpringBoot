package com.huawei.master.measure.service.impl;

import com.google.common.collect.Lists;
import com.huawei.master.core.system.exception.BusinessException;
import com.huawei.master.measure.controller.dto.ReportResultReq;
import com.huawei.master.measure.dao.FlowResultRepository;
import com.huawei.master.measure.dao.ProcedureRepository;
import com.huawei.master.measure.domain.FlowResult;
import com.huawei.master.measure.domain.Procedure;
import com.huawei.master.measure.service.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service("measureService")
public class MeasureServiceImpl implements MeasureService {

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private FlowResultRepository flowResultRepository;


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
        String procedureName = reportResultReq.getProcedure();
        String meterNo = reportResultReq.getNo();

        List<Procedure> procedures = procedureRepository.findByNameAndStatus(procedureName, "running");
        if (CollectionUtils.isEmpty(procedures)) {
            throw new BusinessException("PROCEDURE_NOT_EXISTED");
        }
        Procedure procedure = procedures.get(0);

        FlowResult result = flowResultRepository.findByNo(meterNo);
        if (result == null) {
            List<FlowResult> flowResults = procedure.getResults();
            if (CollectionUtils.isEmpty(flowResults)) {
                flowResults = Lists.newArrayList();
                procedure.setResults(flowResults);
            }

            result = initFlowResult(reportResultReq);
            result.setProcedure(procedure);
            result = flowResultRepository.save(result);
            flowResults.add(result);
            procedureRepository.save(procedure);
        } else {
            updateFlowResult(reportResultReq, result);
            flowResultRepository.save(result);
        }


    }

    private void updateFlowResult(ReportResultReq reportResultReq, FlowResult result) {
        if (result.getQ2() == null) {
            result.setQ2(new FlowResult.ResultCell(reportResultReq.getFlow(), reportResultReq.getVolume(), reportResultReq.getStart(), reportResultReq.getEnd()));
        } else if (result.getQ3() == null) {
            result.setQ3(new FlowResult.ResultCell(reportResultReq.getFlow(), reportResultReq.getVolume(), reportResultReq.getStart(), reportResultReq.getEnd()));
        } else {
            throw new BusinessException("RESULT_HAVE_REPORTED");
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
