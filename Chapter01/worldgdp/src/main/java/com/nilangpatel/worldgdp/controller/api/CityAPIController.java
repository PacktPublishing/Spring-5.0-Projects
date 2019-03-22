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

import com.nilangpatel.worldgdp.dao.CityDAO;
import com.nilangpatel.worldgdp.model.City;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/cities")
public class CityAPIController {

	@Autowired CityDAO cityDao;
	
	@GetMapping("/{countryCode}")
	public ResponseEntity<?> getCities(@PathVariable String countryCode, 
		@RequestParam(name="pageNo", defaultValue="1") Integer pageNo){
		try {
			return new ResponseEntity<>(cityDao.getCities(countryCode, pageNo), 
					HttpStatus.OK);
		}catch(Exception ex) {
			System.out.println("Error while getting cities for country: {}"+ 
					countryCode+ ex);
			return new ResponseEntity<>("Error while getting cities", 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/{countryCode}")
	public ResponseEntity<?> addCity(@PathVariable String countryCode, 
			@Valid @RequestBody City city){
		try {
			cityDao.addCity(countryCode, city);
			return new ResponseEntity<>(city, HttpStatus.CREATED);
		}catch(Exception ex) {
			System.out.println("Error while adding city: {} to country: {}"+ 
					city+ countryCode+ ex);
			return new ResponseEntity<>("Error while adding city", 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{cityId}")
	public ResponseEntity<?> deleteCity(@PathVariable Long cityId){
		try {
			cityDao.deleteCity(cityId);
			return ResponseEntity.ok().build();
		}catch(Exception ex) {
			System.out.println("Error occurred while deleting city : {}"+ cityId+ ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Error occurred while deleting the city: " + cityId);
		}
	}
	
}
