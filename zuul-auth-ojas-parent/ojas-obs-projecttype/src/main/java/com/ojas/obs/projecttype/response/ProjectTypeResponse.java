package com.ojas.obs.projecttype.response;

import java.util.List;

import com.ojas.obs.projecttype.model.ProjectType;

public class ProjectTypeResponse {
	private String message;
	private String statusCode;
	private List<ProjectType> projectTypeList;

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

	public List<ProjectType> getProjectTypeList() {
		return projectTypeList;
	}

	public void setProjectTypeList(List<ProjectType> projectTypeList) {
		this.projectTypeList = projectTypeList;
	}

	@Override
	public String toString() {
		return "ProjectTypeResponse [message=" + message + ", statusCode=" + statusCode + ", projectTypeList="
				+ projectTypeList + "]";
	}

}