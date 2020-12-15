package com.ojas.obs.resetpassword.facade;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.resetpassword.request.ResetPasswordRequest;

public interface ResetPasswordFacade {
	public ResponseEntity<Object> setPassword(ResetPasswordRequest resetPasswordRequest) throws SQLException;
	
	public ResponseEntity<Object> savePassword(ResetPasswordRequest resetPasswordRequest) throws SQLException, AddressException, MessagingException;
}
