package com.ojas.obs.billingtype.request;

import java.util.List;

import com.ojas.obs.billingtype.model.BillingType;

public class BillingTypeRequest {

	private List<BillingType> billingList;
	private String transactionType;

	public List<BillingType> getBillingList() {
		return billingList;
	}

	public void setBillingList(List<BillingType> billingList) {
		this.billingList = billingList;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "BillingTypeRequest [billingList=" + billingList + ", transactionType=" + transactionType + "]";
	}

}
