package com.nilangpatel.springcustomauth.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
	
	private Logger logger =  LoggerFactory.getLogger(WebController.class);

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
		
		return "login";
	}
	
	@GetMapping("/privatePage")
	public String showControlPage(Model model) {
		logger.info("This is privaet page ");
	    return "private-page";
	}
}
