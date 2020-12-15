package com.ojas.obs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Sez_Location")
public class SezLocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer sezlocationId;

	@Column(unique = true)
	private String sezlocationName;

	@Column(columnDefinition = "tinyint default true")
	private Boolean status;

	public Integer getSezlocationId() {
		return sezlocationId;
	}

	public void setSezlocationId(Integer sezlocationId) {
		this.sezlocationId = sezlocationId;
	}

	public String getSezlocationName() {
		return sezlocationName;
	}

	public void setSezlocationName(String sezlocationName) {
		this.sezlocationName = sezlocationName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SezLocation [sezlocationId=" + sezlocationId + ", sezlocationName=" + sezlocationName + ", status="
				+ status + "]";
	}

	

}
