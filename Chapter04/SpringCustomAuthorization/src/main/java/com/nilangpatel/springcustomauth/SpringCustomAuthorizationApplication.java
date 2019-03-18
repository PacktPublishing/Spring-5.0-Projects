package com.nilangpatel.springcustomauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@SpringBootApplication
public class SpringCustomAuthorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCustomAuthorizationApplication.class, args);
	}
	
}
