package com.ojas.obs.permstatus.request;

import java.util.List;

import com.ojas.obs.permstatus.model.PermStatus;

public class PermStatusRequest {
	private List<PermStatus> permStatusList;
	private String transactionType;
	public List<PermStatus> getPermStatusList() {
		return permStatusList;
	}
	public void setPermStatusList(List<PermStatus> permStatusList) {
		this.permStatusList = permStatusList;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "PermStatusRequest [permStatusList=" + permStatusList + ", transactionType=" + transactionType + "]";
	}
	
}
