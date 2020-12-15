package com.ojas.obs.country.response;

import java.util.List;

import com.ojas.obs.country.model.Country;

public class CountryResponse {
	private String message;
	private String statusCode;
	private List<Country> countrylist;

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

	public List<Country> getCountrylist() {
		return countrylist;
	}

	public void setCountrylist(List<Country> countrylist) {
		this.countrylist = countrylist;
	}

	@Override
	public String toString() {
		return "CountryResponse [message=" + message + ", statusCode=" + statusCode + ", countrylist=" + countrylist
				+ "]";
	}

}