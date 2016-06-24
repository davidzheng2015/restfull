package com.gdky.restfull.entity;

import java.io.Serializable;
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4410761482155757494L;
	private Integer id;
	private String username;
	private String password;
	private String passwordHint;
	private String names;
	private Integer accountEnabled;
	private Integer accountExpired;
	private Integer accountLocked;
	private Integer credentialsExpired;
	private Integer jgId;
	private String uname;
	private Dat createTime;
	
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public Integer getJgId() {
		return jgId;
	}
	public void setJgId(Integer jgId) {
		this.jgId = jgId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordHint() {
		return passwordHint;
	}
	public void setPasswordHint(String passwordHint) {
		this.passwordHint = passwordHint;
	}
	public Integer getAccountEnabled() {
		return accountEnabled;
	}
	public void setAccountEnabled(Integer accountEnabled) {
		this.accountEnabled = accountEnabled;
	}
	public Integer getAccountExpired() {
		return accountExpired;
	}
	public void setAccountExpired(Integer accountExpired) {
		this.accountExpired = accountExpired;
	}
	public Integer getAccountLocked() {
		return accountLocked;
	}
	public void setAccountLocked(Integer accountLocked) {
		this.accountLocked = accountLocked;
	}
	public Integer getCredentialsExpired() {
		return credentialsExpired;
	}
	public void setCredentialsExpired(Integer credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}
	public String getNames() {
		return names;
	}
	public void setNames(String name) {
		this.names = name;
	}
	
	public User(User u){
		this.id = u.id;
		this.username = u.username;
		this.names = u.names;
		this.password = u.password;
		this.passwordHint = u.passwordHint;
		this.accountEnabled = u.accountEnabled;
		this.accountExpired =  u.accountExpired;
		this.accountLocked = u.accountLocked;
		this.credentialsExpired = u.credentialsExpired;
		this.jgId=u.jgId;
	}
	public User(){
		
	}
	
}
