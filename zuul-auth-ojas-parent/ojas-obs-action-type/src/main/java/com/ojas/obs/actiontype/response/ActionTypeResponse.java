package com.ojas.obs.actiontype.response;

import java.util.List;

import com.ojas.obs.actiontype.model.ActionType;

public class ActionTypeResponse {
	private String message;
	private String statusCode;
	private List<ActionType> actionTypeList;

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

	public List<ActionType> getActionTypeList() {
		return actionTypeList;
	}

	public void setActionTypeList(List<ActionType> actionTypeList) {
		this.actionTypeList = actionTypeList;
	}

	@Override
	public String toString() {
		return "ActionTypeResponse [message=" + message + ", statusCode=" + statusCode + ", actionTypeList="
				+ actionTypeList + "]";
	}

}
