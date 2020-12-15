package com.ojas.obs.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.request.InsuranceRequest;


public interface InsuranceFacade {

	public ResponseEntity<Object> saveInsuranceDetails(InsuranceRequest insuranceRequest) throws SQLException;
	
	public ResponseEntity<Object> getAllInsuranceDetails(InsuranceRequest insuranceRequest) throws SQLException;
	
}
