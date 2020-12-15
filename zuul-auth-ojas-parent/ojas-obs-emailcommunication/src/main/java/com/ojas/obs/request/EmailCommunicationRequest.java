package com.ojas.obs.request;

import java.util.List;

import com.ojas.obs.model.Email;

public class EmailCommunicationRequest {
	private List<Email> email;
	private String transactionType;

	public List<Email> getEmail() {
		return email;

	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public void setEmail(List<Email> email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "EmailCommunicationRequest [email=" + email + ", transactionType=" + transactionType + "]";
	}

}