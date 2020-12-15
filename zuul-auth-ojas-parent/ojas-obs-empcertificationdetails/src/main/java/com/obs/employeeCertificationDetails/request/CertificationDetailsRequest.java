package com.obs.employeeCertificationDetails.request;

import java.util.List;

import com.obs.employeeCertificationDetails.model.CertificationDetails;

public class CertificationDetailsRequest {

private List<CertificationDetails> certificationDetailsModel;
private String transactionType;
public List<CertificationDetails> getCertificationDetailsModel() {
	return certificationDetailsModel;
}
public void setCertificationDetailsModel(List<CertificationDetails> certificationDetailsModel) {
	this.certificationDetailsModel = certificationDetailsModel;
}
public String getTransactionType() {
	return transactionType;
}
public void setTransactionType(String transactionType) {
	this.transactionType = transactionType;
}
@Override
public String toString() {
	return "CertificationDetailsRequest [certificationDetailsModel=" + certificationDetailsModel + ", transactionType="
			+ transactionType + "]";
}

}


