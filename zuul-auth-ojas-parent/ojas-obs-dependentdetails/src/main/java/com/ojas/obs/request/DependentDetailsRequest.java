package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.DependentDetails;

public class DependentDetailsRequest {
	private List<DependentDetails> dependentDetails;
	private String transactionType;
	
	
	public List<DependentDetails> getDependentDetails() {
		return dependentDetails;
	}
	public void setDependentDetails(List<DependentDetails> dependentDetails) {
		this.dependentDetails = dependentDetails;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "DependentDetailsRequest [dependentDetails=" + dependentDetails + ", transactionType=" + transactionType
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dependentDetails == null) ? 0 : dependentDetails.hashCode());
		result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DependentDetailsRequest other = (DependentDetailsRequest) obj;
		if (dependentDetails == null) {
			if (other.dependentDetails != null)
				return false;
		} else if (!dependentDetails.equals(other.dependentDetails))
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		return true;
	}

	
	
}
