package com.bookstore.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.book.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
