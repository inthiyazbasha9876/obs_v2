package com.ojas.obs.psa.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class ProjectRatecard {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer ratecardId;
	@Column(length = 10)
	private Double projectValue;
	@Column(length = 45)
	private String serviceType;
	@Column(length = 45)
	private String billingType;
	@Column(length = 45)
	private String rateType;
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "projectId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler","projectRatecard"})
	private ProjectInfo project;
	
	public Integer getRatecardId() {
		return ratecardId;
	}
	public void setRatecardId(Integer ratecardId) {
		this.ratecardId = ratecardId;
	}
	public Double getProjectValue() {
		return projectValue;
	}
	public void setProjectValue(Double projectValue) {
		this.projectValue = projectValue;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getBillingType() {
		return billingType;
	}
	public void setBillingType(String billingType) {
		this.billingType = billingType;
	}
	public String getRateType() {
		return rateType;
	}
	public void setRateType(String rateType) {
		this.rateType = rateType;
	}
	public ProjectInfo getProject() {
		return project;
	}
	public void setProject(ProjectInfo project) {
		this.project = project;
	}
	@Override
	public String toString() {
		return "ProjectRatecard [ratecardId=" + ratecardId + ", projectValue=" + projectValue + ", serviceType="
				+ serviceType + ", billingType=" + billingType + ", rateType=" + rateType + ", project=" + project
				+ "]";
	}
	
}
