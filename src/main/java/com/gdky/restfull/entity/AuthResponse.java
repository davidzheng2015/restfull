package com.gdky.restfull.entity;

import java.io.Serializable;
import java.util.List;

import org.hashids.Hashids;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.gdky.restfull.configuration.Constants;

public class AuthResponse implements Serializable {

	private static final long serialVersionUID = 2652559529529474758L;
	private String token;
	private String tokenhash;
	private String jgId;
	private String permission;

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getJgId() {
		return jgId;
	}

	public void setJgId(Integer jgId) {
		this.jgId = null;
		if (jgId != null) {
			Hashids hashids = new Hashids(Constants.HASHID_SALT,
					Constants.HASHID_LEN);
			this.jgId = hashids.encode(jgId.longValue());
		}
	}

	public String getTokenhash() {
		return tokenhash;
	}

	public void setTokenhash(String token) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		// MD5不加盐hash
		String last = token.substring(token.length() - 1);
		String pass = encoder.encodePassword(last + token, null);
		this.tokenhash = pass;
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
