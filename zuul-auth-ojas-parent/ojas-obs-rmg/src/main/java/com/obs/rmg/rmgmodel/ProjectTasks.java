package com.obs.rmg.rmgmodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class ProjectTasks 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int taskId;
	@Column
	private String taskName;
	@Column
	private String taskStartDate;
	@Column
	private String taskEndDate;
	@Column
	private Double hoursPerDay;
	@Column
	private String taskStatus;
	@ManyToOne
    @JoinColumn(name = "specificId")
    private RmgSpecific rmgspecific;
	
	@ManyToOne
    @JoinColumn(name = "resourcegenericId")
    private RmgGenericResourceMap rmggenericresourcemap;
	
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskStartDate() {
		return taskStartDate;
	}
	public void setTaskStartDate(String taskStartDate) {
		this.taskStartDate = taskStartDate;
	}
	public String getTaskEndDate() {
		return taskEndDate;
	}
	public void setTaskEndDate(String taskEndDate) {
		this.taskEndDate = taskEndDate;
	}
	public Double getHoursPerDay() {
		return hoursPerDay;
	}
	public void setHoursPerDay(Double hoursPerDay) {
		this.hoursPerDay = hoursPerDay;
	}
	
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public RmgSpecific getRmgspecific() {
		return rmgspecific;
	}
	public void setRmgspecific(RmgSpecific rmgspecific) {
		this.rmgspecific = rmgspecific;
	}
	public RmgGenericResourceMap getRmggenericresourcemap() {
		return rmggenericresourcemap;
	}
	public void setRmggenericresourcemap(RmgGenericResourceMap rmggenericresourcemap) {
		this.rmggenericresourcemap = rmggenericresourcemap;
	}
	
	
}
