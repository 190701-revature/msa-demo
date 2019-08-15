package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.revature.clients.BookClient;
import com.revature.dto.Book;
import com.revature.messenger.Messenger;
import com.revature.models.Author;
import com.revature.repositories.AuthorRepository;

@Service
public class AuthorService {
	AuthorRepository authorRepository;
	BookClient bookClient;
	Messenger messenger;

	@Autowired
	public void setAuthorRepository(AuthorRepository authorRepository, BookClient bookClient,
				Messenger messenger) {
		this.authorRepository = authorRepository;
		this.bookClient = bookClient;
		this.messenger = messenger;
	}

	public Author saveAuthor(Author author) {
		return authorRepository.save(author);
	}

	public Author getById(int id) {
		Author author = authorRepository.findById(id)
				.orElseThrow( () -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		// Do feign client stuff
		List<Book> books = this.bookClient.getByAuthorId(author.getId());
		author.setBooks(books);
		
		return author;
	}

	
	public Author getByIdWithRestTemplate(int id) {
		Author author = authorRepository.findById(id)
			.orElseThrow( () -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
		
		RestTemplate template = new RestTemplate();

//		List<Book> list = template.getForObject(
//				"http://localhost:8080/books/author/"+author.getId(), Book.class
//				);
		
		List<Book> books = template.exchange("http://localhost:8080/books/author/"+author.getId(),
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Book>>() {}).getBody();
		
		System.out.println(books);
		
		author.setBooks(books);
		return author;
	}

	public Author update(Author author) {
		if (authorRepository.existsById(author.getId())) {
			return authorRepository.save(author);
		}
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
	}

	@Transactional
	public void deleteAuthor(int id) {
		authorRepository.deleteById(id);
		messenger.sendDeleteBooksMessage(id);
	}
	
}
