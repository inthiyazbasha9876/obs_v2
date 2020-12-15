package com.ojas.obs.actionowner.request;

import java.util.List;

import com.ojas.obs.actionowner.model.ActionOwner;

public class ActionOwnerRequest {
	private List<ActionOwner> actionOwnerList;
	private String transactionType;
	public List<ActionOwner> getActionOwnerList() {
		return actionOwnerList;
	}
	public void setActionOwnerList(List<ActionOwner> actionOwnerList) {
		this.actionOwnerList = actionOwnerList;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "ActionOwnerRequest [actionOwnerList=" + actionOwnerList + ", transactionType=" + transactionType + "]";
	}

}
