package com.huawei.master.app.service;

import com.huawei.master.app.controller.dto.request.WorkshopQueryReq;
import com.huawei.master.app.domain.Workshop;

import java.util.List;

public interface WorkshopService {

    void relate(String workshopId, List<String> chapterIds);

    List<Workshop> query(WorkshopQueryReq workshopQueryReq);
}
