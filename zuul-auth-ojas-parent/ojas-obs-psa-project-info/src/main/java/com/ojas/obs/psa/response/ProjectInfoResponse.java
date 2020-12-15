package com.ojas.obs.psa.response;

import java.util.List;

import com.ojas.obs.psa.model.ProjectInfo;

public class ProjectInfoResponse {
	private List<ProjectInfo> projectList;
	private String message;
	private String statusCode;

	public List<ProjectInfo> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<ProjectInfo> projectList) {
		this.projectList = projectList;
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
		return "ProjectInfoResponse [projectList=" + projectList + ", message=" + message + ", statusCode=" + statusCode
				+ "]";
	}

}
