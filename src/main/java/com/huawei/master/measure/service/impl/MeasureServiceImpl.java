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
    public synchronized void report(ReportResultReq reportResultReq) {
        String procedureName = reportResultReq.getProcedure();

        List<Procedure> procedures = procedureRepository.findByNameAndStatus(procedureName, "running");
        if (CollectionUtils.isEmpty(procedures)) {
            throw new BusinessException("PROCEDURE_NOT_EXISTED");
        }
        Procedure procedure = procedures.get(0);

        List<ReportResultReq.MeterResult> meterResults = reportResultReq.getMeterResults();
        Float flow = reportResultReq.getFlow();
        Float volume = reportResultReq.getVolume();
        long now = System.currentTimeMillis();

        for (ReportResultReq.MeterResult meterResult : meterResults) {
            FlowResult result = flowResultRepository.findByNo(meterResult.getNo());

            if (result == null) {
                List<FlowResult> flowResults = procedure.getResults();
                if (CollectionUtils.isEmpty(flowResults)) {
                    flowResults = Lists.newArrayList();
                    procedure.setResults(flowResults);
                }

                result = initFlowResult(meterResult, flow, volume);
                result.setProcedure(procedure);
                result.setTime(now);
                result = flowResultRepository.save(result);
                flowResults.add(result);
                procedureRepository.save(procedure);
            } else {
                FlowResult.ResultCell resultCell = buildResultCell(meterResult, flow, volume);
                if (result.getQ2() == null) {
                    result.setQ2(resultCell);
                } else if (result.getQ3() == null) {
                    result.setQ3(resultCell);
                } else {
                    throw new BusinessException("RESULT_HAVE_REPORTED");
                }
                result.setTime(now);
                flowResultRepository.save(result);
            }

        }

    }

    private FlowResult initFlowResult(ReportResultReq.MeterResult reportResultReq, Float flow, Float volume) {
        FlowResult result;
        result = new FlowResult();
        result.setNo(reportResultReq.getNo());
        result.setQ1(buildResultCell(reportResultReq, flow, volume));
        return result;
    }

    private FlowResult.ResultCell buildResultCell(ReportResultReq.MeterResult reportResultReq, Float flow, Float volume) {
        return new FlowResult.ResultCell(flow, volume, reportResultReq.getStart(), reportResultReq.getEnd());
    }
}
