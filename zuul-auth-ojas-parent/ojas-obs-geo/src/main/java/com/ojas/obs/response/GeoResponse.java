package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.Geo;

public class GeoResponse { 
	private String message;
	private String statusCode;
	private List<Geo> geoList;

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

	public List<Geo> getGeoList() {
		return geoList;
	}

	public void setGeoList(List<Geo> geoList) {
		this.geoList = geoList;
	}

	@Override
	public String toString() {
		return "GeoResponse [message=" + message + ", statusCode=" + statusCode + ", geoList=" + geoList + "]";
	}

}
