package com.ojas.obs.sez.response;

import java.util.List;

import com.ojas.obs.sez.model.Sez;

public class SezResponse {
	private String message;
	private String statusCode;
	private List<Sez> sezlist;

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

	

	public List<Sez> getSezlist() {
		return sezlist;
	}

	public void setSezlist(List<Sez> sezlist) {
		this.sezlist = sezlist;
	}

	public SezResponse() {
		super();
	}

	public SezResponse(String message, String statusCode, List<Sez> sezs) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.sezlist = sezs;
	}

	@Override
	public String toString() {
		return "SezResponse [message=" + message + ", statusCode=" + statusCode + ", sezlist=" + sezlist + "]";
	}

	

}
