package com.huawei.master.app.service.impl;

import com.google.common.collect.Lists;
import com.huawei.master.app.controller.dto.request.WorkshopQueryReq;
import com.huawei.master.app.dao.ChapterRepository;
import com.huawei.master.app.dao.WorkshopRepository;
import com.huawei.master.app.domain.Chapter;
import com.huawei.master.app.domain.Workshop;
import com.huawei.master.app.service.WorkshopService;
import com.huawei.master.core.common.Page;
import com.huawei.master.core.system.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("workshopService")
public class WorkshopServiceImpl implements WorkshopService {

    @Autowired
    private WorkshopRepository workshopRepository;

    @Autowired
    private ChapterRepository chapterRepository;


    @Override
    public void relate(String workshopId, List<String> chapterIds) {
        if (!workshopRepository.existsById(workshopId)) {
            throw new BusinessException("book is not existed...");
        }
        for (String chapterId : chapterIds) {
            if (!chapterRepository.existsById(chapterId)) {
                throw new BusinessException("chapter [ " + chapterId + " ] is not existed...");
            }
        }

        Workshop workshop = workshopRepository.findById(workshopId).get();
        ArrayList<Chapter> chapters = Lists.newArrayList(chapterRepository.findAllById(chapterIds));

        //讨论会关联章节
        workshop.setChapters(chapters);
        workshopRepository.save(workshop);
    }

    @Override
    public List<Workshop> query(WorkshopQueryReq workshopQueryReq) {
        String name = workshopQueryReq.getName();
        Page page = workshopQueryReq.getPage();

        PageRequest pageRequest = PageRequest.of(page.getPage() - 1, page.getRows());
        return workshopRepository.findByNameLike(name, pageRequest);
    }
}
