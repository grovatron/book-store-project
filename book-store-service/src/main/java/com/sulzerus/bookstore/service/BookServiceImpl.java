package com.sulzerus.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sulzerus.bookstore.entity.Book;
import com.sulzerus.bookstore.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository repository;

	@Override
	public Book addBook(Book book) {
		Book addedBook = repository.save(book);
		return addedBook;
	}

	@Override
	public Book getBookById(String id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<Book> getAllBooks() {
		return repository.findAll();
	}

	@Override
	public void updateBook(String id, Book book) {
		if (!repository.existsById(id)) return;
		book.setId(id);
		repository.save(book);
	}

	@Override
	public void deleteBook(String id) {
		repository.deleteById(id);
	}

	@Override
	public void deleteBooks(List<String> ids) {
		for (String id : ids) {
			repository.deleteById(id);
		}
	}

}
