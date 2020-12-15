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
@Table(name="Obs_Customer_GST")
public class CustomerGst 
{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Integer id;
	
	@NotNull
	@Column
	private String location;
	
	@NotNull
	@Column
	private boolean gst;
	
	
	@Column
	private String gstnumber;
	
	@NotNull
	@Column
	private boolean issez;
	
	@NotNull
	@Column
	private boolean customergststatus;

	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "customerId", nullable = false)	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","customergst"})
	 private Customer customer;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isGst() {
		return gst;
	}

	public void setGst(boolean gst) {
		this.gst = gst;
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

	public boolean isCustomergststatus() {
		return customergststatus;
	}

	public void setCustomergststatus(boolean customergststatus) {
		this.customergststatus = customergststatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
	
}
