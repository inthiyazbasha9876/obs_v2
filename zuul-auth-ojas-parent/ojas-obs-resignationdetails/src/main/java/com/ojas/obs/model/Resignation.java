package com.ojas.obs.model;

import java.sql.Timestamp;

public class Resignation {

	private Integer id;
	private String employeeId;
	private String remarks;
	private String resignationType;
	private String leavingReason;
	private String emailId1;
	private String emailId2;
	private String emailId3;
	private String emailId4;
	private Boolean employeeIsDeceased;
	private String dateOfDemise;
	private String leavingDate;
	private String resignationSubmittedOn;
	private String finalSettlementDate;
	private String state;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	@Override
	public String toString() {
		return "Resignation [id=" + id + ", employeeId=" + employeeId + ", remarks=" + remarks + ", resignationType="
				+ resignationType + ", leavingReason=" + leavingReason + ", emailId1=" + emailId1 + ", emailId2="
				+ emailId2 + ", emailId3=" + emailId3 + ", emailId4=" + emailId4 + ", employeeIsDeceased="
				+ employeeIsDeceased + ", dateOfDemise=" + dateOfDemise + ", leavingDate=" + leavingDate
				+ ", resignationSubmittedOn=" + resignationSubmittedOn + ", finalSettlementDate=" + finalSettlementDate
				+ ", state=" + state + "]";
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getResignationType() {
		return resignationType;
	}
	public void setResignationType(String resignationType) {
		this.resignationType = resignationType;
	}
	public String getLeavingReason() {
		return leavingReason;
	}
	public void setLeavingReason(String leavingReason) {
		this.leavingReason = leavingReason;
	}
	public String getEmailId1() {
		return emailId1;
	}
	public void setEmailId1(String emailId1) {
		this.emailId1 = emailId1;
	}
	public String getEmailId2() {
		return emailId2;
	}
	public void setEmailId2(String emailId2) {
		this.emailId2 = emailId2;
	}
	public String getEmailId3() {
		return emailId3;
	}
	public void setEmailId3(String emailId3) {
		this.emailId3 = emailId3;
	}
	public String getEmailId4() {
		return emailId4;
	}
	public void setEmailId4(String emailId4) {
		this.emailId4 = emailId4;
	}
	public Boolean getEmployeeIsDeceased() {
		return employeeIsDeceased;
	}
	public void setEmployeeIsDeceased(Boolean employeeIsDeceased) {
		this.employeeIsDeceased = employeeIsDeceased;
	}
	public String getDateOfDemise() {
		return dateOfDemise;
	}
	public void setDateOfDemise(String dateOfDemise) {
		this.dateOfDemise = dateOfDemise;
	}
	public String getLeavingDate() {
		return leavingDate;
	}
	public void setLeavingDate(String leavingDate) {
		this.leavingDate = leavingDate;
	}
	public String getResignationSubmittedOn() {
		return resignationSubmittedOn;
	}
	public void setResignationSubmittedOn(String resignationSubmittedOn) {
		this.resignationSubmittedOn = resignationSubmittedOn;
	}
	public String getFinalSettlementDate() {
		return finalSettlementDate;
	}
	public void setFinalSettlementDate(String finalSettlementDate) {
		this.finalSettlementDate = finalSettlementDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	

}
