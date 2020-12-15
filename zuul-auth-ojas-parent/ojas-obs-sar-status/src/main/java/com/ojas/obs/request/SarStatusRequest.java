package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.SarStatus;

public class SarStatusRequest {
	private List<SarStatus> sarstatusList;
	private String transactionType;

	public List<SarStatus> getSarstatusList() {
		return sarstatusList;
	}

	public void setSarstatusList(List<SarStatus> sarstatusList) {
		this.sarstatusList = sarstatusList;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "SarStatusRequest [sarstatusList=" + sarstatusList + ", transactionType=" + transactionType + "]";
	}

}