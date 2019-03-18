package com.bookstore.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	@Bean("authenticationManager")
	public AuthenticationManager authenticationManagerBean() throws
	Exception {
		AuthenticationManager authenticationManager = 
				super.authenticationManagerBean();
		return authenticationManager;
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**");
		web.ignoring().antMatchers("/css/**");
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication()
		.withUser("john").password(//"1234").authorities("USER");
				encoder().encode("999")).authorities("USER");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/oauth/authorize","/oauth/token","/").permitAll()
		.and()
		.formLogin().loginPage("/login").permitAll();
	}
	@Bean("encoder")
	public BCryptPasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}
}
