package com.nilangpatel.springauthresource.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class WebController {

	private Logger logger =  LoggerFactory.getLogger(WebController.class);
	
	@GetMapping("/")
	public String showHomePage(Model model) {
		logger.info("This is show home page method ");
		setProcessingData(model, "Home Page");
		return "home";
	}
	
	@GetMapping("/privatePage")
	public String showControlPage(Model model) {
		logger.info("This is privaet page ");
	    setProcessingData(model, "Private Page");
	    return "private-page";
	}
	
	@GetMapping("/customAuth")
	public String authorizeUser(Model model,@Value("${custom.auth.authorization-uri}") String authorizationUri,
			@Value("${custom.auth.client-id}") String clientId,
			@Value("${custom.auth.client-secret}") String clientSecret,
			@Value("${custom.auth.grant-type}") String grantType,
			@Value("${custom.auth.response-type}") String responseType) {
	    
	    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(authorizationUri)
                .queryParam("username", clientId)
                .queryParam("password", clientSecret)
                .queryParam("grant_type", grantType)
                .queryParam("response_type", responseType)
                .queryParam("client_id", clientId);
	    
	    return "redirect:"+uriBuilder.toUriString();
	}
	
	
	/**
	 * This method will check if current logged in user has given role
	 * @param roleName
	 * @return true or false - if user has given role 
	 */
	private boolean checkIfUserHasRole(String roleName) {
		for(GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			logger.info("=====>"+grantedAuthority.getAuthority());
		}
		
		 boolean hasUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
		            .anyMatch(r -> r.getAuthority().equals(roleName));
		 
		 return hasUserRole;
	}
	
	/**
	 * This method will check if valid user is logged in.
	 * @return boolean if user is logged In
	 */
	@ModelAttribute("validUserLogin")
	public boolean isUserLoggedIn() {
		return SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
				 //when Anonymous Authentication is enabled
				 !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken); 
	}
	
	@ModelAttribute("hasUserRole")
	public boolean checkIfUserHasUserRole(){
		return checkIfUserHasRole("USER");
	}
	
	/**
	 * This method will return current user name
	 * @return
	 */
	@ModelAttribute("currentUserName")
	public String getCurrentUserName() {
		return  SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	/**
	 * This method stores various data which are required on presentation layer.
	 * @param model
	 * @param pageTitle
	 */
	private void setProcessingData(Model model,String pageTitle) {
		model.addAttribute("title", pageTitle);
	}
}
