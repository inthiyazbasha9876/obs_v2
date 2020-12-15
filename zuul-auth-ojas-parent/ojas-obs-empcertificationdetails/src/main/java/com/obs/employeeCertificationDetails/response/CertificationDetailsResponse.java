package com.obs.employeeCertificationDetails.response;

import java.util.List;

import com.obs.employeeCertificationDetails.model.CertificationDetails;

public class CertificationDetailsResponse {

private List<CertificationDetails> getCertificationDetailsModelist;
private String message;
private String statusCode;
private List<CertificationDetails> certificationDetailsList;
public List<CertificationDetails> getGetCertificationDetailsModelist() {
	return getCertificationDetailsModelist;
}
public void setGetCertificationDetailsModelist(List<CertificationDetails> getCertificationDetailsModelist) {
	this.getCertificationDetailsModelist = getCertificationDetailsModelist;
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
public List<CertificationDetails> getCertificationDetailsList() {
	return certificationDetailsList;
}
public void setCertificationDetailsList(List<CertificationDetails> certificationDetailsList) {
	this.certificationDetailsList = certificationDetailsList;
}
@Override
public String toString() {
	return "CertificationDetailsResponse [getCertificationDetailsModelist=" + getCertificationDetailsModelist
			+ ", message=" + message + ", statusCode=" + statusCode + ", certificationDetailsList="
			+ certificationDetailsList + "]";
}

}
