package gov.gdgs.zs.entity;

import java.io.Serializable;


public class Lrb implements Serializable{
	
	private static final long serialVersionUID = 3365405747302906236L;	

	private String id;
	private Double zgywsr;
	private Double zgywsr1;
	private Integer nd;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Double getZgywsr() {
		return zgywsr;
	}
	public void setZgywsr(Double zgywsr) {
		this.zgywsr = zgywsr;
	}
	public Double getZgywsr1() {
		return zgywsr1;
	}
	public void setZgywsr1(Double zgywsr1) {
		this.zgywsr1 = zgywsr1;
	}
	public Integer getNd() {
		return nd;
	}
	public void setNd(Integer nd) {
		this.nd = nd;
	}

	
	
}
