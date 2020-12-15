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
@Table(name="Obs_Customer_RegisteredAddress")
public class RegisteredAddress 
{
   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Integer Id;
	
	@NotNull
	@Column
	private String citylocation;
	
	@NotNull
	@Column
	private String stateslocation;
	
	@NotNull
	@Column
	private Integer registeredpincode;
	
	@NotNull
	@Column
	private boolean status;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "customerId", nullable = false)	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","registeredaddress"})
	 private Customer customer;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
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

	public Integer getRegisteredpincode() {
		return registeredpincode;
	}

	public void setRegisteredpincode(Integer registeredpincode) {
		this.registeredpincode = registeredpincode;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
	
	
	
}
