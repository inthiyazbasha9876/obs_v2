package com.ojas.obs.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.request.KYERequest;

/**
 * 
 * @author tshiva
 *
 */
public interface KyeFacade {
	ResponseEntity<Object> setKYE(KYERequest kyeRequest);

	ResponseEntity<Object> getKYE(KYERequest kyeRequest) throws SQLException;
	
	// ResponseEntity<Object> sendMail(KYERequest kyeRequest) throws SQLException;

}