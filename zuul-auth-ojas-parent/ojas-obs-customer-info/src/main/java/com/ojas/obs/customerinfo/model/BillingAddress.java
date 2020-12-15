package com.ojas.obs.customerinfo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="obs_billingaddress")
public class BillingAddress 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Integer billingaddressId;

	@NotNull
	@Column
	private String billingaddressLine1;
	
	@NotNull
	@Column
	private String billingaddressLine2;
	
	@NotNull
	@Column
	private Integer pincode;
	
	@NotNull
	@Column
	private String citylocation;
	
	@NotNull
	@Column
	private String stateslocation;
	
	
	@Column
	private boolean defaultaddressstatus;
	
	
	
	
	@NotNull
	@Column
	private boolean status;

	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","billingaddress"})
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "billingaddress")
    private Set<Customer> customer = new HashSet<>();


	public Integer getBillingaddressId() {
		return billingaddressId;
	}


	public void setBillingaddressId(Integer billingaddressId) {
		this.billingaddressId = billingaddressId;
	}


	public String getBillingaddressLine1() {
		return billingaddressLine1;
	}


	public void setBillingaddressLine1(String billingaddressLine1) {
		this.billingaddressLine1 = billingaddressLine1;
	}


	public String getBillingaddressLine2() {
		return billingaddressLine2;
	}


	public void setBillingaddressLine2(String billingaddressLine2) {
		this.billingaddressLine2 = billingaddressLine2;
	}


	public Integer getPincode() {
		return pincode;
	}


	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}


	public String getCitylocation() {
		return citylocation;
	}


	public void setCitylocation(String citylocation) {
		this.citylocation = citylocation;
	}



	public String getStateslocation() {
		return stateslocation;
	}


	public void setStateslocation(String stateslocation) {
		this.stateslocation = stateslocation;
	}


	public boolean isDefaultaddressstatus() {
		return defaultaddressstatus;
	}


	public void setDefaultaddressstatus(boolean defaultaddressstatus) {
		this.defaultaddressstatus = defaultaddressstatus;
	}


	


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public Set<Customer> getCustomer() {
		return customer;
	}


	public void setCustomer(Set<Customer> customer) {
		this.customer = customer;
	}


	

	
}
