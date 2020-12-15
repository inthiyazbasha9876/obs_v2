package com.ojas.obs.regforgot.model;

import java.sql.Timestamp;

public class ForgotPassword {
	private String employeeId;
	private String emailId;
	private String newPassword;
	private Integer otp;
	private Timestamp expTime;
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmailId() {
		return emailId;
	}
	
	
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Integer getOtp() {
		return otp;
	}
	public void setOtp(Integer otp) {
		this.otp = otp;
	}
	public Timestamp getExpTime() {
		return expTime;
	}
	public void setExpTime(Timestamp expTime) {
		this.expTime = expTime;
	}
	@Override
	public String toString() {
		return "ForgotPassword [employeeId=" + employeeId + ", emailId=" + emailId + ", newPassword=" + newPassword
				+ ", otp=" + otp + ", expTime=" + expTime + "]";
	}
	
}
