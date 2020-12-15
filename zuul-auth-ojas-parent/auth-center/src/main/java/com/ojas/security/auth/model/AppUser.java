package com.ojas.security.auth.model;

public class AppUser {

	private String UserId;
	private String encrytedPassword;
	private boolean pwdChanged;

	public boolean isPwdChanged() {
		return pwdChanged;
	}

	public void setPwdChanged(boolean pwdChanged) {
		this.pwdChanged = pwdChanged;
	}

	public AppUser() {

	}

	public AppUser(String userId, String encrytedPassword, boolean pwdChanged) {
		super();
		UserId = userId;
		this.encrytedPassword = encrytedPassword;
		this.pwdChanged = pwdChanged;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getEncrytedPassword() {
		return encrytedPassword;
	}

	public void setEncrytedPassword(String encrytedPassword) {
		this.encrytedPassword = encrytedPassword;
	}

}
