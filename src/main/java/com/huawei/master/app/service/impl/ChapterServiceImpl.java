package com.huawei.master.app.service.impl;

import com.huawei.master.app.dao.ChapterRepository;
import com.huawei.master.app.dao.ScoreRepository;
import com.huawei.master.app.domain.Chapter;
import com.huawei.master.app.domain.Score;
import com.huawei.master.app.service.ChapterService;
import com.huawei.master.core.system.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("chapterService")
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public void addScore(String chapterId, Integer value) {

        if (!chapterRepository.existsById(chapterId)) {
            throw new BusinessException("chapter [ " + chapterId + " ] is not existed...");
        }

        Chapter chapter = chapterRepository.findById(chapterId).get();
        Score score = chapter.getScore();
        if(score == null)
        {
            score = new Score(value, 1);
            scoreRepository.save(score);
            chapter.setScore(score);
            chapterRepository.save(chapter);
        }
        else
        {
            score.setSum(score.getSum() + value);
            score.setTimes(score.getTimes() + 1);
            scoreRepository.save(score);
        }
    }
}
