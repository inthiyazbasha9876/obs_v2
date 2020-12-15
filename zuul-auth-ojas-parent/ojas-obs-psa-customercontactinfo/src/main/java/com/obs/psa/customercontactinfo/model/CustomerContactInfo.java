package com.obs.psa.customercontactinfo.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "Customer_ContactInfo")
public class CustomerContactInfo  {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="contact_id")
	private Integer contactId;
	
	@Column
	private int customerId;
	
	@Column
	private String contactName;
	
	@Column
	private String designation;
	
	@Column
	private String department;
	
	@Column
	private Long permanentMobileNumber;
	
	@Column
	private Long alternateMobileNumber;
	
	@Column
	private String personalEmail;
	
	@Column
	private String officialEmail;
	
	@Column
	private String bdm;
	
	@Column
	private String dob;
	
	@Column
	private String doa;
	
	@Column(columnDefinition = "tinyint default true")
	private Boolean status;
	
	@Column
	private String state;
	
	@Column
	private int pincode;
	
	@Column
	private String address1;
	
	@Column
	private String address2;
	
	@Column
	private String address3;

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Long getPermanentMobileNumber() {
		return permanentMobileNumber;
	}

	public void setPermanentMobileNumber(Long permanentMobileNumber) {
		this.permanentMobileNumber = permanentMobileNumber;
	}

	public Long getAlternateMobileNumber() {
		return alternateMobileNumber;
	}

	public void setAlternateMobileNumber(Long alternateMobileNumber) {
		this.alternateMobileNumber = alternateMobileNumber;
	}

	public String getPersonalEmail() {
		return personalEmail;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	public String getOfficialEmail() {
		return officialEmail;
	}

	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}

	public String getBdm() {
		return bdm;
	}

	public void setBdm(String bdm) {
		this.bdm = bdm;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDoa() {
		return doa;
	}

	public void setDoa(String doa) {
		this.doa = doa;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	@Override
	public String toString() {
		return "CustomerContactInfo [contactId=" + contactId + ", customerId=" + customerId + ", contactName="
				+ contactName + ", designation=" + designation + ", department=" + department
				+ ", permanentMobileNumber=" + permanentMobileNumber + ", alternateMobileNumber="
				+ alternateMobileNumber + ", personalEmail=" + personalEmail + ", officialEmail=" + officialEmail
				+ ", bdm=" + bdm + ", dob=" + dob + ", doa=" + doa + ", status=" + status + ", state=" + state
				+ ", pincode=" + pincode + ", address1=" + address1 + ", address2=" + address2 + ", address3="
				+ address3 + "]";
	}
	




	

	
	
	
	
}
