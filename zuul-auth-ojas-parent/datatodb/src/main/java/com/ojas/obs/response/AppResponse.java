package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.App;

public class AppResponse {

	private List<App> appList;
	private String message;
	private String statusCode;
	public List<App> getAppList() {
		return appList;
	}
	public void setAppList(List<App> appList) {
		this.appList = appList;
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
		return "AppResponse [appList=" + appList + ", message=" + message + ", statusCode=" + statusCode + "]";
	}
	
	
}
