package com.ojas.obs.utility;


/**
 * 
 * @author nsrikanth
 *
 */
public class ErrorResponse {
	
	private String StatusMessage;
	private String StatusCode;
	private String message;
	@Override
	public String toString() {
		return "ErrorResponse [StatusMessage=" + StatusMessage + ", StatusCode=" + StatusCode + ", message=" + message
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((StatusCode == null) ? 0 : StatusCode.hashCode());
		result = prime * result + ((StatusMessage == null) ? 0 : StatusMessage.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		ErrorResponse other = (ErrorResponse) obj;
		if (StatusCode == null) {
			if (other.StatusCode != null)
				return false;
		} else if (!StatusCode.equals(other.StatusCode))
			return false;
		if (StatusMessage == null) {
			if (other.StatusMessage != null)
				return false;
		} else if (!StatusMessage.equals(other.StatusMessage))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatusMessage() {
		return StatusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		StatusMessage = statusMessage;
	}
	public String getStatusCode() {
		return StatusCode;
	}
	public void setStatusCode(String statusCode) {
		StatusCode = statusCode;
	}
	
	

}
