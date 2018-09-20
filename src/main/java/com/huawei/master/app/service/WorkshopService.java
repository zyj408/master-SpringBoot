package com.huawei.master.app.service;

import java.util.List;

public interface WorkshopService {

    void relate(String workshopId, List<String> chapterIds);
}
