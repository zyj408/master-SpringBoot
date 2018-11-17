package com.huawei.master.measure.service;

import com.huawei.master.measure.controller.dto.ReportResultReq;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MeasureService {

    void register(String userId);

    void report(ReportResultReq reportResultReq);

    void export(String name, HttpServletResponse response) throws IOException;
}
