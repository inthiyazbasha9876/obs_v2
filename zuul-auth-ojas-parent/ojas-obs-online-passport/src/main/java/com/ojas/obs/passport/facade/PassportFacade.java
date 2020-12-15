package com.ojas.obs.passport.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.passport.Request.PassportRequest;

public interface PassportFacade {
	
	public ResponseEntity<Object>  setPassport(PassportRequest passportRequestObject) throws SQLException;

	public ResponseEntity<Object> getPassport(PassportRequest passportRequestObject) throws SQLException;

}
