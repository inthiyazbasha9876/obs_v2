package com.ojas.obs.errorMessage;
/**
 * 
 * @author pnaveen
 *
 */
public class ErrorResponse {
	
	private String message;
	private String statusCode;
	private String statusMessage;

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

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}


}

