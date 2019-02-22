package com.bookstore.inventory.feignproxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bookstore.inventory.dto.BookDTO;

@FeignClient(name="zuul-api-gateway", path="/catalog-test/api/catalog" )
@RibbonClient(name="catalog-test")
public interface CatalogServiceProxy {
	@GetMapping("/get-book/{bookId}")
	public ResponseEntity<BookDTO> getInventory(@PathVariable("bookId") Integer bookId);
}
