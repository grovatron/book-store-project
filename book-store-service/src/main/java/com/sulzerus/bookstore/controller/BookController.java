package com.sulzerus.bookstore.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sulzerus.bookstore.entity.Book;
import com.sulzerus.bookstore.service.BookService;

@RestController
@RequestMapping("/bookstore/api/v1/book")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@PostMapping("/")
	public Book addBook(@Valid @RequestBody Book book) {
		return bookService.addBook(book);
	}
	
	@GetMapping("/{id}")
	public Book getBookById(@PathVariable("id") String id) {
		return bookService.getBookById(id);
	}
	
	@GetMapping("/")
	public List<Book> getAllBooks() {
		return bookService.getAllBooks();
	}
	
	@PutMapping("/{id}")
	public void updateBook(@PathVariable("id") String id, @Valid @RequestBody Book book) {
		bookService.updateBook(id, book);
	}
	
	@DeleteMapping("/{id}")
	public void deleteBook(@PathVariable("id") String id) {
		bookService.deleteBook(id);
	}
	
	@DeleteMapping("/")
	public void deleteBooks(@RequestBody List<String> ids) {
		bookService.deleteBooks(ids);
	}
}
