package com.ojas.obs.model;

import java.util.List;

public class CostCenterRequest {

	private List<CostCenter> costCenter;
	private String transactionType;

	public List<CostCenter> getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(List<CostCenter> costCenter) {
		this.costCenter = costCenter;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "CostCenterRequest [costCenter=" + costCenter + ", transactionType=" + transactionType + "]";
	}
}