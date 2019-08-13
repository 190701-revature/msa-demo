package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Author;
import com.revature.repositories.AuthorRepository;

@Service
public class AuthorService {
	AuthorRepository authorRepository;

	@Autowired
	public void setAuthorRepository(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	public Author saveAuthor(Author author) {
		return authorRepository.save(author);
	}

	public Author getById(int id) {
		return authorRepository.findById(id)
			.orElseThrow( () -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
	}

	public Author update(Author author) {
		if (authorRepository.existsById(author.getId())) {
			return authorRepository.save(author);
		}
		throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
	}
	
}
