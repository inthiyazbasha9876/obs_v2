package com.obs.businessunit.request;

import java.util.List;

import com.obs.businessunit.model.BusinessUnit;

public class BusinessUnitRequest { 
	private List<BusinessUnit> businessUnit;
	private String transactionType;
	public List<BusinessUnit> getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(List<BusinessUnit> businessUnit) {
		this.businessUnit = businessUnit;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "BusinessUnitRequest [businessUnit=" + businessUnit + ", transactionType=" + transactionType + "]";
	}


}
