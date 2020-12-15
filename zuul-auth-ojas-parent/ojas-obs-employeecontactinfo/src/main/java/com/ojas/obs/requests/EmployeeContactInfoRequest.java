package com.ojas.obs.requests;

import java.util.List;

import com.ojas.obs.model.EmployeeContactInfo;

public class EmployeeContactInfoRequest {

	private List<EmployeeContactInfo> empInfo;
	private String transactionType;

	public List<EmployeeContactInfo> getEmpInfo() {
		return empInfo;
	}

	public void setEmpInfo(List<EmployeeContactInfo> empInfo) {
		this.empInfo = empInfo;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "EmployeeContactInfoRequest [empInfo=" + empInfo + ", transactionType=" + transactionType + "]";
	}

}
