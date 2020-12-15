package com.ojas.obs.resetpassword.model;

import java.sql.Timestamp;

public class ResetPassword {
	private Integer id;
	private String employeeId;
	private String createdBy;
	private String updatedBy;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	private String curruntPassword;
	private String newPassword;
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getCurruntPassword() {
		return curruntPassword;
	}
	public void setCurruntPassword(String curruntPassword) {
		this.curruntPassword = curruntPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}
	@Override
	public String toString() {
		return "Password [id=" + id + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdOn="
				+ createdOn + ", updatedOn=" + updatedOn + ", curruntPassword=" + curruntPassword + ", newPassword="
				+ newPassword + "]";
	}
	
	
}
