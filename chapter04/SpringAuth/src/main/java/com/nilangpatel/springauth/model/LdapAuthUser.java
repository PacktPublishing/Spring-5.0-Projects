package com.nilangpatel.springauth.model;

import javax.naming.Name;

import org.springframework.data.domain.Persistable;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;

@Entry(
  base = "ou=users", 
  objectClasses = {"top", "person", "organizationalPerson","inetOrgPerson"})
public final class LdapAuthUser implements Persistable<Name> {

	@Id
    private Name id;
    
	@Attribute(name = "uid")
	@DnAttribute(value="uid")
    private String userName;
	
	@Attribute(name = "sn")
    private String surName;
	
	@Attribute(name = "cn") 
    private String firstName;
	
	@Attribute(name = "userPassword") 
    private String password;
	
	@Transient
	private boolean isNew;
    
	public Name getId() {
		return id;
	}
	public void setId(Name id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	@Override
	public boolean isNew() {
		return this.isNew;
	}
    
    
    
}
