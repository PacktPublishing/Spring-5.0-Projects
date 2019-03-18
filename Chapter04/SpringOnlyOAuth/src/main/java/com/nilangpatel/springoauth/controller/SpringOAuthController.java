package com.nilangpatel.springoauth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.nilangpatel.springoauth.constants.OAuthConstant;

@Controller
public class SpringOAuthController {

	private Logger logger =  LoggerFactory.getLogger(SpringOAuthController.class);


	@GetMapping("/")
	public String showHomePage(Model model) {
		logger.info("This is show home page method ");
		
		setProcessingData(model, OAuthConstant.TITLE_HOME_PAGE);
		return "home";
	}
	
	@GetMapping("/privatePage")
	public String showControlPage(Model model) {
		logger.info("This is privaet page ");
	    setProcessingData(model, OAuthConstant.TITLE_PRIVATE_PAGE);
	    return "private-page";
	}
	
	@GetMapping("/login")
	public String showLoginPage(@RequestParam(value = "error",required = false) String error,
			@RequestParam(value = "logout",	required = false) String logout,Model model) {
		logger.info("This is login page URL   ");
		
		if (error != null) {
			model.addAttribute("error", "Invalid Credentials provided.");
		}

		if (logout != null) {
			model.addAttribute("message", "Logged out");
		}
		
		setProcessingData(model, OAuthConstant.TITLE_LOGIN_PAGE);
		
		return "login";
	}

	
	
	/**
	 * This method will check if current logged in user has given role
	 * @param roleName
	 * @return true or false - if user has given role 
	 */
	private boolean checkIfUserHasRole(String roleName) {
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
	
	/**
	 * This method will return current user name
	 * @return
	 */
	@ModelAttribute("currentUserName")
	public String getCurrentUserName() {
		String name = "";SecurityContextHolder.getContext().getAuthentication().getName();
		if(SecurityContextHolder.getContext().getAuthentication() !=null) {
			if(SecurityContextHolder.getContext().getAuthentication() 
					instanceof OAuth2AuthenticationToken) {
				OAuth2AuthenticationToken oauth2Authentication = 
						(OAuth2AuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
				name = (String)oauth2Authentication.getPrincipal().getAttributes().get("name");
			}else {
				//in case if its not OAuth authentication, then write logic to fetch username
			}
		}
		return name;
	}
	
	/**
	 * This method stores various data which are required on presentation layer.
	 * @param model
	 * @param pageTitle
	 */
	private void setProcessingData(Model model,String pageTitle) {
		model.addAttribute(OAuthConstant.PAGE_TITLE, pageTitle);
	}
}
