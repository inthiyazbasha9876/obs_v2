package com.ojas.obs.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.request.EmployeeInfoRequest;

/**
 * 
 * @author sdileep
 *
 */
public interface EmployeeInfoFacade {
	/**
	 * 
	 * @param employeeInfoRequest
	 * @return
	 * @throws SQLException
	 */
	public ResponseEntity<Object> setEmployeeInfo(EmployeeInfoRequest employeeInfoRequest) throws SQLException;

	/**
	 * 
	 * @param employeeInfoRequest
	 * @return
	 * @throws SQLException
	 */
	public ResponseEntity<Object> getAllEmployeeDetails(EmployeeInfoRequest employeeInfoRequest) throws SQLException;
}
