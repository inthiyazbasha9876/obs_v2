package com.ojas.obs.contractcompany.response;

import java.util.List;

import com.ojas.obs.contractcompany.model.ContractCompany;

public class ContractCompanyResponse {
	private String message;
	private String statusCode;
	private List<ContractCompany> comapnayList;

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

	public List<ContractCompany> getComapnayList() {
		return comapnayList;
	}

	public void setComapnayList(List<ContractCompany> comapnayList) {
		this.comapnayList = comapnayList;
	}

	@Override
	public String toString() {
		return "ContractCompanyResponse [message=" + message + ", statusCode=" + statusCode + ", comapnayList="
				+ comapnayList + "]";
	}

}