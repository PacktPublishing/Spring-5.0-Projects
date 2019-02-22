package com.bookstore.catalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.catalog.model.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

}
