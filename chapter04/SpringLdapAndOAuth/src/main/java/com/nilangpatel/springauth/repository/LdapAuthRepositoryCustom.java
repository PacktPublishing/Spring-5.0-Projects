package com.nilangpatel.springauth.repository;

import java.util.List;

import com.nilangpatel.springauth.model.LdapAuthUser;
import com.nilangpatel.springauth.model.LdapGranntedAuthority;

public interface LdapAuthRepositoryCustom  {

	LdapAuthUser findByUserName(String userName);
    List<LdapAuthUser> findByMatchingUserName(String username);
    boolean authenticateLdapUserWithContext(String userName, String password);
    boolean authenticateLdapUserWithLdapQuery(String userName, String password);
    void create(LdapAuthUser ldapAuthUser);
    void deleteFromTemplate(LdapAuthUser ldapAuthUser);
    void createByBindOperation(LdapAuthUser ldapAuthUser);
    void deleteFromTemplateWithUnbind(String userName);
    void updateWithTemplate(LdapAuthUser ldapAuthUser);
    LdapAuthUser findByUid(String uid);
    List<LdapAuthUser> findAllWithTemplate();
    List<LdapAuthUser> findBySurname(String surName);
    List<LdapGranntedAuthority> getUserAuthorities(String userName);
    
}
