package com.gdky.restfull.entity;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3365405747302906236L;
	private Integer id;
	private String userName;
	private String password;
	private Date createTime;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
