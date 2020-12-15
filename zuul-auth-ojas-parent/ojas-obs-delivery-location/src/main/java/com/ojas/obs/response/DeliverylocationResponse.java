package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.DeliveryLocation;



public class DeliverylocationResponse
{

	private String message;
	private String statusCode;
	private List<DeliveryLocation> deliverylocationList;
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
	public List<DeliveryLocation> getDeliverylocationList() {
		return deliverylocationList;
	}
	public void setDeliverylocationList(List<DeliveryLocation> deliverylocationList) {
		this.deliverylocationList = deliverylocationList;
	}
	@Override
	public String toString() {
		return "CmsResponse [message=" + message + ", statusCode=" + statusCode + ", deliverylocationList="
				+ deliverylocationList + "]";
	}
	
}
