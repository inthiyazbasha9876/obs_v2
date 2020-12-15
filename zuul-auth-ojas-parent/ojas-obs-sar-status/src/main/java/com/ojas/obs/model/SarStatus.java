package com.ojas.obs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SAR_Status")
public class SarStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer sarstatusId;

	@Column(unique = true)
	private String sarStatus;

	@Column(columnDefinition = "tinyint default true")
	private Boolean status;

	public Integer getSarstatusId() {
		return sarstatusId;
	}

	public void setSarstatusId(Integer sarstatusId) {
		this.sarstatusId = sarstatusId;
	}

	public String getSarStatus() {
		return sarStatus;
	}

	public void setSarStatus(String sarStatus) {
		this.sarStatus = sarStatus;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SarStatus [sarstatusId=" + sarstatusId + ", sarStatus=" + sarStatus + ", status=" + status + "]";
	}
}