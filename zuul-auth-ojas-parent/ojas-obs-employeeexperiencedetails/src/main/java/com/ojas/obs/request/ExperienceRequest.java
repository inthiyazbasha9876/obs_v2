package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.EmployeeExperienceDetails;

public class ExperienceRequest {
	private String transactionType;
	List<EmployeeExperienceDetails> employeeExperienceDetails;

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public List<EmployeeExperienceDetails> getEmployeeExperienceDetails() {
		return employeeExperienceDetails;
	}

	public void setEmployeeExperienceDetails(List<EmployeeExperienceDetails> employeeExperienceDetails) {
		this.employeeExperienceDetails = employeeExperienceDetails;
	}

	@Override
	public String toString() {
		return "ExperienceRequest [transactionType=" + transactionType + ", employeeExperienceDetails="
				+ employeeExperienceDetails + "]";
	}


}
