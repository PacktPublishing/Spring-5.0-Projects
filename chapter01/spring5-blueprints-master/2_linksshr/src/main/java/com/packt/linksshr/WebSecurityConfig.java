package com.packt.linksshr;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class WebSecurityConfig{
	
	
	@Bean
	SecurityWebFilterChain security(ServerHttpSecurity httpSecurity) {
		
		return httpSecurity.authorizeExchange()
				.pathMatchers(HttpMethod.GET, "/").permitAll()
				.pathMatchers(HttpMethod.GET, "/api/links/**").permitAll()
				.pathMatchers(HttpMethod.GET, "/links/**").permitAll()
				.pathMatchers(HttpMethod.GET, "/static/**").permitAll()
				.anyExchange().authenticated()
				.and()
				.formLogin()
				.and()
				.csrf().disable()
				.build();
	}
}
