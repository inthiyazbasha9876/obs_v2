package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.Resignation;

public class ResignationRequest {

	private List<Resignation> resignation;
	private String transactionType;
	public List<Resignation> getResignation() {
		return resignation;
	}
	public void setResignation(List<Resignation> resignation) {
		this.resignation = resignation;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "ResignationRequest [resignation=" + resignation + ", transactionType=" + transactionType + "]";
	}
	
}
