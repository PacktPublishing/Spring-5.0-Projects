package com.nilangpatel.springldap.constants;

public interface LdapAuthConstant {

	/* Page title constants  */
	String PAGE_TITLE = "pageTitle";
	
	String TITLE_HOME_PAGE = "Home";
	String TITLE_PRIVATE_PAGE = "Private";
	String TITLE_LOGIN_PAGE = "Login";
	
	/* LDAP Auth structure constants */
	String LDAP_USER_SEARCH ="ou=users";
	String LDAP_GROUP_SEARCH = "ou=roles";	
	String LDAP_USER_DN_PATTERN= "uid={0},ou=users";
}
