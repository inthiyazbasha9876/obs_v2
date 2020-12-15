package com.ojas.obs.resetpassword.response;

import com.ojas.obs.resetpassword.model.ResetPassword;

public class ResetPasswordResponse {
	private String statusCode;
	private String message;
	private ResetPassword pwd;
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

	public ResetPassword getPwd() {
		return pwd;
	}

	public void setPwd(ResetPassword pwd) {
		this.pwd = pwd;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@Override
	public String toString() {
		return "ResetPasswordResponse [statusCode=" + statusCode + ", pwd=" + pwd + ", statusMessage=" + statusMessage + "]";
	}

	
}
