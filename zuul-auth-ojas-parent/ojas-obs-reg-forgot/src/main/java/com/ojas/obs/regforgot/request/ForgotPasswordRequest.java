package com.ojas.obs.regforgot.request;

import com.ojas.obs.regforgot.model.ForgotPassword;

public class ForgotPasswordRequest {
	private ForgotPassword forgotPassword;
	private String transactionType;
	
	public ForgotPassword getForgotPassword() {
		return forgotPassword;
	}
	public void setForgotPassword(ForgotPassword forgotPassword) {
		this.forgotPassword = forgotPassword;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "ForgotPasswordRequest [forgotPassword=" + forgotPassword
				+ ", transactionType=" + transactionType + "]";
	}
	
}
