package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.Budget;

public class BudgetRequest {
	private List<Budget> budgetList;
	private String transactionType;

	public List<Budget> getBudgetList() {
		return budgetList;
	}

	public void setBudgetList(List<Budget> budgetList) {
		this.budgetList = budgetList;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "BudgetRequest [budgetList=" + budgetList + ", transactionType=" + transactionType + "]";
	}
}
