package com.nilangpatel.springldap.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.nilangpatel.springldap.constants.LdapAuthConstant;
import com.nilangpatel.springldap.model.LdapAuthStructure;

@Configuration
@ComponentScan(basePackages = { "com.nilangpatel.springldap.config" })
public class LdapDataConfig {
	
	
	@Value("${spring.ldap.urls}")
    private String ldapUrls;

    @Value("${spring.ldap.base}")
    private String ldapBase;

    @Value("${spring.ldap.password}")
    private String ldapManagerPwd;
    
    @Value("${spring.ldap.username}")
    private String ldapManagerUserName;
    
	
	@Bean("ldapAuthStructure")
	public LdapAuthStructure getLDAPAuthStructure() {
		LdapAuthStructure authStructure = new LdapAuthStructure();
		
		authStructure.setLdapUrl(ldapUrls);
		authStructure.setLdapBase(ldapBase);
		authStructure.setLdapManagerDn(ldapManagerUserName);
		authStructure.setLdapManagerPwd(ldapManagerPwd);
		authStructure.setUserDnPattern(LdapAuthConstant.LDAP_USER_DN_PATTERN);
		authStructure.setUserSearchBase(LdapAuthConstant.LDAP_USER_SEARCH);
		authStructure.setGroupSearchBase(LdapAuthConstant.LDAP_GROUP_SEARCH);
		
		return authStructure;
	}

}
