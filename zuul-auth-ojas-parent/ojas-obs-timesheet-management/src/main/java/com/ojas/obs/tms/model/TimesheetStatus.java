
package com.ojas.obs.tms.model;

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
public class TimesheetStatus {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer statusId;
	
	@Column
	private String submissionState;
	
	@Column
	private String approverId;
	
	@Column
	private String comment;
	
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "timesheet_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler","timesheetStatus"})
	private TimeSheet sheet;
    
    public TimesheetStatus() { }
    public TimesheetStatus(String submissionState) {
    	this.submissionState = submissionState;
    }

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getSubmissionState() {
		return submissionState;
	}
	public void setSubmissionState(String submissionState) {
		this.submissionState = submissionState;
	}
	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public TimeSheet getSheet() {
		return sheet;
	}

	public void setSheet(TimeSheet sheet) {
		this.sheet = sheet;
	}

	@Override
	public String toString() {
		return "TimesheetStatus [statusId=" + statusId + ", submissionState=" + submissionState + ", approverId="
				+ approverId + ", comment=" + comment + ", sheet=" + sheet + "]";
	}

}
