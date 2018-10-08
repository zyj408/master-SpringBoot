package com.huawei.master.measure.service;

import com.huawei.master.measure.controller.dto.ReportResultReq;

public interface MeasureService {

    void register(String userId);

    void report(ReportResultReq reportResultReq);
}
