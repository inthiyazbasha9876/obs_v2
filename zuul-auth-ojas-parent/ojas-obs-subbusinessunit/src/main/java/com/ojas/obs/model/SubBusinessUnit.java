package com.ojas.obs.model;

/**
 * 
 * @author asuneel
 *
 */
public class SubBusinessUnit {

	private Integer id;
	private String name;
	private String businessUnitId;
	private String sbuHead;
	private boolean status;
	public SubBusinessUnit() {
		super();
	}
	public SubBusinessUnit(Integer id, String name, String businessUnitId, String sbuHead, boolean status) {
		super();
		this.id = id;
		this.name = name;
		this.businessUnitId = businessUnitId;
		this.sbuHead = sbuHead;
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBusinessUnitId() {
		return businessUnitId;
	}
	public void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}
	public String getSbuHead() {
		return sbuHead;
	}
	public void setSbuHead(String sbuHead) {
		this.sbuHead = sbuHead;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "SubBusinessUnit [id=" + id + ", name=" + name + ", businessUnitId=" + businessUnitId + ", sbuHead="
				+ sbuHead + ", status=" + status + "]";
	}
	
	

}
