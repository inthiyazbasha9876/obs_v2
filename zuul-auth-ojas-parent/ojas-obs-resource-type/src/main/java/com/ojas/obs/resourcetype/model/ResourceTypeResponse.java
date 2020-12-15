package com.ojas.obs.resourcetype.model;

import java.util.List;

public class ResourceTypeResponse {

	private List<ResourceType> employmentDetailsList;

	private String statusCode;

	private String statusMessage;

	public List<ResourceType> getEmploymentDetailsList() {
		return employmentDetailsList;
	}

	public void setEmploymentDetailsList(List<ResourceType> employmentDetailsList) {
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
