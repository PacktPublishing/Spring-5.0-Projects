package com.nilangpatel.springauth.constants;

public interface LdapAuthConstant {

	/* Page title constants  */
	String PAGE_TITLE = "pageTitle";
	
	String TITLE_HOME_PAGE = "Home";
	String TITLE_PRIVATE_PAGE = "Private";
	String TITLE_LOGIN_PAGE = "Login";
	String TITLE_USER_PAGE = "User";
	String TITLE_ADMIN_PAGE = "Admin";
	
	/* LDAP Auth structure constants */
	String LDAP_USER_SEARCH ="ou=users";
	String LDAP_GROUP_SEARCH = "ou=roles";	
	String LDAP_USER_DN_PATTERN= "uid={0},ou=users";
	
	/* Roles */
	String ROLE_USER = "ROLE_USER";
	String ROLE_ADMIN = "ROLE_ADMIN";
}
