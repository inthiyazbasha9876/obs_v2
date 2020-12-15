package com.ojas.obs.projecttask.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Project_Task")
public class ProjectTask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer projecttaskId;

	@Column(unique = true)
	private String projecttask;

	@Column(columnDefinition = "tinyint default true")
	private Boolean status;

	public Integer getProjecttaskId() {
		return projecttaskId;
	}

	public void setProjecttaskId(Integer projecttaskId) {
		this.projecttaskId = projecttaskId;
	}

	public String getProjecttask() {
		return projecttask;
	}

	public void setProjecttask(String projecttask) {
		this.projecttask = projecttask;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ProjectTask [projecttaskId=" + projecttaskId + ", projecttask=" + projecttask + ", status=" + status
				+ "]";
	}

	
}
