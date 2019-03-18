package com.bookstore.catalog.restcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RefreshScope
public class TestRESTController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${catalog.sample.data: DummyData}")
	private String data;
	
	/*
	 * This is just for testing Cloud configuration feature
	 * */
	@GetMapping("/getSampleData")
	public String getSampleData() {
		logger.info(" sample data value is -->"+this.data);
		return this.data;
	}
}
