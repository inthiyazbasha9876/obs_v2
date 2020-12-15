package com.ojas.obs.customerinfo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="obs_billing_info")
public class BillingInfo 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Integer billingId;
	
	@NotNull
	@Column
	private String apcontactName;
	
	@NotNull
	@Column
	private String apEmail;
	
	@NotNull
	@Column
	private Long phoneNumber;
	
	
	@NotNull
	@Column
	private String bdmconatctName;
	
	@NotNull
	@Column
	private String bdmEmail;
	
	@NotNull
	@Column
	private String amconatctName;
	
	@NotNull
	@Column
	private String amEmail;

	@NotNull
	@Column
	private boolean billinginfostatus;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "customerId", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","billinginfo"})
    private Customer customer;

	public Integer getBillingId() {
		return billingId;
	}

	public void setBillingId(Integer billingId) {
		this.billingId = billingId;
	}

	public String getApcontactName() {
		return apcontactName;
	}

	public void setApcontactName(String apcontactName) {
		this.apcontactName = apcontactName;
	}

	public String getApEmail() {
		return apEmail;
	}

	public void setApEmail(String apEmail) {
		this.apEmail = apEmail;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBdmconatctName() {
		return bdmconatctName;
	}

	public void setBdmconatctName(String bdmconatctName) {
		this.bdmconatctName = bdmconatctName;
	}

	public String getBdmEmail() {
		return bdmEmail;
	}

	public void setBdmEmail(String bdmEmail) {
		this.bdmEmail = bdmEmail;
	}

	public String getAmconatctName() {
		return amconatctName;
	}

	public void setAmconatctName(String amconatctName) {
		this.amconatctName = amconatctName;
	}

	public String getAmEmail() {
		return amEmail;
	}

	public void setAmEmail(String amEmail) {
		this.amEmail = amEmail;
	}

	public boolean isBillinginfostatus() {
		return billinginfostatus;
	}

	public void setBillinginfostatus(boolean billinginfostatus) {
		this.billinginfostatus = billinginfostatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
	
	
}
