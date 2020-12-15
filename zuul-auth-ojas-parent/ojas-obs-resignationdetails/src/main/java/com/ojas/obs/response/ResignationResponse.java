package com.ojas.obs.response;



import java.util.List;

import com.ojas.obs.model.Resignation;

public class ResignationResponse {

	private List<Resignation> resignationList;
	private String statusCode;
	private String message;
	public List<Resignation> getResignationList() {
		return resignationList;
	}
	public void setResignationList(List<Resignation> resignationList) {
		this.resignationList = resignationList;
	}
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
	@Override
	public String toString() {
		return "ResignationResponse [resignationList=" + resignationList + ", statusCode=" + statusCode + ", message="
				+ message + "]";
	}
	
	
	
}
