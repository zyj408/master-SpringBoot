package com.huawei.master.measure.service;

import com.huawei.master.measure.controller.dto.QueryResultReq;
import com.huawei.master.measure.domain.FlowResult;
import org.springframework.data.domain.Page;

public interface ResultService {
    Page<FlowResult> query(QueryResultReq queryResultReq);
}
