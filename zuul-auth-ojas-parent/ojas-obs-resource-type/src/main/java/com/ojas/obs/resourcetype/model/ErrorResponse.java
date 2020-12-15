package com.ojas.obs.resourcetype.model;

public class ErrorResponse {

	private String statusCode;

	private String message;
private String statusMessage;

	public ErrorResponse() {
	}

	

	public ErrorResponse(String statusCode, String message, String statusMessage) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.statusMessage = statusMessage;
	}



	public String getStatusCode() {
		return statusCode;
	}



	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}



	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
