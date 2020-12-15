package com.ojas.obs.facade;

import java.sql.SQLException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.request.EmployeeStatusRequest;
/**
 * 
 * @author Manohar
 *
 */
public interface EmployeeStatusFacade {
	public ResponseEntity<Object> setEmployeeStatus(EmployeeStatusRequest employeeStatusRequest) throws SQLException,DuplicateKeyException;
	public ResponseEntity<Object> getEmployeeStatus(EmployeeStatusRequest employeeStatusRequest) throws SQLException;

}
