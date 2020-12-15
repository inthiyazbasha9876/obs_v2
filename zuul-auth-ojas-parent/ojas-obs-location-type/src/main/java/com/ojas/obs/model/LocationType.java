package com.ojas.obs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "location_type")
public class LocationType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer locationtypeId;

	@Column(unique = true)
	private String locationType;

	@Column(columnDefinition = "tinyint default true")
	private Boolean status;

	public Integer getLocationtypeId() {
		return locationtypeId;
	}

	public void setLocationtypeId(Integer locationtypeId) {
		this.locationtypeId = locationtypeId;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "LocationType [locationtypeId=" + locationtypeId + ", locationType=" + locationType + ", status="
				+ status + "]";
	}

}