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
@Table(name="obs_contact_info")
public class ContactInfo
{

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Integer contactId;

	
	@NotNull
	@Column
	private String contactName;
	

	
	@NotNull
	@Column
	private boolean contactinfostatus;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "customerId", nullable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","contactinfo"})
    private Customer customer;

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}



	public boolean isContactinfostatus() {
		return contactinfostatus;
	}

	public void setContactinfostatus(boolean contactinfostatus) {
		this.contactinfostatus = contactinfostatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	 
	

	

}
