package com.sulzerus.bookstore.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sulzerus.bookstore.entity.Book;

public interface BookRepository extends MongoRepository<Book, String> {

}
