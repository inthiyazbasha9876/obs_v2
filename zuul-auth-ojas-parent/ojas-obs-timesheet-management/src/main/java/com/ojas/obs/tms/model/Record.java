package com.ojas.obs.tms.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@NamedNativeQuery(name = "Schedules", query = "SELECT t.employee_id, r.project_id, COALESCE(SUM(r.hours_logged),0) as hours from obs_tms.time_sheet t, obs_tms.record r, obs_tms.timesheet_status ts, obs_tms.time_sheet_submission_record_map tr where r.project_id= :projId and r.date BETWEEN :from and :to and tr.time_sheetid = t.time_sheetid and tr.record_id = r.record_id and ts.timesheet_id = tr.time_sheetid and ts.submission_state='Approved' group by t.employee_id", resultSetMapping = "OrderResults")
@NamedNativeQuery(name = "GetByProject", query = "SELECT r.record_id, r.hours_logged, r.activity_type, r.date, r.project_id, t.employee_id, COALESCE(SUM(r.hours_logged),0) as hours from obs_tms.time_sheet t, obs_tms.record r, obs_tms.time_sheet_submission_record_map tr, obs_tms.timesheet_status ts where r.project_id= :proId and t.employee_id= :empId and r.date BETWEEN :start and :end and tr.time_sheetid = t.time_sheetid and tr.record_id = r.record_id and ts.timesheet_id = tr.time_sheetid and ts.submission_state='Approved' group by r.record_id", resultSetMapping="GetRecordsByProject")

@SqlResultSetMapping(name = "OrderResults", classes = { @ConstructorResult(targetClass = Record.class, columns = {
		@ColumnResult(name = "employee_id"), @ColumnResult(name = "project_id"), @ColumnResult(name = "hours") }) })

@SqlResultSetMapping(name = "GetRecordsByProject", classes = { @ConstructorResult(targetClass = Record.class, columns = {
		@ColumnResult(name = "record_id"),@ColumnResult(name = "hours_logged"),
		@ColumnResult(name = "activity_type"), @ColumnResult(name = "date"), @ColumnResult(name = "project_id"),
		@ColumnResult(name = "employee_id"), @ColumnResult(name = "hours")
		}) })


public class Record {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer recordId;
	@NotNull
	@Column
	private String date;
	@Column(columnDefinition = "float default null", nullable = true)
	private Float hoursLogged;
	@NotNull
	@Column
	private String activityType;
	@Transient
	private String employeeId;
	@NotNull
	@Column
	private String projectId;
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "record" })
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "record")
	private Set<TimeSheet> sheet = new HashSet<>();
	@Transient
	private double hours;

	public Record() {
	}

	public Record(String empId, String projId, double hours) {
		this.employeeId = empId;
		this.projectId = projId;
		this.hours = hours;
	}

	public Record(Integer recordId, Float hoursLogged, String activityType, String date, String projectId, String employeeId, double hours) {
		this.recordId = recordId;
		this.date = date;
		this.hoursLogged = hoursLogged;
		this.activityType = activityType;
		this.employeeId = employeeId;
		this.projectId = projectId;
		this.hours = hours;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Float getHoursLogged() {
		return hoursLogged;
	}

	public void setHoursLogged(Float hoursLogged) {
		this.hoursLogged = hoursLogged;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Set<TimeSheet> getSheet() {
		return sheet;
	}

	public void setSheet(Set<TimeSheet> sheet) {
		this.sheet = sheet;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

	@Override
	public String toString() {
		return "Record [recordId=" + recordId + ", date=" + date + ", hoursLogged=" + hoursLogged + ", activityType="
				+ activityType + ", employeeId=" + employeeId + ", projectId=" + projectId + ", sheet=" + sheet
				+ ", hours=" + hours + "]";
	}

}
