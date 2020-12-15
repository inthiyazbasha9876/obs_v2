package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.States;

public class StatesResponse{
	private List<States> statesList;
	private String message;
	private String  statusCode;
	public List<States> getStatesList() {
		return statesList;
	}
	public void setStatesList(List<States> statesList) {
		this.statesList = statesList;
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
		return "StatesResponse [statesList=" + statesList + ", message=" + message + ", statusCode=" + statusCode + "]";
	}
	
	
}
