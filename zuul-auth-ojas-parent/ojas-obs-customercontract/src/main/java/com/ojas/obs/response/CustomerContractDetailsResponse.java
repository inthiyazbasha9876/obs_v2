package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.CustomerContractDetails;

public class CustomerContractDetailsResponse {

	private String message;
	private String statusCode;
	private List<CustomerContractDetails> customercontractdetailslist;
	private String document;
	

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
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

	public List<CustomerContractDetails> getCustomercontractdetailslist() {
		return customercontractdetailslist;
	}

	public void setCustomercontractdetailslist(List<CustomerContractDetails> customercontractdetailslist) {
		this.customercontractdetailslist = customercontractdetailslist;
	}

}
