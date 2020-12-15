package com.ojas.obs.mailSend;

import java.sql.Timestamp;



public class ProjectDetails {

	
	private Timestamp startDate;
	private Timestamp endDate;
	
	private String employeeId;

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String toString() {
		return "ProjectDetails [startDate=" + startDate + ", endDate=" + endDate + ", employeeId=" + employeeId + "]";
	}
	
	
}
