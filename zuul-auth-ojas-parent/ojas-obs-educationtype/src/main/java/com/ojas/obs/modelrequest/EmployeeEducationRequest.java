package com.ojas.obs.modelrequest;

import java.util.List;

import com.ojas.obs.model.EmployeeEducation;
/**
 * 
 * @author mpraneethguptha
 *
 */

public class EmployeeEducationRequest {

	private List<EmployeeEducation> listEmployeeEducations;
    private String transactionType;

	public List<EmployeeEducation> getListEmployeeEducations() {
		return listEmployeeEducations;
	}

	public void setListEmployeeEducations(List<EmployeeEducation> listEmployeeEducations) {
		this.listEmployeeEducations = listEmployeeEducations;
	}
	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "EmployeeEducationRequest [listEmployeeEducations=" + listEmployeeEducations + ", transactionType="
				+ transactionType + "]";
	}
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((listEmployeeEducations == null) ? 0 : listEmployeeEducations.hashCode());
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
		EmployeeEducationRequest other = (EmployeeEducationRequest) obj;
		if (listEmployeeEducations == null) {
			if (other.listEmployeeEducations != null)
				return false;
		} else if (!listEmployeeEducations.equals(other.listEmployeeEducations)) {
			return false;}
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType)) {
			return false;
		}
		return true;
	}

	
	

	

}