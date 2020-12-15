package com.ojas.obs.sez.request;

import java.util.List;

import com.ojas.obs.sez.model.Sez;

public class SezRequest {
	private List<Sez> sezlist;
	private String transactionType;
	public List<Sez> getSezlist() {
		return sezlist;
	}
	public void setSezlist(List<Sez> sezlist) {
		this.sezlist = sezlist;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "SezRequest [sezlist=" + sezlist + ", transactionType=" + transactionType + "]";
	}

	

}
