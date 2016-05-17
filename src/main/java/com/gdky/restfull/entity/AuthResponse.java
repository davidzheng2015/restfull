package com.gdky.restfull.entity;

import java.io.Serializable;

public class AuthResponse implements Serializable{
	
	private static final long serialVersionUID = 2652559529529474758L;
	private String token;

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
