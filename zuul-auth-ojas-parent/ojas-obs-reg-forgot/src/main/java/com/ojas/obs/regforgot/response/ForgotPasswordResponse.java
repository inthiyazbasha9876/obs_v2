package com.ojas.obs.regforgot.response;

import com.ojas.obs.regforgot.model.ForgotPassword;

public class ForgotPasswordResponse {
	private String statusCode;
	private String message;
	private String statusMessage;
	private ForgotPassword forgotPassword;
	
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ForgotPassword getForgotPassword() {
		return forgotPassword;
	}
	public void setForgotPassword(ForgotPassword forgotPassword) {
		this.forgotPassword = forgotPassword;
	}
	@Override
	public String toString() {
		return "ForgotPasswordResponse [statusCode=" + statusCode + ", message=" + message + ", forgotPassword="
				+ forgotPassword + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((forgotPassword == null) ? 0 : forgotPassword.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForgotPasswordResponse other = (ForgotPasswordResponse) obj;
		if (forgotPassword == null) {
			if (other.forgotPassword != null)
				return false;
		} else if (!forgotPassword.equals(other.forgotPassword))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode))
			return false;
		return true;
	}

}
