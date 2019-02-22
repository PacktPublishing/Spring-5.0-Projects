package com.bookstore.book.restcontroller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.book.model.Book;
import com.bookstore.book.repository.BookRepository;

@RestController
@RequestMapping("/book")
public class BookRESTController {

	BookRepository bookRepository;
	
	public BookRESTController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	@GetMapping("/get-all-books")
	public List<Book> getAllBooks(){
		return bookRepository.findAll();
	}
}
