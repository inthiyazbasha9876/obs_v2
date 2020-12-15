package com.ojas.obs.actionowner.response;

import java.util.List;

import com.ojas.obs.actionowner.model.ActionOwner;

public class ActionOwnerResponse {
	private String message;
	private String statusCode;
	private List<ActionOwner> actionOwnerList;

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

	public List<ActionOwner> getActionOwnerList() {
		return actionOwnerList;
	}

	public void setActionOwnerList(List<ActionOwner> actionOwnerList) {
		this.actionOwnerList = actionOwnerList;
	}

	@Override
	public String toString() {
		return "ActionOwnerResponse [message=" + message + ", statusCode=" + statusCode + ", actionOwnerList="
				+ actionOwnerList + "]";
	}

}
