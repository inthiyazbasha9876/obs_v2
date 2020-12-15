package com.ojas.obs.psa.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity(name = "resource_mapping")
public class ProjectResourceMapping {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer resourceMappingId;
//	@Column(length = 10)
//	private Integer resourceCount;
	@Column(length = 50)
	private String projectManager;
	@Column(length = 50)
	private String techLead;
	// @ManyToMany(cascade = { CascadeType.ALL })

	/*
	 * 
	 * @JoinColumn(name = "resourceMappingId") }, inverseJoinColumns =
	 * { @JoinColumn(name = "techStack") })
	 */
	@ElementCollection
	@Column(length = 45)
	private Set<String> techStack;
//	@ElementCollection
//	@Column(length = 10, name = "project_resources")
//	private Set<String> resources;

	/*
	 * @JsonIgnoreProperties({"hibernateLazyInitializer",
	 * "handler","projectResourceMapping"})
	 * 
	 * @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy =
	 * "projectResourceMapping")
	 */
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "projectId", nullable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "projectResourceMapping" })
	private ProjectInfo project;

	public Integer getResourceMappingId() {
		return resourceMappingId;
	}

	public void setResourceMappingId(Integer resourceMappingId) {
		this.resourceMappingId = resourceMappingId;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getTechLead() {
		return techLead;
	}

	public void setTechLead(String techLead) {
		this.techLead = techLead;
	}

	public Set<String> getTechStack() {
		return techStack;
	}

	public void setTechStack(Set<String> techStack) {
		this.techStack = techStack;
	}

	public ProjectInfo getProject() {
		return project;
	}

	public void setProject(ProjectInfo project) {
		this.project = project;
	}

	@Override
	public String toString() {
		return "ProjectResourceMapping [resourceMappingId=" + resourceMappingId + ", projectManager=" + projectManager
				+ ", techLead=" + techLead + ", techStack=" + techStack + ", project=" + project + "]";
	}

}
