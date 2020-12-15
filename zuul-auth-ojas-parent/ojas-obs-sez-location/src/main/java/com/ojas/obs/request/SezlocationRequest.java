package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.SezLocation;



public class SezlocationRequest 
{

	private List<SezLocation> sezlocationList;
	private String transactionType;
	public List<SezLocation> getSezlocationList() {
		return sezlocationList;
	}
	public void setSezlocationList(List<SezLocation> sezlocationList) {
		this.sezlocationList = sezlocationList;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "SezlocationRequest [sezlocationList=" + sezlocationList + ", transactionType=" + transactionType + "]";
	}
	
	

	
}
