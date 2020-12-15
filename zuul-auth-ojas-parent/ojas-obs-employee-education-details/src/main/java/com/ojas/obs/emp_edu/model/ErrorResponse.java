package com.ojas.obs.emp_edu.model;

public class ErrorResponse {
	private String statuscode;
	private String StatusMessage;
	private String message;
	public String getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}
	public String getStatusMessage() {
		return StatusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		StatusMessage = statusMessage;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ErrorResponse [statuscode=" + statuscode + ", StatusMessage=" + StatusMessage + ", message=" + message
				+ "]";
	}
	

}
