package com.ojas.obs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Delivery_Location")
public class DeliveryLocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer deliverylocationId;
	
	@Column
	private Integer stateid;
	

	@Column(unique = true)
	private String deliverylocationName;

	@Column(columnDefinition = "tinyint default true")
	private Boolean status;

	public Integer getDeliverylocationId() {
		return deliverylocationId;
	}

	public void setDeliverylocationId(Integer deliverylocationId) {
		this.deliverylocationId = deliverylocationId;
	}

	public String getDeliverylocationName() {
		return deliverylocationName;
	}

	public void setDeliverylocationName(String deliverylocationName) {
		this.deliverylocationName = deliverylocationName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Integer getStateid() {
		return stateid;
	}

	public void setStateid(Integer stateid) {
		this.stateid = stateid;
	}

	@Override
	public String toString() {
		return "DeliveryLocation [deliverylocationId=" + deliverylocationId + ", stateid=" + stateid
				+ ", deliverylocationName=" + deliverylocationName + ", status=" + status + "]";
	}



}
