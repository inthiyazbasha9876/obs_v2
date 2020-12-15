package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.EmployeeExperience;

public class EmployeeExperienceRequest {
	private List<EmployeeExperience> empExperienceList;
	private String transactionType;

	public List<EmployeeExperience> getEmpExperienceList() {
		return empExperienceList;
	}

	public void setEmpExperienceList(List<EmployeeExperience> empExperienceList) {
		this.empExperienceList = empExperienceList;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

}
