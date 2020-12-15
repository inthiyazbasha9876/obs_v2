package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.Genders;

public class GenderResponse {
	private List<Genders> genderList;
	private String message;
	private String statusCode;
	public List<Genders> getGenderList() {
		return genderList;
	}
	public void setGenderList(List<Genders> genderList) {
		this.genderList = genderList;
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
		return "GenderResponse [genderList=" + genderList + ", message=" + message + ", statusCode=" + statusCode + "]";
	}
	
	
}
