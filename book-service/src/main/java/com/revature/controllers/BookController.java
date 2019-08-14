package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.model.Book;
import com.revature.services.BookService;

@RestController
@RequestMapping("")
public class BookController {
	
	BookService bookService;
	
	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping("")
	public Book saveBook(@RequestBody Book book) {
		return this.bookService.saveBook(book);
	}
	
	@GetMapping("/{id}")
	public Book getById(@PathVariable int id) {
		return this.bookService.getById(id);
	}
	
	@GetMapping("/author/{id}")
	public List<Book> getByAuthorId(@PathVariable int id) {
		return this.bookService.findBooksByAuthorId(id);
	}
	
	@PutMapping("")
	public Book putBook(@RequestBody Book book) {
		return this.bookService.putBook(book);
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> handleClientError(HttpClientErrorException e) {
		return ResponseEntity
				.status(e.getStatusCode())
				.body(e.getMessage());
				
	}
}







