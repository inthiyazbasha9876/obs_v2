package com.ojas.obs.psa.request;

import java.util.List;

import com.ojas.obs.psa.model.Milestone;
import com.ojas.obs.psa.model.ProjectInfo;
import com.ojas.obs.psa.model.ProjectRatecard;
import com.ojas.obs.psa.model.ProjectResourceMapping;

public class ProjectInfoRequest {
	private ProjectInfo projectInfo;
	private ProjectRatecard rateCard;
	private ProjectResourceMapping resourceMap;
	private List<Milestone> milestones;
	private String transactionType;
	
	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}
	public void setProjectInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}
	public ProjectRatecard getRateCard() {
		return rateCard;
	}
	public void setRateCard(ProjectRatecard rateCard) {
		this.rateCard = rateCard;
	}
	public ProjectResourceMapping getResourceMap() {
		return resourceMap;
	}
	public void setResourceMap(ProjectResourceMapping resourceMap) {
		this.resourceMap = resourceMap;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public List<Milestone> getMilestones() {
		return milestones;
	}
	public void setMilestones(List<Milestone> milestones) {
		this.milestones = milestones;
	}
	@Override
	public String toString() {
		return "ProjectInfoRequest [projectInfo=" + projectInfo + ", rateCard=" + rateCard + ", resourceMap="
				+ resourceMap + ", milestones=" + milestones + ", transactionType=" + transactionType + "]";
	}

}
