package com.ojas.obs.tms.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class TimeSheet {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Integer timeSheetID;
	@NotNull
	
	@Column
	private String employeeId;
	@NotNull
	@Column
	private String timeSheetStartDate;
	//@NotNull
	@Column
	private Float totalHoursLogged;
	@Column
	private String filePath;
	@Transient
	private String attachment;
	@Transient
	private String fileName;
	@Transient
	private String reportingMngr;
	@Transient
	private String projectId;
	@Transient
	@JsonProperty("startDate")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	@Transient
	@JsonProperty("endDate")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date endDate;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "timeSheet_submission_record_map", joinColumns = {
			@JoinColumn(name = "timeSheetID") }, inverseJoinColumns = { @JoinColumn(name = "recordId") })
	@NotNull
	@JsonIgnoreProperties("sheet")
	private Set<Record> record = new HashSet<>();
	
	@OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "sheet")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","sheet"})
	private TimesheetStatus timesheetStatus;

	public TimeSheet() {}
	public TimeSheet(String employeeId, @NotNull String timeSheetStartDate, String timesheetState) {
		super();
		this.employeeId = employeeId;
		this.timeSheetStartDate = timeSheetStartDate;
		this.timesheetStatus = new TimesheetStatus(timesheetState);
	}
	public Integer getTimeSheetID() {
		return timeSheetID;
	}
	public void setTimeSheetID(Integer timeSheetID) {
		this.timeSheetID = timeSheetID;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getTimeSheetStartDate() {
		return timeSheetStartDate;
	}
	public void setTimeSheetStartDate(String timeSheetStartDate) {
		this.timeSheetStartDate = timeSheetStartDate;
	}
	public Float getTotalHoursLogged() {
		return totalHoursLogged;
	}
	public void setTotalHoursLogged(Float totalHoursLogged) {
		this.totalHoursLogged = totalHoursLogged;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getReportingMngr() {
		return reportingMngr;
	}
	public void setReportingMngr(String reportingMngr) {
		this.reportingMngr = reportingMngr;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Set<Record> getRecord() {
		return record;
	}
	public void setRecord(Set<Record> record) {
		this.record = record;
	}
	public TimesheetStatus getTimesheetStatus() {
		return timesheetStatus;
	}
	public void setTimesheetStatus(TimesheetStatus timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
	}
	@Override
	public String toString() {
		return "TimeSheet [timeSheetID=" + timeSheetID + ", employeeId=" + employeeId + ", timeSheetStartDate="
				+ timeSheetStartDate + ", totalHoursLogged=" + totalHoursLogged + ", filePath=" + filePath
				+ ", attachment=" + attachment + ", fileName=" + fileName + ", reportingMngr=" + reportingMngr
				+ ", projectId=" + projectId + ", startDate=" + startDate + ", endDate=" + endDate + ", record="
				+ record + ", timesheetStatus=" + timesheetStatus + "]";
	}

}