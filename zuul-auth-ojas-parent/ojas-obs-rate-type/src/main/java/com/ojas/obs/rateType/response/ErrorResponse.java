package com.ojas.obs.rateType.response;

public class ErrorResponse {
	private String code;
	private String statusMessage;
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	@Override
	public String toString() {
		return "ErrorResponse [code=" + code + ", statusMessage=" + statusMessage + ", message=" + message + "]";
	}

}
