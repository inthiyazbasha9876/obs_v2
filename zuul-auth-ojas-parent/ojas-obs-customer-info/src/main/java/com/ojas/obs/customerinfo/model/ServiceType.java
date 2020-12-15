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
@Table(name="Obs_Customer_CustomerServiceType")
public class ServiceType 
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Integer id;
	
	@Column
	private String servicetypeid;
	
	@NotNull
	@Column
	private boolean servicestatus;
	
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","servicetype"})
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "servicetype")
    private Set<Customer> customer = new HashSet<>();


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getServicetypeid() {
		return servicetypeid;
	}


	public void setServicetypeid(String servicetypeid) {
		this.servicetypeid = servicetypeid;
	}


	public boolean isServicestatus() {
		return servicestatus;
	}


	public void setServicestatus(boolean servicestatus) {
		this.servicestatus = servicestatus;
	}


	public Set<Customer> getCustomer() {
		return customer;
	}


	public void setCustomer(Set<Customer> customer) {
		this.customer = customer;
	}


	
	
	
	
}
