package com.ojas.obs.rateType.request;

import java.util.List;

import com.ojas.obs.rateType.model.RateType;

public class RateTypeRequest {
	private List<RateType> rateType;
	private String transactionType;

	public List<RateType> getRateType() {
		return rateType;
	}

	public void setRateType(List<RateType> rateType) {
		this.rateType = rateType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "RateTypeRequest [rateType=" + rateType + ", transactionType=" + transactionType + "]";
	}

}
