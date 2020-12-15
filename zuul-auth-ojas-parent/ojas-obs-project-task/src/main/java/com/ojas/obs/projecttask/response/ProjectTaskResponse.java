package com.ojas.obs.projecttask.response;

import java.util.List;

import com.ojas.obs.projecttask.model.ProjectTask;

public class ProjectTaskResponse {
	private String message;
	private String statusCode;
	private List<ProjectTask> projecttasklist;

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

	public List<ProjectTask> getProjecttasklist() {
		return projecttasklist;
	}

	public void setProjecttasklist(List<ProjectTask> projecttasklist) {
		this.projecttasklist = projecttasklist;
	}

	@Override
	public String toString() {
		return "ProjectTaskResponse [message=" + message + ", statusCode=" + statusCode + ", projecttasklist="
				+ projecttasklist + "]";
	}

}
