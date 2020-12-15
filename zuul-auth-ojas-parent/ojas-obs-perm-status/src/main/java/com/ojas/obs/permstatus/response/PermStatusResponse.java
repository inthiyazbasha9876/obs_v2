package com.ojas.obs.permstatus.response;

import java.util.List;

import com.ojas.obs.permstatus.model.PermStatus;

public class PermStatusResponse {

	private String message;
	private String statusCode;
	private List<PermStatus>permStatusList;
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
	public List<PermStatus> getPermStatusList() {
		return permStatusList;
	}
	public void setPermStatusList(List<PermStatus> permStatusList) {
		this.permStatusList = permStatusList;
	}
	@Override
	public String toString() {
		return "PermStatusResponse [message=" + message + ", statusCode=" + statusCode + ", permStatusList="
				+ permStatusList + "]";
	}
	
}
