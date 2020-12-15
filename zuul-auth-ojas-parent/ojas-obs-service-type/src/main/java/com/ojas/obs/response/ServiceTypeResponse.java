package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.ServiceType;

public class ServiceTypeResponse {
	private String message;
	private String statusCode;
	private List<ServiceType> servicetypeList;

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

	public List<ServiceType> getServicetypeList() {
		return servicetypeList;
	}

	public void setServicetypeList(List<ServiceType> servicetypeList) {
		this.servicetypeList = servicetypeList;
	}

	@Override
	public String toString() {
		return "ServiceTypeResponse [message=" + message + ", statusCode=" + statusCode + ", servicetypeList="
				+ servicetypeList + "]";
	}
}
