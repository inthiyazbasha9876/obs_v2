package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.LocationType;

public class LocationTypeResponse {
	private String message;
	private String statusCode;
	private List<LocationType> locationTypeList;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public List<LocationType> getLocationTypeList() {
		return locationTypeList;
	}

	public void setLocationTypeList(List<LocationType> locationTypeList) {
		this.locationTypeList = locationTypeList;
	}

	@Override
	public String toString() {
		return "LocationTypeResponse [message=" + message + ", statusCode=" + statusCode + ", locationTypeList="
				+ locationTypeList + "]";
	}
}
