package com.ojas.obs.request;

import com.ojas.obs.model.App;

public class AppRequest {

	private App applist;
	private String transactionType;
	public App getApplist() {
		return applist;
	}
	public void setApplist(App applist) {
		this.applist = applist;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "AppRequest [applist=" + applist + ", transactionType=" + transactionType + "]";
	}
	
	
	
	
	
}
