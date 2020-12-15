package com.ojas.obs.model;

import java.sql.Timestamp;

/**
 * 
 * @author sdileep
 *
 */
public class EmployeeInfo {

	private Integer id;
	private String firstname;
	private String middlename;
	private String lastname;
	private String status;
	private String dob;
	private String gender;
	private String title;
	private String reportingManager;
	private String employeeId;
	private boolean flag;
	//private String statusDate;
	private String password;
	private Integer role;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	private String createdBy;
	private String updatedBy;
	private String officialEmail;
	private String email;
	private String personalMobileNo;
	private String image;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReportingManager() {
		return reportingManager;
	}

	public void setReportingManager(String reportingManager) {
		this.reportingManager = reportingManager;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/*
	 * public String getStatusDate() { return statusDate; }
	 * 
	 * public void setStatusDate(String statusDate) { this.statusDate = statusDate;
	 * }
	 */

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
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

	public String getOfficialEmail() {
		return officialEmail;
	}

	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPersonalMobileNo() {
		return personalMobileNo;
	}

	public void setPersonalMobileNo(String personalMobileNo) {
		this.personalMobileNo = personalMobileNo;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "EmployeeInfo [id=" + id + ", firstname=" + firstname + ", middlename=" + middlename + ", lastname="
				+ lastname + ", status=" + status + ", dob=" + dob + ", gender=" + gender + ", title=" + title
				+ ", reportingManager=" + reportingManager + ", employeeId=" + employeeId + ", flag=" + flag
				+ ", password=" + password + ", role=" + role
				+ ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", createdBy=" + createdBy + ", updatedBy="
				+ updatedBy + ", officialEmail=" + officialEmail + ", email=" + email + ", personalMobileNo="
				+ personalMobileNo + ", image=" + image + "]";
	}
	
	

	}