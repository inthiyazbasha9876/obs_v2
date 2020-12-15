package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.Geo;

public class GeoRequest {
	private List<Geo> geoList;
	private String transactionType;

	public List<Geo> getGeoList() {
		return geoList;
	}

	public void setGeoList(List<Geo> geoList) {
		this.geoList = geoList;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "GeoRequest [geoList=" + geoList + ", transactionType=" + transactionType + "]";
	}

}
