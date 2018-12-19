package com.packt.linksshr.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@Configuration
public class TestAppConfiguration {

	
	@Bean("testMongoClient")
	public MongoClient mongoClient() throws IOException {
		return MongoClients.create("mongodb://localhost");
	}
	
	@Bean("testMongoTemplate")
	public ReactiveMongoTemplate mongoTemplate() throws IOException {
		return new ReactiveMongoTemplate(mongoClient(), "links");
	}
	
	
}
