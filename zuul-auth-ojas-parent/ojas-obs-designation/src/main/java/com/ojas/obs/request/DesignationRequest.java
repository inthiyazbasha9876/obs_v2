package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.Designation;



/**
 * 
 * @author nsrikanth
 *
 */

public class DesignationRequest {

	private List<Designation> designation;
    private String transactionType;
	public List<Designation> getDesignation() {
		return designation;
	}
	public void setDesignation(List<Designation> designation) {
		this.designation = designation;
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
		result = prime * result + ((designation == null) ? 0 : designation.hashCode());
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
		DesignationRequest other = (DesignationRequest) obj;
		if (designation == null) {
			if (other.designation != null)
				return false;
		} else if (!designation.equals(other.designation))
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
		return "DesignationRequest [designation=" + designation + ", transactionType=" + transactionType + "]";
	} 

	

}
