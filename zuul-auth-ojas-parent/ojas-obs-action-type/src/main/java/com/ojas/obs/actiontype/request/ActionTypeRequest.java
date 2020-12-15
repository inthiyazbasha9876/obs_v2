package com.ojas.obs.actiontype.request;

import java.util.List;

import com.ojas.obs.actiontype.model.ActionType;

public class ActionTypeRequest {

	private List<ActionType> actionTypeList;
	private String transactionType;

	public List<ActionType> getActionTypeList() {
		return actionTypeList;
	}

	public void setActionTypeList(List<ActionType> actionTypeList) {
		this.actionTypeList = actionTypeList;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "ActionTypeRequest [actionTypeList=" + actionTypeList + ", transactionType=" + transactionType + "]";
	}

}
