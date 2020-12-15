package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.GpaPlan;
/**
 * 
 * @author pnaveen
 *
 */

public class GpaRequest {

	private List<GpaPlan> gpaPlan;
	private String transactionType;
	
	public List<GpaPlan> getGpaPlan() {
		return gpaPlan;
	}
	public void setGpaPlan(List<GpaPlan> gpaPlan) {
		this.gpaPlan = gpaPlan;
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
		result = prime * result + ((gpaPlan == null) ? 0 : gpaPlan.hashCode());
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
		GpaRequest other = (GpaRequest) obj;
		if (gpaPlan == null) {
			if (other.gpaPlan != null)
				return false;
		} else if (!gpaPlan.equals(other.gpaPlan))
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
		return "GpaRequest [gpaPlan=" + gpaPlan + ", transactionType=" + transactionType + "]";
	}
	
}

