package com.obs.lms.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "leave_Info")
public class LeaveInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer id;
	@Column
	private String empId;
	@Column
	private String leaveType;
	@Column
	private String fromDate;
	@Column
	private String toDate;
	@Column
	private String session1;
	@Column
	private String session2;
	@Column
	private Float countNumOfDays;
	@Column
	private String applyType;
	@Column
	private String applyTo;
	@Column
	private String leaveReason;
	@Column
	private Long contactDetails;
	@Column
	private String filePath;
	@Transient
	private String attachment;
	@Transient
	private String fileName;
	@Column
	private String ccTo;
	@Column
	private String comment;
	@Column
	private Boolean flag;
	@Column
	private String status;
	@Column
	private String updatedBy;
	@Column
	private String appliedOn;
	@Column
	private String updatedOn;

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getSession1() {
		return session1;
	}

	public void setSession1(String session1) {
		this.session1 = session1;
	}

	public String getSession2() {
		return session2;
	}

	public void setSession2(String session2) {
		this.session2 = session2;
	}

	public Float getCountNumOfDays() {
		return countNumOfDays;
	}

	public void setCountNumOfDays(Float countNumOfDays) {
		this.countNumOfDays = countNumOfDays;
	}

	public String getApplyTo() {
		return applyTo;
	}

	public void setApplyTo(String applyTo) {
		this.applyTo = applyTo;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	public Long getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(Long contactDetails) {
		this.contactDetails = contactDetails;
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

	public String getCcTo() {
		return ccTo;
	}

	public void setCcTo(String ccTo) {
		this.ccTo = ccTo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getAppliedOn() {
		return appliedOn;
	}

	public void setAppliedOn(String appliedOn) {
		this.appliedOn = appliedOn;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return "LeaveInfo [id=" + id + ", empId=" + empId + ", leaveType=" + leaveType + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", session1=" + session1 + ", session2=" + session2 + ", countNumOfDays="
				+ countNumOfDays + ", applyType=" + applyType + ", applyTo=" + applyTo + ", leaveReason=" + leaveReason
				+ ", contactDetails=" + contactDetails + ", filePath=" + filePath + ", attachment=" + attachment
				+ ", fileName=" + fileName + ", ccTo=" + ccTo + ", comment=" + comment + ", flag=" + flag + ", status="
				+ status + ", updatedBy=" + updatedBy + ", appliedOn=" + appliedOn + ", updatedOn=" + updatedOn + "]";
	}

}