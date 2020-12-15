package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.CustomerContractDetails;

public class CustomerContractDetailsRequest {

	private List<CustomerContractDetails> customercontractdetailslist;
	private String transactiontype;

	public List<CustomerContractDetails> getCustomercontractdetailslist() {
		return customercontractdetailslist;
	}

	public void setCustomercontractdetailslist(List<CustomerContractDetails> customercontractdetailslist) {
		this.customercontractdetailslist = customercontractdetailslist;
	}

	public String getTransactiontype() { 
		return transactiontype;
	} 

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}
 
}
