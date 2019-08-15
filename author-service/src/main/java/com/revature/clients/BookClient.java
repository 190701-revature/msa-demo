package com.revature.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.revature.clients.fallbacks.BookFallback;
import com.revature.dto.Book;

// Why use the term Client?
// We are using the term "client" because we act as a "client" to the book service when
// using it.
@FeignClient(name="book-service", fallback = BookFallback.class)
@Component
public interface BookClient {
	@GetMapping("/author/{id}")
	public List<Book> getByAuthorId(@PathVariable int id);
}
