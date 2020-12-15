package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.EmployeeStatus;

/**
 * 
 * @author Manohar
 *
 */
public class EmployeeStatusRequest {

	private List<EmployeeStatus> employeeStatus;

	private String transactionType;

	public List<EmployeeStatus> getEmployeeStatus() {
		return employeeStatus;
	}

	public void setEmployeeStatus(List<EmployeeStatus> employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "EmployeeStatusRequest [employeeStatus=" + employeeStatus + ", transactionType=" + transactionType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeStatus == null) ? 0 : employeeStatus.hashCode());
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
		EmployeeStatusRequest other = (EmployeeStatusRequest) obj;
		if (employeeStatus == null) {
			if (other.employeeStatus != null)
				return false;
		} else if (!employeeStatus.equals(other.employeeStatus))
			return false;
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType))
			return false;
		return true;
	}
	
	

}
