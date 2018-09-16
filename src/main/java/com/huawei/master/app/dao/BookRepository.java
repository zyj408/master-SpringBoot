package com.huawei.master.app.dao;

import com.huawei.master.app.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
