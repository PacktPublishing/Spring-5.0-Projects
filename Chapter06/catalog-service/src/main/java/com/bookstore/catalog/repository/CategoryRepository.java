package com.bookstore.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.catalog.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
