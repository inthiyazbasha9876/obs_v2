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
@Table(name="obs_shippingaddress")
public class ShippingAddress 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Integer shippingaddressId;

	@NotNull
	@Column
	private String shippingaddressLine1;
	
	
	@Column
	private String shippingaddressLine2;
	
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
	private boolean issez;
	
	@NotNull
	@Column
	private String gstlocation;
	
	
	@Column
	private String gstnumber;
	
	
	@NotNull
	@Column
	private boolean status;
	

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","shippingaddress"})
		@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "shippingaddress")
	    private Set<Customer> customer = new HashSet<>();


	public Integer getShippingaddressId() {
		return shippingaddressId;
	}


	public void setShippingaddressId(Integer shippingaddressId) {
		this.shippingaddressId = shippingaddressId;
	}


	public String getShippingaddressLine1() {
		return shippingaddressLine1;
	}


	public void setShippingaddressLine1(String shippingaddressLine1) {
		this.shippingaddressLine1 = shippingaddressLine1;
	}


	public String getShippingaddressLine2() {
		return shippingaddressLine2;
	}


	public void setShippingaddressLine2(String shippingaddressLine2) {
		this.shippingaddressLine2 = shippingaddressLine2;
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


	

	public String getGstnumber() {
		return gstnumber;
	}


	public void setGstnumber(String gstnumber) {
		this.gstnumber = gstnumber;
	}


	public boolean isIssez() {
		return issez;
	}


	public void setIssez(boolean issez) {
		this.issez = issez;
	}


	public String getGstlocation() {
		return gstlocation;
	}


	public void setGstlocation(String gstlocation) {
		this.gstlocation = gstlocation;
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
