package com.ojas.obs.contractcompany.request;

import java.util.List;

import com.ojas.obs.contractcompany.model.ContractCompany;

public class ContractCompanyRequest {

	private List<ContractCompany> companyList;
	private String transactionType;

	public List<ContractCompany> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<ContractCompany> companyList) {
		this.companyList = companyList;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "ContractCompanyRequest [companyList=" + companyList + ", transactionType=" + transactionType + "]";
	}

}
