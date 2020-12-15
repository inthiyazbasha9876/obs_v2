package com.ojas.obs.resourcetype.model;

import java.util.List;

public class ResourceTypeRequest {

	private List<ResourceType> resourceTypes;

	private String transactionType;

	public List<ResourceType> getResourceTypes() {
		return resourceTypes;
	}

	public void setResourceTypes(List<ResourceType> resourceTypes) {
		this.resourceTypes = resourceTypes;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

}
