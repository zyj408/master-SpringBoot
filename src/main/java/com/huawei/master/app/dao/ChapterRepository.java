package com.huawei.master.app.dao;

import com.huawei.master.app.domain.Chapter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChapterRepository extends MongoRepository<Chapter, String> {
}
