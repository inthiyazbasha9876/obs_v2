package com.ojas.obs.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.request.GenderRequest;


public interface GenderFacade {
	ResponseEntity<Object> getGender(GenderRequest genderRequest) throws SQLException;

	ResponseEntity<Object> setGender(GenderRequest genderRequest)throws SQLException;
}
