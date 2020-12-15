package com.ojas.obs.projecttype.request;

import java.util.List;

import com.ojas.obs.projecttype.model.ProjectType;

public class ProjectTypeRequest {
	private List<ProjectType> projectTypeList;
	private String transactionType;

	public List<ProjectType> getProjectTypeList() {
		return projectTypeList;
	}

	public void setProjectTypeList(List<ProjectType> projectTypeList) {
		this.projectTypeList = projectTypeList;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "ProjectTypeRequest [projectTypeList=" + projectTypeList + ", transactionType=" + transactionType + "]";
	}

}
