package com.huawei.master.measure.service;

import com.huawei.master.measure.controller.dto.FinishProcedureReq;
import com.huawei.master.measure.controller.dto.QueryProcedureReq;
import com.huawei.master.measure.controller.dto.StartProcedureReq;
import com.huawei.master.measure.domain.Procedure;
import org.springframework.data.domain.Page;

public interface ProcedureService {
    Page<Procedure> query(QueryProcedureReq queryProcedureReq);

    String start(StartProcedureReq startProcedureReq);

    void finish(FinishProcedureReq finishProcedureReq);

    void restart(StartProcedureReq startProcedureReq);
}
