package com.ojas.obs.c2hstatus.response;

import java.util.List;

import com.ojas.obs.c2hstatus.model.C2HStatus;


public class C2HStatusResponse {
	private String message;
	private String statusCode;
	private List<C2HStatus> c2hstatuslist;
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
	public List<C2HStatus> getC2hstatuslist() {
		return c2hstatuslist;
	}
	public void setC2hstatuslist(List<C2HStatus> c2hstatuslist) {
		this.c2hstatuslist = c2hstatuslist;
	}
	@Override
	public String toString() {
		return "C2HStatusResponse [message=" + message + ", statusCode=" + statusCode + ", c2hstatuslist="
				+ c2hstatuslist + "]";
	}
	
}
