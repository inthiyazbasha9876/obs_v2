package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.LocationType;

public class LocationTypeRequest {
	private List<LocationType> locationTypeList;
	private String transactionType;

	public List<LocationType> getLocationTypeList() {
		return locationTypeList;
	}

	public void setLocationTypeList(List<LocationType> locationTypeList) {
		this.locationTypeList = locationTypeList;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "LocationTypeRequest [locationTypeList=" + locationTypeList + ", transactionType=" + transactionType
				+ "]";
	}
}
