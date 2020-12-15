package com.ojas.obs.employmentdetails.model;

import java.util.List;

public class EmploymentDetailsResponse {

	private List<EmploymentDetails> employmentDetailsList;

	private String statusCode;

	private String statusMessage;

	public List<EmploymentDetails> getEmploymentDetailsList() {
		return employmentDetailsList;
	}

	public void setEmploymentDetailsList(List<EmploymentDetails> employmentDetailsList) {
		this.employmentDetailsList = employmentDetailsList;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

}
