package com.ojas.obs.emp_edu.model;

import java.util.List;

public class EmployeeEducationResponse {
	private List<EmployeeEducationDetails> employeeEducationDetailsList;
	private String message;
	private String statusCode;
	public List<EmployeeEducationDetails> getEmployeeEducationDetailsList() {
		return employeeEducationDetailsList;
	}
	public void setEmployeeEducationDetailsList(List<EmployeeEducationDetails> employeeEducationDetailsList) {
		this.employeeEducationDetailsList = employeeEducationDetailsList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	@Override
	public String toString() {
		return "EmployeeEducationResponse [employeeEducationDetailsList=" + employeeEducationDetailsList + ", message="
				+ message + ", statusCode=" + statusCode + "]";
	}
	
	
}
