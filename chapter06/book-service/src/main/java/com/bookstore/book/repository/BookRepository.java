package com.bookstore.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.book.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	
}
