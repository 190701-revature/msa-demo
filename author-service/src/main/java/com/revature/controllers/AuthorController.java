package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Author;
import com.revature.services.AuthorService;

@RestController
@RequestMapping("")
public class AuthorController {

	AuthorService authorService;
	
	@Autowired
	public void setAuthorService(AuthorService authorService) {
		this.authorService = authorService;
	}


	@PostMapping("")
	public Author saveAuthor(@RequestBody Author author) {
		return this.authorService.saveAuthor(author);
	}
	
	@GetMapping("/{id}")
	public Author getAuthorById(@PathVariable int id) {
		return authorService.getById(id);
	}
	
	@PutMapping("")
	public Author updateAuthor(@RequestBody Author author) {
		return authorService.update(author);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAuthor(@PathVariable int id) {
		authorService.deleteAuthor(id);
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> handleClientError(HttpClientErrorException e) {
		return ResponseEntity
				.status(e.getStatusCode())
				.body(e.getMessage());
				
	}
}
