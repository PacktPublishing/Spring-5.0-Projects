package com.bookstore.inventory.feignproxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bookstore.inventory.dto.BookDTO;
/* Without ribbon  */
//@FeignClient(name="catalog-service", url="http://localhost:8792", path="/api/catalog" )

/* With ribbon client and without Eureka */
/*@FeignClient(name="catalog-service", path="/api/catalog" )
@RibbonClient(name="catalog-test")*/

/* With ribbon client and with Eureka */
@FeignClient(name="catalog-service", path="/api/catalog" )
@RibbonClient(name="catalog-service")
public interface CatalogServiceProxy {
	@GetMapping("/get-book/{bookId}")
	public ResponseEntity<BookDTO> getInventory(@PathVariable("bookId") Integer bookId);
}
