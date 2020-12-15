package com.ojas.obs.projectDetails.response;

import java.util.List;

import com.ojas.obs.projectDetails.model.ProjectDetails;

public class ProjectDetailsResponse {
	private List<ProjectDetails> projectDetailsList;
	private String statusCode;
	private String statusMessage;

	public List<ProjectDetails> getProjectDetailsList() {
		return projectDetailsList;
	}

	public void setProjectDetailsList(List<ProjectDetails> projectDetailsList) {
		this.projectDetailsList = projectDetailsList;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((projectDetailsList == null) ? 0 : projectDetailsList.hashCode());
		result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
		result = prime * result + ((statusMessage == null) ? 0 : statusMessage.hashCode());
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
		ProjectDetailsResponse other = (ProjectDetailsResponse) obj;
		if (projectDetailsList == null) {
			if (other.projectDetailsList != null)
				return false;
		} else if (!projectDetailsList.equals(other.projectDetailsList)) {
			return false;}
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode)) {
			return false;}
		if (statusMessage == null) {
			if (other.statusMessage != null)
				return false;
		} else if (!statusMessage.equals(other.statusMessage)) {
			return false;}
		return true;
	}

}
