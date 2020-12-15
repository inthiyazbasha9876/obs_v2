package com.ojas.obs.resetpassword.request;

import com.ojas.obs.resetpassword.model.ResetPassword;

public class ResetPasswordRequest {
	private ResetPassword pwd;
	private String transactionType;
	

	public ResetPassword getPwd() {
		return pwd;
	}
	public void setPwd(ResetPassword pwd) {
		this.pwd = pwd;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	@Override
	public String toString() {
		return "ResetPasswordRequest [pwd=" + pwd + ", transactionType=" + transactionType + "]";
	}
	
	
}
