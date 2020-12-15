package com.ojas.obs.model;

public class CostCenter {

	private int costCenterCode;
	private Integer id;
	private Boolean status;

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
 
	public int getCostCenterCode() {
		return costCenterCode;
	}
	public void setCostCenterCode(int costCenterCode) {
		this.costCenterCode = costCenterCode;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "CostCenter [costCenterCode=" + costCenterCode + ", id=" + id + "]";
	}
}