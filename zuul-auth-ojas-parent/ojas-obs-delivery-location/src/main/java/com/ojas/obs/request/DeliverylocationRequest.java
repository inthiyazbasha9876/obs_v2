package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.DeliveryLocation;



public class DeliverylocationRequest 
{

	private List<DeliveryLocation> deliverylocationList;
	private String transactionType;
	
	
	public List<DeliveryLocation> getDeliverylocationList() {
		return deliverylocationList;
	}
	public void setDeliverylocationList(List<DeliveryLocation> deliverylocationList) {
		this.deliverylocationList = deliverylocationList;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "CmsRequest [deliverylocationList=" + deliverylocationList + ", transactionType=" + transactionType
				+ "]";
	}
	
	
}
