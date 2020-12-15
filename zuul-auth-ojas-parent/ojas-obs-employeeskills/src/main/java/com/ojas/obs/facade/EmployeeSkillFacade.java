package com.ojas.obs.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.model.EmployeeSkillInfoRequest;

public interface EmployeeSkillFacade {

	
	ResponseEntity<Object> setEmployeeSkillInfo(EmployeeSkillInfoRequest employeeSkillInfoRequest) throws SQLException ;
	
	ResponseEntity<Object> getEmployeeSkillInfo(EmployeeSkillInfoRequest employeeSkillInfoRequest) throws SQLException;
}
