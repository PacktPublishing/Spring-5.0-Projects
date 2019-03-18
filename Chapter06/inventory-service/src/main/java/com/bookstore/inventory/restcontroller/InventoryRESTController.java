package com.bookstore.inventory.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.inventory.dto.BookDTO;
import com.bookstore.inventory.feignproxy.CatalogServiceProxy;

@RestController
@RequestMapping("/api/inventory")
public class InventoryRESTController {

	@Autowired
	CatalogServiceProxy catalogServiceProxy;
	
	@GetMapping("/get-inventory/{bookId}")
	public ResponseEntity<BookDTO> getInventory(@PathVariable("bookId") Integer bookId) {
		return catalogServiceProxy.getInventory(bookId);
	}
	
	@GetMapping("/test")
	public ResponseEntity<Void> getTest() {
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
	
	/*@GetMapping("/home")
    @SuppressWarnings("unchecked")
    public String howdy(Model model, Principal principal) {
        OAuth2Authentication authentication = (OAuth2Authentication) principal;
        Map<String, Object> user = (Map<String, Object>) authentication.getUserAuthentication().getDetails();
        model.addAttribute("user", user);
        return "home";
    }*/
}
