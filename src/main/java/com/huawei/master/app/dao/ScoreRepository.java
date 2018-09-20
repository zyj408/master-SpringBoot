package com.huawei.master.app.dao;

import com.huawei.master.app.domain.Score;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScoreRepository extends MongoRepository<Score, String> {
}
