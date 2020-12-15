package com.ojas.obs.projecttechstack.request;

import java.util.List;

import com.ojas.obs.projecttechstack.model.ProjectTechStack;

public class ProjectTechStackRequest {
	private List<ProjectTechStack> projectTechStackList;
	private String transactionType;
	public List<ProjectTechStack> getProjectTechStackList() {
		return projectTechStackList;
	}
	public void setProjectTechStackList(List<ProjectTechStack> projectTechStackList) {
		this.projectTechStackList = projectTechStackList;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "ProjectTechStackRequest [projectTechStackList=" + projectTechStackList + ", transactionType="
				+ transactionType + "]";
	}
	
}
