package com.ojas.obs.regforgot.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.regforgot.request.ForgotPasswordRequest;

public interface ForgotPasswordFacade {
	public ResponseEntity<Object> setForgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws SQLException;
}
