package com.huawei.master.measure.service.impl;

import com.google.common.collect.Lists;
import com.huawei.master.core.system.exception.BusinessException;
import com.huawei.master.measure.controller.dto.ReportResultReq;
import com.huawei.master.measure.dao.FlowResultRepository;
import com.huawei.master.measure.dao.ProcedureRepository;
import com.huawei.master.measure.domain.FlowResult;
import com.huawei.master.measure.domain.Procedure;
import com.huawei.master.measure.service.MeasureService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service("measureService")
public class MeasureServiceImpl implements MeasureService {

    private Logger logger = LoggerFactory.getLogger(MeasureServiceImpl.class);

    /**
     * 国家标准
     */
    private static float NATIONAL_STANDARDS = 0.02f;

    /**
     * 厂家标准
     */
    private static float FACTORY_STANDARDS = 0.015f;

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

        Map<String, List<ReportResultReq.MeterResult>> meterResultMap = parseResultMap(reportResultReq.getMeterResults());

        long now = System.currentTimeMillis();

        for (Map.Entry<String, List<ReportResultReq.MeterResult>> entry : meterResultMap.entrySet()) {
            String meterNo = entry.getKey();
            List<ReportResultReq.MeterResult> results = entry.getValue();
            FlowResult result = flowResultRepository.findByNo(meterNo);

            if (checkResult(results, result)) continue;

            boolean nationalQualified = true;
            boolean factoryQualified = true;

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
                if (Math.abs(cell.getDeviation()) >= NATIONAL_STANDARDS) nationalQualified = false;
                if (Math.abs(cell.getDeviation()) >= FACTORY_STANDARDS) factoryQualified = false;

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

            flowResult.setFactoryQualified(factoryQualified);
            flowResult.setNationalQualified(nationalQualified);
            flowResult = flowResultRepository.save(flowResult);

            //关联操作
            relateResult(procedure, flowResult);
            //更新统计
            updateStatistic(procedure, nationalQualified, factoryQualified);
            //更新最新上传时间
            procedure.setLastTime(System.currentTimeMillis());
            procedureRepository.save(procedure);
        }
    }

    @Override
    public void export(String name, HttpServletResponse response) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("流量记录单");


        List<Procedure> procedures = procedureRepository.findByName(name);
        if (CollectionUtils.isNotEmpty(procedures)) {
            Procedure procedure = procedures.get(0);
            List<FlowResult> flowResults = procedure.getResults();
            if (CollectionUtils.isNotEmpty(flowResults)) {
                createHeaderInfo(sheet, flowResults.get(0));
                for (int i = 0; i < flowResults.size(); i++) {
                    createRecord(sheet, flowResults.get(i), i);
                }
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        //设置文件名
        String excelName = name + "_" + sdf.format(new Date()) + ".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + excelName);

        workbook.write(response.getOutputStream());
    }

    private void createRecord(HSSFSheet sheet, FlowResult flowResult, int i) {
        HSSFRow row = sheet.createRow(i + 4);
        //序号
        row.createCell(0).setCellValue(i + 1);

        //表号
        row.createCell(1).setCellValue(flowResult.getNo());

        //Q3
        row.createCell(2).setCellValue(flowResult.getQ3().getStart());
        row.createCell(3).setCellValue(flowResult.getQ3().getEnd());
        double q3Div = new BigDecimal(flowResult.getQ3().getDeviation() * 100).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        row.createCell(4).setCellValue(q3Div);

        //Q2
        row.createCell(5).setCellValue(flowResult.getQ2().getStart());
        row.createCell(6).setCellValue(flowResult.getQ2().getEnd());
        double q2Div = new BigDecimal(flowResult.getQ2().getDeviation() * 100).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        row.createCell(7).setCellValue(q2Div);

        //Q1
        row.createCell(8).setCellValue(flowResult.getQ1().getStart());
        row.createCell(9).setCellValue(flowResult.getQ1().getEnd());
        double q1Div = new BigDecimal(flowResult.getQ1().getDeviation() * 100).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        row.createCell(10).setCellValue(q1Div);
    }

    private static void createHeaderInfo(HSSFSheet sheet, FlowResult flowResult) {
        HSSFRow[] infoRow = new HSSFRow[4];
        HSSFCell[][] infoGrid = new HSSFCell[4][16];
        for (int i = 0; i < 4; i++) {
            HSSFRow row = sheet.createRow(i);
            infoRow[i] = row;
            for (int j = 0; j < 16; j++) {
                infoGrid[i][j] = row.createCell(j);
            }
        }

        //第一行
        FlowResult.ResultCell q3 = flowResult.getQ3();
        FlowResult.ResultCell q2 = flowResult.getQ2();
        FlowResult.ResultCell q1 = flowResult.getQ1();

        infoGrid[0][0].setCellValue("型号规格");
        infoGrid[0][2].setCellValue("Q3");
        infoGrid[0][3].setCellValue(q3.getVolume());
        infoGrid[0][4].setCellValue("Q3/Q1");
        infoGrid[0][5].setCellValue(Math.round(q3.getVolume() / q1.getVolume()));
        infoGrid[0][6].setCellValue("Q2/Q1");
        infoGrid[0][7].setCellValue(Math.round(q2.getVolume() / q1.getVolume()));
        infoGrid[0][8].setCellValue("设备编号");
        infoGrid[0][10].setCellValue("室温");
        infoGrid[0][12].setCellValue("水温");
        infoGrid[0][14].setCellValue("水压");

        infoGrid[1][0].setCellValue("序号");
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 0, 0));
        infoGrid[1][1].setCellValue("表号");
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 1, 1));

        infoGrid[1][2].setCellValue("常用流量（Q3）：" + q3.getFlow());
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 4));

        infoGrid[2][2].setCellValue("用水量（Q3）：" + q3.getVolume());
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 4));

        infoGrid[1][5].setCellValue("常用流量（Q2）：" + q2.getFlow());
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 7));

        infoGrid[2][5].setCellValue("用水量（Q2）：" + q2.getVolume());
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 7));

        infoGrid[1][8].setCellValue("常用流量（Q1）：" + q1.getFlow());
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 8, 10));

        infoGrid[2][8].setCellValue("用水量（Q1）：" + q1.getVolume());
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 8, 10));

        infoGrid[1][11].setCellValue("耐压");
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 11, 11));

        infoGrid[1][12].setCellValue("始动");
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 12, 12));

        infoGrid[1][13].setCellValue("结论");
        sheet.addMergedRegion(new CellRangeAddress(1, 3, 13, 13));

        for (int i = 0; i < 3; i++) {
            infoGrid[3][2 + i * 3].setCellValue("初值");
            infoGrid[3][3 + i * 3].setCellValue("末值");
            infoGrid[3][4 + i * 3].setCellValue("误差%");
        }

    }

    private Map<String, List<ReportResultReq.MeterResult>> parseResultMap(List<ReportResultReq.MeterResult> meterResults) {
        Map<String, List<ReportResultReq.MeterResult>> resultMap = new HashMap<>();
        for (ReportResultReq.MeterResult r : meterResults) {
            List<ReportResultReq.MeterResult> resultList = resultMap.computeIfAbsent(r.getMeterNo(), m -> Lists.newArrayList());
            resultList.add(r);
        }
        return resultMap;
    }

    private void updateStatistic(Procedure procedure, boolean nationalQualified, boolean factoryQualified) {
        procedure.setRecord(procedure.getRecord() == null ? 1 : procedure.getRecord() + 1);
        if (nationalQualified)
            procedure.setNationalQualified(procedure.getNationalQualified() == null ? 1 : procedure.getNationalQualified() + 1);
        if (factoryQualified)
            procedure.setFactoryQualified(procedure.getFactoryQualified() == null ? 1 : procedure.getFactoryQualified() + 1);
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
