package com.ojas.security.auth.service.impl;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User{

	public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	private boolean pwdChanged;
	public boolean isPwdChanged() {
		return pwdChanged;
	}
	public void setPwdChanged(boolean pwdChanged) {
		this.pwdChanged = pwdChanged;
	}
}
