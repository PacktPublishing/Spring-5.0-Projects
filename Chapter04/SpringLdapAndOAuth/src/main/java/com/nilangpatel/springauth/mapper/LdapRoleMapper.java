package com.nilangpatel.springauth.mapper;

import javax.naming.NamingException;

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.security.ldap.userdetails.LdapAuthority;

import com.nilangpatel.springauth.model.LdapGranntedAuthority;

public class LdapRoleMapper  implements ContextMapper<LdapGranntedAuthority>{

	@Override
	public LdapGranntedAuthority mapFromContext(Object ctx) throws NamingException {
		DirContextAdapter adapter = (DirContextAdapter) ctx;
		String role = adapter.getStringAttribute("cn");
		LdapGranntedAuthority ldapGranntedAuthority = new LdapGranntedAuthority();
		ldapGranntedAuthority.setAuthority(role);
		return ldapGranntedAuthority;
	}
}
