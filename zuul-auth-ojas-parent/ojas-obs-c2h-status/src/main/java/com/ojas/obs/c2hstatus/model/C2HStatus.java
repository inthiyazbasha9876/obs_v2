package com.ojas.obs.c2hstatus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "C2H_Status")
public class C2HStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer c2hstatusId;

	@Column(unique = true)
	private String c2hstatus;

	@Column(columnDefinition = "tinyint default true")
	private Boolean status;

	public Integer getC2hstatusId() {
		return c2hstatusId;
	}

	public void setC2hstatusId(Integer c2hstatusId) {
		this.c2hstatusId = c2hstatusId;
	}

	public String getC2hstatus() {
		return c2hstatus;
	}

	public void setC2hstatus(String c2hstatus) {
		this.c2hstatus = c2hstatus;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "C2HStatus [c2hstatusId=" + c2hstatusId + ", c2hstatus=" + c2hstatus + ", status=" + status + "]";
	}

	

}
