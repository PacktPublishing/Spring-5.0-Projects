package com.nilangpatel.springauth.model;

public class LdapAuthStructure {

	private String ldapUrl;
	private String ldapBase;
	private String ldapManagerDn;
	private String ldapManagerPwd;
	private String userDnPattern;
	private String userSearchBase;
	private String groupSearchBase;
	
	public String getLdapUrl() {
		return ldapUrl;
	}
	public void setLdapUrl(String ldapUrl) {
		this.ldapUrl = ldapUrl;
	}
	public String getLdapBase() {
		return ldapBase;
	}
	public void setLdapBase(String ldapBase) {
		this.ldapBase = ldapBase;
	}
	public String getLdapManagerDn() {
		return ldapManagerDn;
	}
	public void setLdapManagerDn(String ldapManagerDn) {
		this.ldapManagerDn = ldapManagerDn;
	}
	public String getLdapManagerPwd() {
		return ldapManagerPwd;
	}
	public void setLdapManagerPwd(String ldapManagerPwd) {
		this.ldapManagerPwd = ldapManagerPwd;
	}
	public String getUserDnPattern() {
		return userDnPattern;
	}
	public void setUserDnPattern(String userDnPattern) {
		this.userDnPattern = userDnPattern;
	}
	public String getUserSearchBase() {
		return userSearchBase;
	}
	public void setUserSearchBase(String userSearchBase) {
		this.userSearchBase = userSearchBase;
	}
	public String getGroupSearchBase() {
		return groupSearchBase;
	}
	public void setGroupSearchBase(String groupSearchBase) {
		this.groupSearchBase = groupSearchBase;
	}
	
}
