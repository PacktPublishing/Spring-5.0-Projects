package com.nilangpatel.blogpress.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nilangpatel.blogpress.constants.BlogpressConstants;

@Configuration
@EnableWebSecurity
@ComponentScan("com.nilangpatel.blogpress.security")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	  web.ignoring().antMatchers("/js/**");
	  web.ignoring().antMatchers("/css/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/api/listBlogs","/viewBlog","/addComment","/search").permitAll()
            .antMatchers("/controlPage/","/addNewBlog/").hasAnyAuthority(BlogpressConstants.ROLE_USER,BlogpressConstants.ROLE_ADMIN)
            .antMatchers("/showComments/").hasAnyAuthority(BlogpressConstants.ROLE_ADMIN)
            .and()
        .formLogin().loginPage("/login").permitAll()
        	.defaultSuccessUrl("/controlPage",true)
        	.failureUrl("/login?error=true")
            .and()
        .logout()
            .permitAll().logoutSuccessUrl("/login?logout=true");
		super.configure(http);
		
	}

	// create users and admin
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    	BCryptPasswordEncoder encoder = passwordEncoder();
        auth.inMemoryAuthentication().passwordEncoder(encoder)
                .withUser("blogUser1").password(encoder.encode("password")).authorities("ROLE_USER")
                .and()
                .withUser("blogUser2").password(encoder.encode("password")).authorities("ROLE_USER")
                .and()
                .withUser("blogAdmin").password(encoder.encode("password")).authorities("ROLE_ADMIN");	
    }
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
}
