package com.ojas.obs.model;

public class BasicInfoModel {

	
	private String reportingManager;
	private String employeeId;
	public String getReportingManager() {
		return reportingManager;
	}
	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	@Override
	public String toString() {
		return "BasicInfoModel [reportingManager=" + reportingManager + ", employeeId=" + employeeId + "]";
	}
	
	 
	
}
