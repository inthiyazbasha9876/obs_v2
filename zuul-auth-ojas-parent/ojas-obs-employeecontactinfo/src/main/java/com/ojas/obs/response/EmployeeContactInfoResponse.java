package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.EmployeeContactInfo;

public class EmployeeContactInfoResponse {

	private String statusCode;
	private String message;
	private List<EmployeeContactInfo> empContacts;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<EmployeeContactInfo> getEmpContacts() {
		return empContacts;
	}

	public void setEmpContacts(List<EmployeeContactInfo> empContacts) {
		this.empContacts = empContacts;
	}

	@Override
	public String toString() {
		return "EmployeeContactInfoResponse [statusCode=" + statusCode + ", message=" + message + ", empContacts="
				+ empContacts + "]";
	}

}
