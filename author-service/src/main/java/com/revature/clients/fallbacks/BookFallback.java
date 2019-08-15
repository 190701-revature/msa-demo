package com.revature.clients.fallbacks;

import java.util.List;

import org.springframework.stereotype.Component;

import com.revature.clients.BookClient;
import com.revature.dto.Book;

@Component
public class BookFallback implements BookClient {

	@Override
	public List<Book> getByAuthorId(int id) {
		System.out.println("Book client failure! Using fallback!");
		return null;
	}

}
