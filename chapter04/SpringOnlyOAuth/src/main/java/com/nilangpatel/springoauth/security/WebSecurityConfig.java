package com.nilangpatel.springoauth.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ComponentScan("com.nilangpatel.springldap.security")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	private Logger logger =  LoggerFactory.getLogger(WebSecurityConfig.class);
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**");
		web.ignoring().antMatchers("/css/**");
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .oauth2Login();
		
		logger.info("configure method is called to make the resources secure ...");
		
		//super.configure(http);
		
	}
}
