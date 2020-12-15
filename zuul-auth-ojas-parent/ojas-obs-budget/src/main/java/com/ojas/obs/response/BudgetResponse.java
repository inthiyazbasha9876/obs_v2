package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.Budget;

public class BudgetResponse {
	private String message;
	private String statusCode;
	private List<Budget> budgetList;

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

	public List<Budget> getBudgetList() {
		return budgetList;
	}

	public void setBudgetList(List<Budget> budgetList) {
		this.budgetList = budgetList;
	}

	@Override
	public String toString() {
		return "BudgetResponse [message=" + message + ", statusCode=" + statusCode + ", budgetList=" + budgetList + "]";
	}
}
