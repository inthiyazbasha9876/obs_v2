package com.obs.businessunit.response;

import java.util.List;

import com.obs.businessunit.model.BusinessUnit;

public class BusinessUnitResponse {
	private List<BusinessUnit> businessUnitList;
	private String message;
	private String statusCode;
	private List<String> buHeads;
	
	
	public List<String> getBuHeads() {
		return buHeads;
	}
	public void setBuHeads(List<String> buHeads) {
		this.buHeads = buHeads;
	}
	public List<BusinessUnit> getBusinessUnitList() {
		return businessUnitList;
	}
	public void setBusinessUnitList(List<BusinessUnit> businessUnitList) {
		this.businessUnitList = businessUnitList;
	}
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

}
