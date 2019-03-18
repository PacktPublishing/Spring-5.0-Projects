package com.nilangpatel.springauth.security;

import javax.naming.Name;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;

import com.nilangpatel.springauth.model.LdapAuthStructure;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private Logger logger =  LoggerFactory.getLogger(WebSecurityConfig.class);
	
	@Autowired
	LdapAuthStructure ldapAuthStructure;
		
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/js/**");
			web.ignoring().antMatchers("/css/**");
		}

		/**
    	 * Use this method for OAuth authentication. While testing with LDAP authentication, just comment this method.
    	 */
    	@Override
    	protected void configure(HttpSecurity http) throws Exception {
    	   http.authorizeRequests()
    	   	.antMatchers("/","/ldapLogin","/login").permitAll()
    		.antMatchers("/adminPage/").hasAnyAuthority("ADMIN")
    		.antMatchers("/userPage/").hasAnyAuthority("USER","ADMIN")
    		.anyRequest().fullyAuthenticated()
    			.and()
            .oauth2Login().loginPage("/login")
            	.defaultSuccessUrl("/privatePage",true)
            	.failureUrl("/login?error=true")
            	.and()
            .logout()
            	.permitAll().logoutSuccessUrl("/login?logout=true");
    	}
    	
    	
		
		/*
		 * User below two methods only for LDAP authentication and authorization integration.
		 * 
		 */
		/*@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.antMatchers("/","/login").permitAll()
				.antMatchers("/adminPage/").hasAnyAuthority("ADMIN")
				.antMatchers("/userPage/").hasAnyAuthority("USER")
				.anyRequest().authenticated()
				.and()
	        .formLogin().loginPage("/login").permitAll()
	        	.defaultSuccessUrl("/privatePage",true)
	        	.failureUrl("/login?error=true")
	            .and()
	        .logout()
	            .permitAll().logoutSuccessUrl("/login?logout=true");
		} 
		@Override
		protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
			authManagerBuilder.ldapAuthentication()
			.userDnPatterns(ldapAuthStructure.getUserDnPattern())
			.userSearchBase(ldapAuthStructure.getUserSearchBase())
			.userSearchFilter("uid={0}").rolePrefix("")
			.groupSearchBase(ldapAuthStructure.getGroupSearchBase())
			.groupSearchFilter("member={0}").rolePrefix("")
			.contextSource()
				.url(ldapAuthStructure.getLdapUrl()+"/"+ldapAuthStructure.getLdapBase())
				.managerDn(ldapAuthStructure.getLdapManagerDn()).managerPassword(ldapAuthStructure.getLdapManagerPwd())
				.and()
			.passwordCompare()
				.passwordEncoder(new LdapShaPasswordEncoder())
				.passwordAttribute("userPassword");
		} */
	
	
	
}
