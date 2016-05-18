package com.gdky.restfull.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class AuthResponse implements Serializable{
	
	private static final long serialVersionUID = 2652559529529474758L;
	private String token;
	private List<GrantedAuthority> roles;
	private String names;

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public List<GrantedAuthority> getRoles() {
		return roles;
	}

	public void setRoles(List<GrantedAuthority> roles) {
		this.roles = roles;
	}

	public AuthResponse() {
		super();
	}

	public AuthResponse(String token) {
		this.setToken(token);
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
