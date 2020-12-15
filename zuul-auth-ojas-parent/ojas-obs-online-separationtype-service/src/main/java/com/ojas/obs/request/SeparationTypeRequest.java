package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.SeparationType;

/**
 * 
 * @author nsrikanth
 *
 */

public class SeparationTypeRequest {

	private List<SeparationType> separationType;

	private String transactionType;

	public List<SeparationType> getSeparationType() {
		return separationType;
	}

	public void setSeparationType(List<SeparationType> separationType) {
		this.separationType = separationType;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((separationType == null) ? 0 : separationType.hashCode());
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
		SeparationTypeRequest other = (SeparationTypeRequest) obj;
		if (separationType == null) {
			if (other.separationType != null)
				return false;
		} else if (!separationType.equals(other.separationType))
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SeparationTypeRequest [separationType=" + separationType + ", transactionType=" + transactionType + "]";
	}
	

}
