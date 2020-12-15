package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.ServiceCategory;


public class ServicecategoryResponse {

	private String message;
	private String statusCode;
	private List<ServiceCategory> servicecategoryList;
	
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
	public List<ServiceCategory> getServicecategoryList() {
		return servicecategoryList;
	}
	public void setServicecategoryList(List<ServiceCategory> servicecategoryList) {
		this.servicecategoryList = servicecategoryList;
	}
	@Override
	public String toString() {
		return "CmsResponse [message=" + message + ", statusCode=" + statusCode + ", servicecategoryList="
				+ servicecategoryList + "]";
	}
	
	
}
