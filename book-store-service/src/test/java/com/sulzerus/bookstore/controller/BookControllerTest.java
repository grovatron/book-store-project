package com.sulzerus.bookstore.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flextrade.jfixture.FixtureCollections;
import com.flextrade.jfixture.JFixture;
import com.sulzerus.bookstore.entity.Book;
import com.sulzerus.bookstore.service.BookService;

@RunWith(SpringJUnit4ClassRunner.class)
public class BookControllerTest {
	
	@Mock BookService bookService;
	@InjectMocks
	private BookController bookController;
	
	private JFixture fixture;
	private ObjectMapper objectMapper;
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(bookController)
				.build();
		fixture = new JFixture();
		objectMapper = new ObjectMapper();
	}

	@Test
	public void addBook_VerifyCallsBookServiceAddBook() throws JsonProcessingException, Exception {
		// arrange
		Book book = fixture.create(Book.class);
		
		// act
		mockMvc.perform(
				post("/bookstore/api/v1/book/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(book)));
		
		// assert
		verify(bookService, times(1)).addBook(ArgumentMatchers.eq(book));
	}
	
	@Test
	public void addBook_VerifyReturnsAddedBook() throws JsonProcessingException, Exception {
		// arrange
		Book bookToAdd = fixture.create(Book.class);
		Book bookToReturn = fixture.create(Book.class);
		when(bookService.addBook(bookToAdd)).thenReturn(bookToReturn);
		
		// act / assert
		mockMvc.perform(
				post("/bookstore/api/v1/book/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(bookToAdd)))
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(bookToReturn)));
	}
	
	@Test
	public void getBookById_VerifyCallsBookServiceGetBookById() throws Exception {
		// arrange
		String id = fixture.create(String.class);
		
		// act
		mockMvc.perform(
				get("/bookstore/api/v1/book/{id}", id));
		
		// assert
		verify(bookService, times(1)).getBookById(id);
	}
	
	@Test
	public void getBookById_VerifyReturnsBook() throws Exception {
		// arrange
		String id = fixture.create(String.class);
		Book book = fixture.create(Book.class);
		when(bookService.getBookById(id)).thenReturn(book);
		
		// act / assert
		mockMvc.perform(
				get("/bookstore/api/v1/book/{id}", id))
			   .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(book)));
	}
	
	@Test
	public void getAllBooks_VerifyCallsBookServiceGetAllBooks() throws Exception {
		// act
		mockMvc.perform(
				get("/bookstore/api/v1/book/"));
		
		// assert
		verify(bookService, times(1)).getAllBooks();
	}
	
	@Test
	public void getAllBooks_VerifyReturnsListOfBooks() throws Exception {
		// arrange
		FixtureCollections fixtureCollections = fixture.collections();
		List<Book> books = (List<Book>) fixtureCollections.createCollection(Book.class);
		when(bookService.getAllBooks()).thenReturn(books);
		
		// act / assert
		mockMvc.perform(
				get("/bookstore/api/v1/book/"))
			   .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(books)));
	}
	
	@Test
	public void updateBook_VerifyCallsBookServiceUpdateBook() throws Exception {
		// arrange
		String id = fixture.create(String.class);
		Book book = fixture.create(Book.class);
		
		// act
		mockMvc.perform(
				put("/bookstore/api/v1/book/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(book)));
		
		// assert
		verify(bookService, times(1)).updateBook(id, book);
	}
	
	@Test
	public void deleteBook_VerifyCallsBookServiceDeleteBook() throws Exception {
		// arrange
		String id = fixture.create(String.class);
		
		// act
		mockMvc.perform(
				delete("/bookstore/api/v1/book/{id}", id));
		
		// assert
		verify(bookService, times(1)).deleteBook(id);
	}
	
	@Test
	public void deleteBooks_VerifyCallsBookServiceDeleteBooks() throws Exception {
		// arrange
		FixtureCollections fixtureCollections = fixture.collections();
		List<String> ids = (List<String>) fixtureCollections.createCollection(String.class);
		
		// act
		mockMvc.perform(
				delete("/bookstore/api/v1/book/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ids)));
		
		// assert
		verify(bookService, times(1)).deleteBooks(ids);
	}

}
