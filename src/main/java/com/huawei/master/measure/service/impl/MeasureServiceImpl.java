package com.huawei.master.measure.service.impl;

import com.google.common.collect.Lists;
import com.huawei.master.core.system.exception.BusinessException;
import com.huawei.master.measure.controller.dto.ReportResultReq;
import com.huawei.master.measure.dao.FlowResultRepository;
import com.huawei.master.measure.dao.ProcedureRepository;
import com.huawei.master.measure.domain.FlowResult;
import com.huawei.master.measure.domain.Procedure;
import com.huawei.master.measure.service.MeasureService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service("measureService")
public class MeasureServiceImpl implements MeasureService {

    private Logger logger = LoggerFactory.getLogger(MeasureServiceImpl.class);

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

        List<ReportResultReq.MeasureParameter> measureParameters = reportResultReq.getMeasureParameters();
        Map<String, ReportResultReq.MeasureParameter> parameterMap = measureParameters.stream()
                .collect(Collectors.toMap(ReportResultReq.MeasureParameter::getId, m -> m));

        Map<String, List<ReportResultReq.MeterResult>> meterResults = reportResultReq.getMeterResults();
        long now = System.currentTimeMillis();

        for (Map.Entry<String, List<ReportResultReq.MeterResult>> entry : meterResults.entrySet()) {
            String meterNo = entry.getKey();
            List<ReportResultReq.MeterResult> results = entry.getValue();
            FlowResult result = flowResultRepository.findByNo(meterNo);

            if (checkResult(results, result)) continue;

            boolean qualified = true;
            FlowResult flowResult = new FlowResult();
            flowResult.setTime(now);
            flowResult.setNo(meterNo);
            flowResult.setProcedure(procedure);

            for (ReportResultReq.MeterResult r : results) {
                String parameter = r.getParameter();
                ReportResultReq.MeasureParameter measureParameter = parameterMap.get(parameter);

                Float flow = measureParameter.getFlow();
                Float volume = measureParameter.getVolume();
                Float start = r.getStart();
                Float end = r.getEnd();

                FlowResult.ResultCell cell = new FlowResult.ResultCell(flow, volume, start, end);
                if (Math.abs(cell.getDeviation()) >= 0.20f) {
                    qualified = false;
                }
                switch (parameter) {
                    case "q1":
                        flowResult.setQ1(cell);
                        break;
                    case "q2":
                        flowResult.setQ2(cell);
                        break;
                    case "q3":
                        flowResult.setQ3(cell);
                        break;
                }
            }

            flowResult = flowResultRepository.save(flowResult);

            //关联操作
            relateResult(procedure, flowResult);
            //更新统计
            updateStatistic(procedure, qualified);
            procedureRepository.save(procedure);
        }
    }

    private void updateStatistic(Procedure procedure, boolean qualified) {
        procedure.setRecord(procedure.getRecord() == null ? 1 : procedure.getRecord() + 1);
        if (qualified) {
            procedure.setStandard(procedure.getStandard() == null ? 1 : procedure.getStandard() + 1);
        }
    }

    private void relateResult(Procedure procedure, FlowResult flowResult) {
        List<FlowResult> flowResults = procedure.getResults();
        if (CollectionUtils.isEmpty(flowResults)) {
            flowResults = Lists.newArrayList();
            procedure.setResults(flowResults);
        }
        flowResults.add(flowResult);
    }

    private boolean checkResult(List<ReportResultReq.MeterResult> results, FlowResult result) {
        if (result != null) {
            logger.info("the result of meter [{}] existed...");
            return true;
        }

        if (results.size() != 3) {
            logger.info("the result not complete");
            return true;
        }
        return false;
    }
}
