package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.GstLocation;



public class GstlocationResponse
{

	private String message;
	private String statusCode;
	private List<GstLocation> gstlocationList;
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
	public List<GstLocation> getGstlocationList() {
		return gstlocationList;
	}
	public void setGstlocationList(List<GstLocation> gstlocationList) {
		this.gstlocationList = gstlocationList;
	}
	
	
	@Override
	public String toString() {
		return "GstlocationResponse [message=" + message + ", statusCode=" + statusCode + ", gstlocationList="
				+ gstlocationList + "]";
	}
	
	
	
	
}
