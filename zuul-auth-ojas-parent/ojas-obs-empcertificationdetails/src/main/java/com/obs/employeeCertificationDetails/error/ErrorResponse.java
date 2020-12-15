package com.obs.employeeCertificationDetails.error;

public class ErrorResponse {
  private String code;
  private String message;
  private String statusMessage;
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
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
@Override
public String toString() {
	return "ErrorResponse [code=" + code + ", message=" + message + ", statusMessage=" + statusMessage + "]";
}

   
}
