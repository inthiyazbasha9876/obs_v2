package com.ojas.obs.projectDetails.request;

import java.util.List;

import com.ojas.obs.projectDetails.model.ProjectDetails;

public class ProjectDetailsRequest {
	private String transactionType;
	private List<ProjectDetails> projectDetailsList;

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public List<ProjectDetails> getProjectDetailsList() {
		return projectDetailsList;
	}

	public void setProjectDetailsList(List<ProjectDetails> projectDetailsList) {
		this.projectDetailsList = projectDetailsList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((projectDetailsList == null) ? 0 : projectDetailsList.hashCode());
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
		ProjectDetailsRequest other = (ProjectDetailsRequest) obj;
		if (projectDetailsList == null) {
			if (other.projectDetailsList != null)
				return false;
		} else if (!projectDetailsList.equals(other.projectDetailsList)) {
			return false;}
		if (transactionType == null) {
			if (other.transactionType != null)
				return false;
		} else if (!transactionType.equals(other.transactionType)) {
			return false;}
		return true;
	}

}
