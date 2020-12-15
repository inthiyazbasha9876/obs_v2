package com.ojas.obs.model;

import java.util.List;

public class CostCenterResponse {

	private List<CostCenter> listOfCostCenter;
	private String message;
	private String statusCode;

	public List<CostCenter> getListOfCostCenter() {
		return listOfCostCenter;
	}

	public void setListOfCostCenter(List<CostCenter> listOfCostCenter) {
		this.listOfCostCenter = listOfCostCenter;
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

	@Override
	public String toString() {
		return "CostCenterResponse [listOfCostCenter=" + listOfCostCenter + ", message=" + message + ", statusCode="
				+ statusCode + "]";
	}
}
