package com.bookstore.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.book.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

}
