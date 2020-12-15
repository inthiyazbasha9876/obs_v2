package com.ojas.obs.projecttask.request;

import java.util.List;

import com.ojas.obs.projecttask.model.ProjectTask;

public class ProjectTaskRequest {
	private List<ProjectTask> projecttasklist;
	private String transactionType;

	public List<ProjectTask> getProjecttasklist() {
		return projecttasklist;
	}

	public void setProjecttasklist(List<ProjectTask> projecttasklist) {
		this.projecttasklist = projecttasklist;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "ProjectTaskRequest [projecttasklist=" + projecttasklist + ", transactionType=" + transactionType + "]";
	}

}
