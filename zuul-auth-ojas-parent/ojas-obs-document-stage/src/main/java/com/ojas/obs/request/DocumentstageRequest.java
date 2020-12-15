package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.DocumentStage;

public class DocumentstageRequest {  
	private List<DocumentStage> doucumentStageList;
	private String transactionType;

	public List<DocumentStage> getDoucumentStageList() {
		return doucumentStageList;
	}

	public void setDoucumentStageList(List<DocumentStage> doucumentStageList) {
		this.doucumentStageList = doucumentStageList;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "DocumentstageRequest [doucumentStageList=" + doucumentStageList + ", transactionType=" + transactionType
				+ "]";
	}

}