package com.ojas.obs.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.requests.EmployeeContactInfoRequest;

public interface EmployeeContactFacade {

	ResponseEntity<Object> setEmployeeContactInfo(EmployeeContactInfoRequest empRequest) throws SQLException;

	ResponseEntity<Object> getEmployeeContactInfo(EmployeeContactInfoRequest empRequest) throws SQLException;

}
