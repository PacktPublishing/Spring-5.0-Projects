package com.nilangpatel.springauth.authprovider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.nilangpatel.springauth.model.LdapGranntedAuthority;
import com.nilangpatel.springauth.service.LdapAuthService;

@Component
public class CustomLdapAuthProvider implements AuthenticationProvider{

	@Autowired
	LdapAuthService ldapAuthService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();

		boolean isAuthenticate =  ldapAuthService.authenticateLdapUserWithContext(userName, password);

		if(isAuthenticate == true) {
			List<LdapGranntedAuthority> userRoles =  ldapAuthService.getUserAuthorities(userName);
			return new UsernamePasswordAuthenticationToken(
					userName, password, userRoles);
		}else {
			return null;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(
				UsernamePasswordAuthenticationToken.class);
	}
}
