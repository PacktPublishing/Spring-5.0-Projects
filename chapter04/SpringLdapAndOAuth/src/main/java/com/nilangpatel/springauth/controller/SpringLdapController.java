package com.nilangpatel.springauth.controller;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Name;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nilangpatel.springauth.authprovider.CustomLdapAuthProvider;
import com.nilangpatel.springauth.constants.LdapAuthConstant;
import com.nilangpatel.springauth.model.LdapAuthUser;
import com.nilangpatel.springauth.service.LdapAuthService;

@Controller
public class SpringLdapController {

	private Logger logger =  LoggerFactory.getLogger(SpringLdapController.class);

	@Autowired
	private LdapAuthService ldapAuthService;

	@GetMapping("/")
	public String showHomePage(Model model) {
		logger.info("This is show home page method ");

		setProcessingData(model, LdapAuthConstant.TITLE_HOME_PAGE);
		return "home";
	}
	
	@GetMapping("/privatePage")
	public String showControlPage(Model model) {
		logger.info("This is privaet page ");
	    setProcessingData(model, LdapAuthConstant.TITLE_PRIVATE_PAGE);
	    return "private-page";
	}
	
	@GetMapping("/adminPage")
	public String showAdminPage(Model model) {
		logger.info("This is admin page ");
	    setProcessingData(model, LdapAuthConstant.TITLE_ADMIN_PAGE);
	    return "admin-page";
	}
	
	@GetMapping("/userPage")
	public String showUserPage(Model model) {
		logger.info("This is use page ");
	    setProcessingData(model, LdapAuthConstant.TITLE_USER_PAGE);
	    return "user-page";
	}
	
	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;
	
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
		
		String authorizationRequestBaseUri = "oauth2/authorization";
		Map<String, String> oauth2AuthenticationUrls = new HashMap<String, String>();
		
		Iterable<ClientRegistration> clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
		
		clientRegistrations.forEach(registration -> 
		oauth2AuthenticationUrls.put(registration.getClientName(), 
				authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
		model.addAttribute("urls", oauth2AuthenticationUrls);

		setProcessingData(model, LdapAuthConstant.TITLE_LOGIN_PAGE);
		
		return "login";
	}
	
	@Autowired
	CustomLdapAuthProvider customLdapAuthProvider;

	@PostMapping("/ldapLogin")
	public String ldapAuthenticate(HttpServletRequest req,@RequestParam(value = "username",required = true) String username,
			@RequestParam(value = "password",	required = true) String password,RedirectAttributes redirectAttributes) {
		
		UsernamePasswordAuthenticationToken authReq
		= new UsernamePasswordAuthenticationToken(username, password);
		Authentication auth = customLdapAuthProvider.authenticate(authReq);
		if(auth !=null) {
			logger.info(" If user is authenticated  .... "+auth.isAuthenticated());
			SecurityContext sc = SecurityContextHolder.getContext();
			sc.setAuthentication(auth);
			HttpSession session = req.getSession(true);
			session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);

			if(auth.isAuthenticated() == true) {
				return "redirect:/privatePage"; 
			}else {
				redirectAttributes.addAttribute("error", "true");
				return "redirect:/login";
			}
		}else { // failed authentication - either username or password fails.
			redirectAttributes.addAttribute("error", "true");
			return "redirect:/login";
		}
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
	
	@ModelAttribute("hasAdminRole")
	public boolean checkIfUserHasAdminRole(){
		return checkIfUserHasRole(LdapAuthConstant.ROLE_ADMIN);
	}
	
	
	@ModelAttribute("hasUserRole")
	public boolean checkIfUserHasUserRole(){
		return checkIfUserHasRole(LdapAuthConstant.ROLE_USER);
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
				String userName =  SecurityContextHolder.getContext().getAuthentication().getName();
				LdapAuthUser ldapUser =  ldapAuthService.getUser(userName);
				if(ldapUser !=null) {
					name = ldapUser.getFirstName()+" "+ldapUser.getSurName();
				}
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
		model.addAttribute(LdapAuthConstant.PAGE_TITLE, pageTitle);
	}
	
	/**
	 * This method can be used to add new user by passing required parameters.
	 */
	private void addUser() {
		LdapAuthUser ldapUser = new LdapAuthUser();
        ldapUser.setUserName("kpatel");
        ldapUser.setPassword("test1234");
        ldapUser.setFirstName("Komal");
        ldapUser.setSurName("Patel");
        ldapUser.setIsNew(true);
        
        Name dn = LdapNameBuilder.newInstance()
              .add("ou=users")
              .add("uid=kpatel")
              .build();
        ldapUser.setId(dn);
        
        ldapAuthService.addUser(ldapUser);
	}
}
