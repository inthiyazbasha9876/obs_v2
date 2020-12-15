package com.ojas.obs.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.request.DesignationRequest;

/**
 * 
 * @author nsrikanth
 *
 */

public interface DesignationFacade {

	public ResponseEntity<Object> setDesignation(DesignationRequest designationRequest) throws SQLException;

	ResponseEntity<Object> getDesignation(DesignationRequest request) throws SQLException;

	//public ResponseEntity<Object> deleteDesignation(DesignationRequest designationRequest) throws Exception;

}
