package com.revature.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.model.Book;

@RestController
@RequestMapping("")
public class BookController {
	@PostMapping("")
	public Book saveBook(@RequestBody Book book) {
		book.setName("Abby's Book");
		return book;
		
	}
}
