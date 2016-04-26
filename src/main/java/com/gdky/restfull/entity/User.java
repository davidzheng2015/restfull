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
	private Date signUpDate;
	private Date lastActTime;
	private String ip;
	private String yxbz;
	private Integer deptId;
	private String cname;
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
	public Date getSignUpDate() {
		return signUpDate;
	}
	public void setSignUpDate(Date signUpDate) {
		this.signUpDate = signUpDate;
	}
	public Date getLastActTime() {
		return lastActTime;
	}
	public void setLastActTime(Date lastActTime) {
		this.lastActTime = lastActTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getYxbz() {
		return yxbz;
	}
	public void setYxbz(String yxbz) {
		this.yxbz = yxbz;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
}
