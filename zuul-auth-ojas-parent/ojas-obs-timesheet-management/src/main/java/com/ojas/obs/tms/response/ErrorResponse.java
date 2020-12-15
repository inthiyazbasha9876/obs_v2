package com.ojas.obs.tms.response;

public class ErrorResponse {
	private String message;
	private String statusMessage;
	private String statusCode;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	@Override
	public String toString() {
		return "ErrorResponse [message=" + message + ", statusMessage=" + statusMessage + ", statusCode=" + statusCode
				+ "]";
	}
	
	
}
