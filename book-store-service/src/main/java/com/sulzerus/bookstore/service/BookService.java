package com.sulzerus.bookstore.service;

import java.util.List;

import com.sulzerus.bookstore.entity.Book;

public interface BookService {

	public Book addBook(Book book);
	
	public Book getBookById(String id);
	
	public List<Book> getAllBooks();
	
	public void updateBook(String id, Book book);
	
	public void deleteBook(String id);
	
	public void deleteBooks(List<String> ids);
	
}
