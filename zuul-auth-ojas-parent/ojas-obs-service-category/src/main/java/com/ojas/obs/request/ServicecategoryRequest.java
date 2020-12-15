package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.ServiceCategory;


public class ServicecategoryRequest 
{

	private List<ServiceCategory> servicecategoryList;
	private String transactionType;
	
	public List<ServiceCategory> getServicecategoryList() {
		return servicecategoryList;
	}
	public void setServicecategoryList(List<ServiceCategory> servicecategoryList) {
		this.servicecategoryList = servicecategoryList;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "CmsRequest [servicecategoryList=" + servicecategoryList + ", transactionType=" + transactionType + "]";
	}

}
