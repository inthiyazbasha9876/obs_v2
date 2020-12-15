package com.ojas.obs.projecttechstack.response;

import java.util.List;

import com.ojas.obs.projecttechstack.model.ProjectTechStack;

public class ProjectTechStackResponse {
	private List<ProjectTechStack> projectTechStackList;
	private String message;
	private String statusCode;

	public List<ProjectTechStack> getProjectTechStackList() {
		return projectTechStackList;
	}

	public void setProjectTechStackList(List<ProjectTechStack> projectTechStackList) {
		this.projectTechStackList = projectTechStackList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "ProjectTechStackResponse [projectTechStackList=" + projectTechStackList + ", message=" + message
				+ ", statusCode=" + statusCode + "]";
	}

}
