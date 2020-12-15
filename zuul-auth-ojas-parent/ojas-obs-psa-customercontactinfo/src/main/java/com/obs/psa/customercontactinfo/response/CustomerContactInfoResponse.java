package com.obs.psa.customercontactinfo.response;

import java.util.List;

import com.obs.psa.customercontactinfo.model.CustomerContactInfo;

public class CustomerContactInfoResponse {
	private String message;
	private String statusCode;
	private List<CustomerContactInfo> customerContactInfoList;
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
	public List<CustomerContactInfo> getCustomerContactInfoList() {
		return customerContactInfoList;
	}
	public void setCustomerContactInfoList(List<CustomerContactInfo> customerContactInfoList) {
		this.customerContactInfoList = customerContactInfoList;
	}
	
	@Override
	public String toString() {
		return "CustomerContactInfoResponse [message=" + message + ", statusCode=" + statusCode
				+ ", customerContactInfoList=" + customerContactInfoList + "]";
	}

}
