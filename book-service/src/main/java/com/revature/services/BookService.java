package com.revature.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.model.Book;
import com.revature.repositories.BookRepository;

@Service
public class BookService {

	BookRepository bookRepository;

	@Autowired
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}

	public Book getById(int id) {
		return bookRepository.findById(id)
				.orElseThrow( 
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	public Book putBook(Book book) {
		if (bookRepository.existsById(book.getId())) {
			return this.bookRepository.save(book);
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
	}
	
	public List<Book> findBooksByAuthorId(int authorId) {
		return bookRepository.findAllByAuthorId(authorId);
	}
	
	@Transactional
	public void deleteBooksByAuthorId(int authorId) {
		bookRepository.deleteBooksByAuthorId(authorId);
	}
	
}
