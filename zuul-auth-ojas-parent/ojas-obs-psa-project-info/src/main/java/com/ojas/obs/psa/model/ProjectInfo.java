package com.ojas.obs.psa.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "project_info")
@SequenceGenerator(name="project_seq", initialValue=11)
public class ProjectInfo{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator = "project_seq")
	@Column(length = 2)
	private Integer projectId;
	@Column(length = 100)
	private String projectName;
	@Column(length = 200)
	private String projectDescription;
	@Column
	private String startDate;
	@Column
	private String endDate;
	@Column(length = 4)
	private Integer customerId;
	@Column(length = 3)
	private Integer contractId;
	@Column(length = 45)
	private String servicecategory;
	@Column(length = 45)
	private String projectType;
	@Column(length = 45)
	private String deliveryLocation;
	@Column(length = 45)
	private String locationType;
	@Column(length = 8)
	private String buHead;
	@Column(length = 8)
	private String sbuHead;
	@Column(length = 20)
	private String buStatus;
	@Column(length = 20)
	private String financeStatus;
	@Column(length = 8)
	private String createdBy;
	@Column(length = 8)
	private String updatedBy;
	@Column(columnDefinition = "boolean default true")
	private Boolean flag = true;
	@Column(length = 45)
	@ElementCollection
	private Set<String> tasks;
	@ElementCollection
	private Set<String> additionalTasks;
	@Column(length = 256)
	private String comment;
	@Column(length = 45)
	private String projectColor;
	
	
	@OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "project")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","project"})
	private ProjectResourceMapping projectResourceMapping;
	
	@OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "project")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","project"})
	private ProjectRatecard projectRatecard;
	
	@OneToMany(mappedBy = "projectInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Milestone> milestones;

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public String getServicecategory() {
		return servicecategory;
	}

	public void setServicecategory(String servicecategory) {
		this.servicecategory = servicecategory;
	}

	public Set<String> getAdditionalTasks() {
		return additionalTasks;
	}

	public void setAdditionalTasks(Set<String> additionalTasks) {
		this.additionalTasks = additionalTasks;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getDeliveryLocation() {
		return deliveryLocation;
	}

	public void setDeliveryLocation(String deliveryLocation) {
		this.deliveryLocation = deliveryLocation;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public Set<String> getTasks() {
		return tasks;
	}

	public void setTasks(Set<String> tasks) {
		this.tasks = tasks;
	}

	public ProjectResourceMapping getProjectResourceMapping() {
		return projectResourceMapping;
	}

	public void setProjectResourceMapping(ProjectResourceMapping projectResourceMapping) {
		this.projectResourceMapping = projectResourceMapping;
	}

	public ProjectRatecard getProjectRatecard() {
		return projectRatecard;
	}

	public void setProjectRatecard(ProjectRatecard projectRatecard) {
		this.projectRatecard = projectRatecard;
	}
	public String getBuHead() {
		return buHead;
	}

	public void setBuHead(String buHead) {
		this.buHead = buHead;
	}

	public String getSbuHead() {
		return sbuHead;
	}

	public void setSbuHead(String sbuHead) {
		this.sbuHead = sbuHead;
	}

	public String getBuStatus() {
		return buStatus;
	}

	public void setBuStatus(String buStatus) {
		this.buStatus = buStatus;
	}

	public String getFinanceStatus() {
		return financeStatus;
	}

	public void setFinanceStatus(String financeStatus) {
		this.financeStatus = financeStatus;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Milestone> getMilestones() {
		return milestones;
	}

	public void setMilestones(List<Milestone> milestones) {
		this.milestones = milestones;
	}

	public String getProjectColor() {
		return projectColor;
	}

	public void setProjectColor(String projectColor) {
		this.projectColor = projectColor;
	}

	@Override
	public String toString() {
		return "ProjectInfo [projectId=" + projectId + ", projectName=" + projectName + ", projectDescription="
				+ projectDescription + ", startDate=" + startDate + ", endDate=" + endDate + ", customerId="
				+ customerId + ", contractId=" + contractId + ", servicecategory=" + servicecategory + ", projectType="
				+ projectType + ", deliveryLocation=" + deliveryLocation + ", locationType=" + locationType
				+ ", buHead=" + buHead + ", sbuHead=" + sbuHead + ", buStatus=" + buStatus + ", financeStatus="
				+ financeStatus + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", flag=" + flag
				+ ", tasks=" + tasks + ", additionalTasks=" + additionalTasks + ", comment=" + comment
				+ ", projectColor=" + projectColor + ", projectResourceMapping=" + projectResourceMapping
				+ ", projectRatecard=" + projectRatecard + ", milestones=" + milestones + "]";
	}

}
