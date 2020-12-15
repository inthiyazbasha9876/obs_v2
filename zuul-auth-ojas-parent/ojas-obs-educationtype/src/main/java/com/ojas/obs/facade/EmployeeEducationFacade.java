package com.ojas.obs.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.modelrequest.EmployeeEducationRequest;

/**
 * 
 * @author mpraneethguptha
 *
 */
public interface EmployeeEducationFacade {
	public ResponseEntity<Object> setEmployeeEducationInfo(EmployeeEducationRequest employeeEducationRequest) throws SQLException;

	public ResponseEntity<Object> getEmployeeEducationInfo(EmployeeEducationRequest employeeEducationRequest) throws SQLException;

}
