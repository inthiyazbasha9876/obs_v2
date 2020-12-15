package com.ojas.obs.psa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "project_milestones")
public class Milestone{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer milestoneId;
	@Column(length = 255)
	private String milestoneName;
	@Column
	private String startDate;
	@Column
	private String endDate;
	@Column
	private Double billingAmount;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "project_id", nullable = true)
	private ProjectInfo projectInfo;
	public Integer getMilestoneId() {
		return milestoneId;
	}
	public void setMilestoneId(Integer milestoneId) {
		this.milestoneId = milestoneId;
	}
	public String getMilestoneName() {
		return milestoneName;
	}
	public void setMilestoneName(String milestoneName) {
		this.milestoneName = milestoneName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}
	public void setProjectInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}
	
	public Double getBillingAmount() {
		return billingAmount;
	}
	public void setBillingAmount(Double billingAmount) {
		this.billingAmount = billingAmount;
	}
	@Override
	public String toString() {
		return "Milestone [milestoneId=" + milestoneId + ", milestoneName=" + milestoneName + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", billingAmount=" + billingAmount + ", projectInfo=" + projectInfo + "]";
	}
}
