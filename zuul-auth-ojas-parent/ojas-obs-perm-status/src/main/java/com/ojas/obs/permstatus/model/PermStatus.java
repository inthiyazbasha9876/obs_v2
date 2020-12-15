package com.ojas.obs.permstatus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Permstatus")
public class PermStatus {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer permstatusId;
	
	@Column(unique = true)
	private String permstatus;

	@Column(columnDefinition = "tinyint default true")
	private Boolean status;

	public Integer getPermstatusId() {
		return permstatusId;
	}

	public void setPermstatusId(Integer permstatusId) {
		this.permstatusId = permstatusId;
	}

	public String getPermstatus() {
		return permstatus;
	}

	public void setPermstatus(String permstatus) {
		this.permstatus = permstatus;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PermStatus [permstatusId=" + permstatusId + ", permstatus=" + permstatus + ", status=" + status + "]";
	}

	
}
