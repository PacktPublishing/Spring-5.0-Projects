package com.bookstore.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.catalog.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	
}
