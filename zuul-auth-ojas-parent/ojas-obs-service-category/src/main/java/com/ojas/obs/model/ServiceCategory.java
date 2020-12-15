package com.ojas.obs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Service_Category")
public class ServiceCategory 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer serviceategoryId;

	@Column(unique = true)
	private String serviceategoryName;
	
	@Column(columnDefinition = "tinyint default true")
	private Boolean serviceStatus;

	public Integer getServiceategoryId() {
		return serviceategoryId;
	}

	public void setServiceategoryId(Integer serviceategoryId) {
		this.serviceategoryId = serviceategoryId;
	}

	public String getServiceategoryName() {
		return serviceategoryName;
	}

	public void setServiceategoryName(String serviceategoryName) {
		this.serviceategoryName = serviceategoryName;
	}

	public Boolean getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(Boolean serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	@Override
	public String toString() {
		return "ServiceCategory [serviceategoryId=" + serviceategoryId + ", serviceategoryName=" + serviceategoryName
				+ ", serviceStatus=" + serviceStatus + "]";
	}

	

	
}
