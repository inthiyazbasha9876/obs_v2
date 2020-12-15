package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.States;

public class StatesRequest {
	private List<States> states;
	private String transactionType;
	public List<States> getStates() {
		return states;
	}
	public void setStates(List<States> states) {
		this.states = states;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "StatesRequest [states=" + states + ", transactionType=" + transactionType + "]";
	}
	
	
	

}
