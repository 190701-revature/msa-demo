package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.dto.Book;
import com.revature.models.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
