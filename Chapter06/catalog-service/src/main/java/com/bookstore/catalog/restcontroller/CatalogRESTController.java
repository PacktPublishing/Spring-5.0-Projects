package com.bookstore.catalog.restcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.catalog.dto.BookDTO;
import com.bookstore.catalog.model.Book;
import com.bookstore.catalog.model.Category;
import com.bookstore.catalog.model.Publisher;
import com.bookstore.catalog.repository.BookRepository;
import com.bookstore.catalog.repository.CategoryRepository;
import com.bookstore.catalog.repository.PublisherRepository;

@RestController
@RequestMapping("/api/catalog")
public class CatalogRESTController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private PublisherRepository publisherRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private Environment env;
	
	public CatalogRESTController(BookRepository bookRepository,
								 PublisherRepository publisherRepository,
								 CategoryRepository categoryRepository) {
		this.bookRepository = bookRepository;
		this.publisherRepository =publisherRepository;
		this.categoryRepository = categoryRepository;
	}
	
	@GetMapping("/get-all-books")
	public List<Book> getAllBooks(){
		return bookRepository.findAll();
	}
	
	@GetMapping("/get-book/{bookId}")
	public ResponseEntity<BookDTO> getBook(@PathVariable("bookId") Integer bookId) {
		Book bookObject = null;
		Optional<Book> bookData =  bookRepository.findById(bookId);
		if(bookData.isPresent()) {
			bookObject =  bookData.get();
		}
		
		BookDTO bookDto = new BookDTO();
		if(bookObject !=null) {
			
			bookDto.setBookId(bookId);
			bookDto.setCategory(bookObject.getCategory().getId());
			bookDto.setLongDesc(bookObject.getLongDesc());
			bookDto.setPrice(bookObject.getPrice());
			bookDto.setPublisherId(bookObject.getPublisher().getId());
			bookDto.setSmallDesc(bookObject.getSmallDesc());
			bookDto.setTitle(bookObject.getTitle());
			bookDto.setPort(env.getProperty("local.server.port"));
			
			return new ResponseEntity(bookDto,HttpStatus.OK);
		}else {
			return new ResponseEntity(bookDto, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value="/addBook",consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> addNewBook(@Valid @RequestBody BookDTO bookDto,Errors errors) {
		
		// Add BookDTO validations here
		
		if(errors.hasErrors()) {
			List<String> errorMsg = new ArrayList<String>();
			errors.getAllErrors().forEach(a -> errorMsg.add(a.getDefaultMessage()));
			return new ResponseEntity<List<String>>(errorMsg, HttpStatus.BAD_REQUEST);
		}else {
			Book bookEntity = new Book();
			Publisher bookPublisher = getPublisher(bookDto.getPublisherId());
			bookEntity.setPublisher(bookPublisher);
			
			bookEntity.setPrice(bookDto.getPrice());
			bookEntity.setCategory(getCategory(bookDto.getCategory()));
			bookEntity.setLongDesc(bookDto.getLongDesc());
			bookEntity.setSmallDesc(bookDto.getSmallDesc());
			bookEntity.setTitle(bookDto.getTitle());
			
			
			bookRepository.save(bookEntity);
			List<String> msgLst = Arrays.asList("Book -"+bookDto.getTitle()+" has been added successfully");
			return new ResponseEntity<List<String>>(msgLst, HttpStatus.OK);
		}
	}
	
	/**
	 * This method simply returns publisher object form publisherId
	 * @param publisherId
	 * @return Publisher
	 */
	private Publisher getPublisher(Integer publisherId) {
		Optional<Publisher> publisherData =  publisherRepository.findById(publisherId);
		if(publisherData.isPresent()) {
			return publisherData.get();
		}else {
			return null;
		}
	}

	/**
	 * This method return the category list of matching categoryIds
	 * 
	 * @param categoryId
	 * @return
	 */
	private Category getCategory(Integer categoryId){
		Optional<Category> categoryData =  categoryRepository.findById(categoryId);
		if(categoryData.isPresent()) {
			return categoryData.get();
		}else {
			return null;
		}		
	}
	
}
