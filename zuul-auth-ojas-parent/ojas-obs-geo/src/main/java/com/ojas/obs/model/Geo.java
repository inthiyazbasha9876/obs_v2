package com.ojas.obs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Geo")
public class Geo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer geoId;
	@Column(unique = true)
	private String geoname;

	@Column(columnDefinition = "tinyint default true")
	private Boolean status;

	public Integer getGeoId() {
		return geoId;
	}

	public void setGeoId(Integer geoId) {
		this.geoId = geoId;
	}

	public String getGeoname() {
		return geoname;
	}

	public void setGeoname(String geoname) {
		this.geoname = geoname;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Geo [geoId=" + geoId + ", geoname=" + geoname + ", status=" + status + "]";
	}

}
