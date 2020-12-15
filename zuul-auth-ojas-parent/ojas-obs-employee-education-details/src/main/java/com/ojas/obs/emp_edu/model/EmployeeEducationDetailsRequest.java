package com.ojas.obs.emp_edu.model;

import java.util.List;

public class EmployeeEducationDetailsRequest {

	private List<EmployeeEducationDetails> employeeEducationDetailsList;
	private String transactionType;
	public List<EmployeeEducationDetails> getEmployeeEducationDetailsList() {
		return employeeEducationDetailsList;
	}
	public void setEmployeeEducationDetailsList(List<EmployeeEducationDetails> employeeEducationDetailsList) {
		this.employeeEducationDetailsList = employeeEducationDetailsList;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "EmployeeEducationDetailsRequest [employeeEducationDetailsList=" + employeeEducationDetailsList
				+ ", transactionType=" + transactionType + "]";
	}
	
}
