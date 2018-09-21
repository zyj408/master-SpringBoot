package com.huawei.master.app.dao;

import com.huawei.master.app.domain.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByNameLike(String name, Pageable page);
}
