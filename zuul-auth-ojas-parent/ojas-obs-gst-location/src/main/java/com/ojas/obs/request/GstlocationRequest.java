package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.GstLocation;



public class GstlocationRequest 
{

	private List<GstLocation> gstlocationList;
	private String transactionType;
	
	public List<GstLocation> getGstlocationList() {
		return gstlocationList;
	}
	public void setGstlocationList(List<GstLocation> gstlocationList) {
		this.gstlocationList = gstlocationList;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	@Override
	public String toString() {
		return "GstlocationRequest [gstlocationList=" + gstlocationList + ", transactionType=" + transactionType + "]";
	}
	
	
	

	
}
