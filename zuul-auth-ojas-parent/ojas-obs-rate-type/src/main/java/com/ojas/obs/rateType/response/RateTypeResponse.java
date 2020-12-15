package com.ojas.obs.rateType.response;

import java.util.List;

import com.ojas.obs.rateType.model.RateType;

public class RateTypeResponse {
	private List<RateType> rateTypeList;
	private String message;
	private String statusCode;

	public List<RateType> getRateTypeList() {
		return rateTypeList;
	}

	public void setRateTypeList(List<RateType> rateTypeList) {
		this.rateTypeList = rateTypeList;
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
		return "RateTypeResponse [rateTypeList=" + rateTypeList + ", message=" + message + ", statusCode=" + statusCode
				+ "]";
	}

}
