package com.gdky.restfull.security;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthProvider implements AuthenticationProvider {

	private static final Logger log = LoggerFactory
			.getLogger(AuthProvider.class);

	@Resource
	private CustomUserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		String username = auth.getName();
		String password = auth.getCredentials().toString();
		log.debug("username:{} ,\tpassword:{}", username, password);

		UserDetails user = userDetailsService.loadUserByUsername(username);
		log.debug(ReflectionToStringBuilder.toString(user));
		if (user != null) {
			Authentication token = new UsernamePasswordAuthenticationToken(
					username, password, user.getAuthorities());
			return token;
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return false;
	}

}
