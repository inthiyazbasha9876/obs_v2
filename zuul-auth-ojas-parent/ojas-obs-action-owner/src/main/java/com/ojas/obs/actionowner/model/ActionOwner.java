 package com.ojas.obs.actionowner.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ActionOwner")
public class ActionOwner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer actionownerId;

	@Column(unique = true)
	private String actionowner;

	@Column(columnDefinition = "tinyint default true")
	private Boolean status;

	public Integer getActionownerId() {
		return actionownerId;
	}

	public void setActionownerId(Integer actionownerId) {
		this.actionownerId = actionownerId;
	}

	public String getActionowner() {
		return actionowner;
	}

	public void setActionowner(String actionowner) {
		this.actionowner = actionowner;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ActionOwner [actionownerId=" + actionownerId + ", actionowner=" + actionowner + ", status=" + status
				+ "]";
	}

	
}
