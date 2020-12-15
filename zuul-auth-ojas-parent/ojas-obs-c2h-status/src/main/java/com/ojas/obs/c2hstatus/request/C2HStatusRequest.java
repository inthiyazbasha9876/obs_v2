package com.ojas.obs.c2hstatus.request;

import java.util.List;

import com.ojas.obs.c2hstatus.model.C2HStatus;

public class C2HStatusRequest {
	private List<C2HStatus> c2hstatuslist;
	private String transactionType;
	public List<C2HStatus> getC2hstatuslist() {
		return c2hstatuslist;
	}
	public void setC2hstatuslist(List<C2HStatus> c2hstatuslist) {
		this.c2hstatuslist = c2hstatuslist;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "C2HStatusRequest [c2hstatuslist=" + c2hstatuslist + ", transactionType=" + transactionType + "]";
	}
	
}
