package com.obs.businessunit.model;

public class BusinessUnit {
	private Integer id;
	private String businessUnitName;
	private int costCenterId;
	private String buHead;
	private boolean status;
	public BusinessUnit() {
		super();
	}
	public BusinessUnit(Integer id, String businessUnitName, int costCenterId, String buHead, boolean status) {
		super();
		this.id = id;
		this.businessUnitName = businessUnitName;
		this.costCenterId = costCenterId;
		this.buHead = buHead;
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBusinessUnitName() {
		return businessUnitName;
	}
	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}
	public int getCostCenterId() {
		return costCenterId;
	}
	public void setCostCenterId(int costCenterId) {
		this.costCenterId = costCenterId;
	}
	public String getBuHead() {
		return buHead;
	}
	public void setBuHead(String buHead) {
		this.buHead = buHead;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "BusinessUnit [id=" + id + ", businessUnitName=" + businessUnitName + ", costCenterId=" + costCenterId
				+ ", buHead=" + buHead + ", status=" + status + "]";
	}
	
	
	
}
