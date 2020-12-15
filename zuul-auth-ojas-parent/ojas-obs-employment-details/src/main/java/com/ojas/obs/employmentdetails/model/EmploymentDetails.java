package com.ojas.obs.employmentdetails.model;

import java.sql.Timestamp;

public class EmploymentDetails {

	private Integer id;
	private String employeeId;
	private String joiningDate;
	private String resourceType;
	private Boolean bondStatus;
	private String bondTenure;
	
	private String resourceCat;
	private String confirmationDate;
	private String costCenterId;
	private String buId;
	private String sbuId;
	private Boolean flag;
	private String createdBy;
	private String updatedBy;
	private Timestamp createdDate;
	private Timestamp updatedDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public Boolean isBondStatus() {
		return bondStatus;
	}
	public void setBondStatus(Boolean bondStatus) {
		this.bondStatus = bondStatus;
	}
	public String getBondTenure() {
		return bondTenure;
	}
	public void setBondTenure(String bondTenure) {
		this.bondTenure = bondTenure;
	}
	public String getResourceCat() {
		return resourceCat;
	}
	public void setResourceCat(String resourceCat) {
		this.resourceCat = resourceCat;
	}
	public String getConfirmationDate() {
		return confirmationDate;
	}
	public void setConfirmationDate(String confirmationDate) {
		this.confirmationDate = confirmationDate;
	}
	public String getCostCenterId() {
		return costCenterId;
	}
	public void setCostCenterId(String costCenterId) {
		this.costCenterId = costCenterId;
	}
	public String getBuId() {
		return buId;
	}
	public void setBuId(String buId) {
		this.buId = buId;
	}
	public String getSbuId() {
		return sbuId;
	}
	public void setSbuId(String sbuId) {
		this.sbuId = sbuId;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "EmploymentDetails [id=" + id + ", employeeId=" + employeeId + ", joiningDate=" + joiningDate
				+ ", resourceType=" + resourceType + ", bondStatus=" + bondStatus + ", bondTenure=" + bondTenure
				+ ", resourceCat=" + resourceCat + ", confirmationDate=" + confirmationDate + ", costCenterId="
				+ costCenterId + ", buId=" + buId + ", sbuId=" + sbuId + ", flag=" + flag + ", createdBy=" + createdBy
				+ ", updatedBy=" + updatedBy + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + "]";
	}

}