package com.sulzerus.bookstore.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.flextrade.jfixture.FixtureCollections;
import com.flextrade.jfixture.JFixture;
import com.sulzerus.bookstore.entity.Book;
import com.sulzerus.bookstore.repository.BookRepository;

public class BookServiceImplTest {
	
	@Mock BookRepository repository;
	@InjectMocks BookServiceImpl sut;
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
	
	private JFixture fixture;
	
	@Before
	public void init() {
		fixture = new JFixture();
	}

	@Test
	public void addBook_VerifyCallsRepositorySave() {
		// arrange
		Book book = fixture.create(Book.class);
		
		// act
		sut.addBook(book);
		
		// assert
		verify(repository, times(1)).save(book);
	}
	
	@Test
	public void addBook_VerifyReturnsAddedBook() {
		// arrange
		Book bookToAdd = fixture.create(Book.class);
		Book bookAdded = fixture.create(Book.class);
		when(repository.save(bookToAdd)).thenReturn(bookAdded);
		
		// act
		Book result = sut.addBook(bookToAdd);
		
		// assert
		assertEquals(result, bookAdded);
	}

	@Test
	public void getBookById_VerifyCallsGetBookById() {
		// arrange
		String id = fixture.create(String.class);
		
		// act
		sut.getBookById(id);
		
		// assert
		verify(repository, times(1)).findById(id);
	}
	
	@Test
	public void getBookById_ReturnsBookIfExists() {
		// arrange
		String id = fixture.create(String.class);
		Book book = fixture.create(Book.class);
		when(repository.findById(id)).thenReturn(Optional.of(book));
		
		// act
		Book result = sut.getBookById(id);
		
		//assert
		assertEquals(result, book);
	}
	
	@Test
	public void getBookById_ReturnsNullIfNotExists() {
		// arrange
		String id = fixture.create(String.class);
		when(repository.findById(id)).thenReturn(Optional.<Book>empty());
		
		// act
		Book result = sut.getBookById(id);
		
		//assert
		assertEquals(result, null);
	}
	
	@Test
	public void getAllBooks_VerifyReturnsListOfBooks() {
		// arrange
		FixtureCollections fixtureCollections = fixture.collections();
		List<Book> books = new ArrayList<>();
		fixtureCollections.addManyTo(books, Book.class);
		when(repository.findAll()).thenReturn(books);
		
		// act
		List<Book> result = sut.getAllBooks();
		
		// assert
		assertEquals(result, books);
	}
	
	@Test
	public void updateBook_VerifyCallsRepositoryExistsById() {
		// arrange
		String id = fixture.create(String.class);
		Book book = fixture.create(Book.class);
		
		// act
		sut.updateBook(id, book);
		
		// assert
		verify(repository, times(1)).existsById(id);
	}
	
	@Test
	public void updateBook_BookExists_VerifyCallsBookSetId() {
		// arrange
		String id = fixture.create(String.class);
		Book bookSpy = spy(fixture.create(Book.class));
		when(repository.existsById(id)).thenReturn(true);
		
		// act
		sut.updateBook(id, bookSpy);
		
		// assert
		verify(bookSpy, times(1)).setId(id);
	}
	
	@Test
	public void updateBook_BookExists_VerifyCallsRepositorySave() {
		// arrange
		String id = fixture.create(String.class);
		Book book = fixture.create(Book.class);
		when(repository.existsById(id)).thenReturn(true);
		
		// act
		sut.updateBook(id, book);
		
		// assert
		verify(repository, times(1)).save(book);
	}
	
	@Test
	public void updateBook_BookDoesNotExist_VerifyDoesNotCallBookOrRepository() {
		// arrange
		String id = fixture.create(String.class);
		Book bookSpy = spy(fixture.create(Book.class));
		
		// act
		sut.updateBook(id, bookSpy);
		
		// assert
		verify(bookSpy, never()).setId(id);
		verify(repository, never()).save(bookSpy);
	}
	
	@Test
	public void deleteBook_VerifyCallsRepositoryDeleteById() {
		// arrange
		String id = fixture.create(String.class);
		
		// act
		sut.deleteBook(id);
		
		// assert
		verify(repository, times(1)).deleteById(id);
	}
	
	@Test
	public void deleteBooks_VerifyCallsRepositoryDeleteById() {
		// arrange
		int numberOfIds = 5;
		FixtureCollections fixtureCollections = fixture.collections();
		List<String> ids = (List<String>) fixtureCollections.createCollection(String.class, numberOfIds);
		
		// act
		sut.deleteBooks(ids);
		
		// assert
		for (String id : ids) {
			verify(repository, times(1)).deleteById(id);
		}
	}
	
	@Test
	public void deleteBooks_NoIds_VerifyCallsRepositoryDeleteById() {
		// arrange
		int numberOfIds = 0;
		FixtureCollections fixtureCollections = fixture.collections();
		List<String> ids = (List<String>) fixtureCollections.createCollection(String.class, numberOfIds);
		
		// act
		sut.deleteBooks(ids);
		
		// assert
		verify(repository, never()).deleteById(ArgumentMatchers.anyString());
	}
}
