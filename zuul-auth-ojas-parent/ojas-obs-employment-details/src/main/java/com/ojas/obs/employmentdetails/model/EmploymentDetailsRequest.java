package com.ojas.obs.employmentdetails.model;

import java.util.List;

public class EmploymentDetailsRequest {

	private List<EmploymentDetails> employmentDetails;

	private String transactionType;

	private int pageNo;

	private int pageSize;

	public List<EmploymentDetails> getEmploymentDetails() {
		return employmentDetails;
	}

	public void setEmploymentDetails(List<EmploymentDetails> employmentDetails) {
		this.employmentDetails = employmentDetails;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
