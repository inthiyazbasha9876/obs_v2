package com.ojas.obs.billingtype.response;

import java.util.List;

import com.ojas.obs.billingtype.model.BillingType;

public class BillingTypeResponse {
	private String message;
	private String statusCode;
	private List<BillingType> billingList;
	
	
	public List<BillingType> getBillingList() {
		return billingList;
	}
	public void setBillingList(List<BillingType> billingList) {
		this.billingList = billingList;
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

	@Override
	public String toString() {
		return "BillingTypeResponse [message=" + message + ", statusCode=" + statusCode + ", billingList=" + billingList
				+ "]";
	}
	

	
}
