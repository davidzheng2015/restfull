package com.gdky.restfull.framework.entity;

import java.io.Serializable;

public class Menu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7249381922332307459L;
	private Integer id;
	private Integer pid;
	private String name;
	private String href;
	private Integer orderNo;
	private String path;
	private String visble;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPId() {
		return pid;
	}
	public void setPid(Integer pId) {
		this.pid = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getVisble() {
		return visble;
	}
	public void setVisble(String visble) {
		this.visble = visble;
	}
	
	
}
