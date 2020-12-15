package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.SezLocation;



public class SezlocationResponse
{

	private String message;
	private String statusCode;
	private List<SezLocation> sezlocationList;
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
	public List<SezLocation> getSezlocationList() {
		return sezlocationList;
	}
	public void setSezlocationList(List<SezLocation> sezlocationList) {
		this.sezlocationList = sezlocationList;
	}
	@Override
	public String toString() {
		return "SezlocationResponse [message=" + message + ", statusCode=" + statusCode + ", sezlocationList="
				+ sezlocationList + "]";
	}
	
	
	
}
