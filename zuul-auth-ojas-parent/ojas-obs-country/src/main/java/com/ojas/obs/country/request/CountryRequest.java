package com.ojas.obs.country.request;

import java.util.List;

import com.ojas.obs.country.model.Country;

public class CountryRequest {
	private List<Country> countrylist;
	private String transactionType;

	public List<Country> getCountrylist() {
		return countrylist;
	}

	public void setCountrylist(List<Country> countrylist) {
		this.countrylist = countrylist;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "CountryRequest [countrylist=" + countrylist + ", transactionType=" + transactionType + "]";
	}

}
