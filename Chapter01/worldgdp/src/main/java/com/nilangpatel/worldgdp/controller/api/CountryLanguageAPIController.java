package com.nilangpatel.worldgdp.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nilangpatel.worldgdp.dao.CountryLanguageDAO;
import com.nilangpatel.worldgdp.model.CountryLanguage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/languages")
public class CountryLanguageAPIController {

	@Autowired CountryLanguageDAO cLanguageDao;
	
	@GetMapping("/{countryCode}")
	public ResponseEntity<?> getLanguages(@PathVariable String countryCode, 
		@RequestParam(name="pageNo", defaultValue="1") Integer pageNo){
		try {
			return ResponseEntity.ok(cLanguageDao.getLanguages(countryCode, pageNo));
		}catch(Exception ex) {
			System.out.println("Error while getting languages for country: {}"+ 
					countryCode+ ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error while languages cities");
		}
		
	}
	
	@PostMapping("/{countryCode}")
	public ResponseEntity<?> addLanguage(@PathVariable String countryCode, 
			@Valid @RequestBody CountryLanguage language){
		try {
			if ( cLanguageDao.languageExists(countryCode, language.getLanguage())) {
				return ResponseEntity.badRequest()
						.body("Language already exists for country");
			}
			cLanguageDao.addLanguage(countryCode, language);
			return ResponseEntity.ok(language);
		}catch(Exception ex) {
			System.out.println("Error while adding language: {} to country: {}"+ 
					language+ countryCode+ ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error while adding language");
		}
	}
	
	@DeleteMapping("/{countryCode}/language/{language}")
	public ResponseEntity<?> deleteLanguage(@PathVariable String countryCode, 
			@PathVariable String language){
		try {
			cLanguageDao.deleteLanguage(countryCode, language);;
			return ResponseEntity.ok().build();
		}catch(Exception ex) {
			System.out.println("Error occurred while deleting language : {}, for country: {}"+ 
					language+ countryCode+ ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Error occurred while deleting the language");
		}
	}
	
}
