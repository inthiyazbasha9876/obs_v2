package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.EmployeeBUDetails;

public class EmployeeBUDetailsRequest {
	private List<EmployeeBUDetails> employeeBUDeatils;
	private String transactionType;

	public List<EmployeeBUDetails> getEmployeeBUDeatils() {
		return employeeBUDeatils;
	}

	public void setEmployeeBUDeatils(List<EmployeeBUDetails> employeeBUDeatils) {
		this.employeeBUDeatils = employeeBUDeatils;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "EmployeeBUDetailsRequest [employeeBUDeatils=" + employeeBUDeatils + ", transactionType="
				+ transactionType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeBUDeatils == null) ? 0 : employeeBUDeatils.hashCode());
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
		EmployeeBUDetailsRequest other = (EmployeeBUDetailsRequest) obj;
		if (employeeBUDeatils == null) {
			if (other.employeeBUDeatils != null)
				return false;
		} else if (!employeeBUDeatils.equals(other.employeeBUDeatils))
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		return true;
	}

}
