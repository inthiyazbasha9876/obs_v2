package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.ServiceType;

public class ServiceTypeRequest {
	private List<ServiceType> servicetypeList;
	private String transactionType;

	public List<ServiceType> getServicetypeList() {
		return servicetypeList;
	}

	public void setServicetypeList(List<ServiceType> servicetypeList) {
		this.servicetypeList = servicetypeList;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public String toString() {
		return "ServiceTypeRequest [servicetypeList=" + servicetypeList + ", transactionType=" + transactionType + "]";
	}
}
