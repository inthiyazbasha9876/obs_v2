package com.obs.psa.customercontactinfo.request;

import java.util.List;

import com.obs.psa.customercontactinfo.model.CustomerContactInfo;

public class CustomerContactInfoRequest {
	private List<CustomerContactInfo> customerContactInfo;
	private String transactionType;
	
	public List<CustomerContactInfo> getCustomerContactInfo() {
		return customerContactInfo;
	}
	public void setCustomerContactInfo(List<CustomerContactInfo> customerContactInfo) {
		this.customerContactInfo = customerContactInfo;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
}
