package com.nilangpatel.springauth.repository.impl;

import java.util.List;

import javax.naming.Name;
import javax.naming.directory.DirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Repository;

import com.nilangpatel.springauth.mapper.LdapRoleMapper;
import com.nilangpatel.springauth.model.LdapAuthUser;
import com.nilangpatel.springauth.model.LdapGranntedAuthority;
import com.nilangpatel.springauth.repository.LdapAuthRepositoryCustom;

@Repository
public class LdapAuthRepositoryCustomImpl implements LdapAuthRepositoryCustom {

	private Logger logger =  LoggerFactory.getLogger(LdapAuthRepositoryCustomImpl.class);
	
	@Autowired
	private LdapTemplate ldapTemplate;

	@Override
	public LdapAuthUser findByUserName(String userName) {
		return ldapTemplate.findOne(LdapQueryBuilder.query().where("uid").is(userName), LdapAuthUser.class);
	}

	@Override
	public boolean authenticateLdapUserWithLdapQuery(String userName, String password) {
		
		try {
		 ldapTemplate.authenticate(LdapQueryBuilder.query().where("uid").is(userName), password);
		 return true;
		}catch(Exception e) {
			logger.error("Exception occuired while authenticating user with user name "+userName,e.getMessage(),e);
		}
		return false;
	}
	
	/**
	 * This method will return roles of given user.
	 */
	@Override
	public List<LdapGranntedAuthority> getUserAuthorities(String userName) {
		AndFilter groupFilter = new AndFilter();
		groupFilter.and(new EqualsFilter("objectclass","groupOfNames"));
		groupFilter.and(new EqualsFilter("member","uid="+userName+",ou=users,o=packtPublisher"));
		List<LdapGranntedAuthority> userRoleLst = ldapTemplate.search(LdapQueryBuilder.query().filter(groupFilter),new LdapRoleMapper());
		return userRoleLst;
	}
	@Override
	public boolean authenticateLdapUserWithContext(String userName, String password) {
		DirContext ctx = null;
		try {
			String userDn = getDnForUser(userName);
			ctx = ldapTemplate.getContextSource().getContext(userDn, password);
			return true;
		} catch (Exception e) {
			// If exception occurred while creating Context, means - authentication did not succeed
			logger.error("Authentication failed ", e.getMessage(),e);
			return false;
		} finally {
			// DirContext must be closed here.
			LdapUtils.closeContext(ctx);
		}
	}

	@Override
	public List<LdapAuthUser> findByMatchingUserName(String userName) {
		return ldapTemplate.find(LdapQueryBuilder.query().where("uid").like(userName), LdapAuthUser.class);
	}

	@Override
	public void create(LdapAuthUser ldapAuthUser) {
		ldapAuthUser.setIsNew(true);
		ldapTemplate.create(ldapAuthUser);
	}
	@Override
	public void createByBindOperation(LdapAuthUser ldapAuthUser) {
		
		DirContextOperations ctx = new DirContextAdapter();
		ctx.setAttributeValues("objectclass", new String[] {"top", "person", "organizationalPerson","inetOrgPerson"});
		ctx.setAttributeValue("cn", ldapAuthUser.getFirstName());
		ctx.setAttributeValue("sn", ldapAuthUser.getSurName());
		ctx.setAttributeValue("uid", ldapAuthUser.getUserName());
		ctx.setAttributeValue("userPassword", ldapAuthUser.getPassword());
		
		Name dn = LdapNameBuilder.newInstance()
			      .add("ou=users")
			      .add("uid=bpatel")
			      .build();
		
		ctx.setDn(dn);
		ldapTemplate.bind(ctx);
		
	}

	@Override
	public LdapAuthUser findByUid(String uid) {
		return ldapTemplate.findOne(LdapQueryBuilder.query().where("uid").is(uid), LdapAuthUser.class);
	}

	@Override
	public void updateWithTemplate(LdapAuthUser ldapAuthUser) {
		ldapTemplate.update(ldapAuthUser);
	}

	@Override
	public void deleteFromTemplate(LdapAuthUser ldapAuthUser) {
		ldapTemplate.delete(ldapAuthUser);
	}
	
	@Override
	public void deleteFromTemplateWithUnbind(String userName) {
		Name dn = LdapNameBuilder.newInstance()
			      .add("ou=users")
			      .add("uid="+userName)
			      .build();
		ldapTemplate.unbind(dn);
	}

	@Override
	public List<LdapAuthUser> findAllWithTemplate() {
		return ldapTemplate.findAll(LdapAuthUser.class);
	}

	@Override
	public List<LdapAuthUser> findBySurname(String surName) {
		return ldapTemplate.find(LdapQueryBuilder.query().where("sn").is(surName), LdapAuthUser.class);
	}

	
	private String getDnForUser(String uid) {
		  List<String> result = ldapTemplate.search(
		      LdapQueryBuilder.query().where("uid").is(uid),
		      new AbstractContextMapper<String>() {
		         protected String doMapFromContext(DirContextOperations ctx) {
		        	 logger.info("######## NameInNamespace -->"+ctx.getNameInNamespace());
		            return ctx.getNameInNamespace();
		         }
		      });

		  if(result.size() != 1) {
		    throw new RuntimeException("User not found or not unique");
		  }

		  return result.get(0);
		}
}
