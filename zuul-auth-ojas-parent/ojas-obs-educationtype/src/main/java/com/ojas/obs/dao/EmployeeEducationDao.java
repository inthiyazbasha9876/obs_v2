package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ojas.obs.model.EmployeeEducation;
import com.ojas.obs.modelrequest.EmployeeEducationRequest;

/**
 * 
 * @author mpraneethguptha
 *
 */
@Repository
public interface EmployeeEducationDao {
	
	boolean saveEmployeeEducation(EmployeeEducationRequest employeeEducationRequest) throws SQLException;
	
	boolean updateEmployeeEducation(EmployeeEducationRequest employeeEducationRequest) throws SQLException;
	
	boolean deleteEmployeeEducation(int id) throws SQLException;
	
	List<EmployeeEducation> getAllEmployeeEducation(EmployeeEducationRequest employeeEducationRequest) throws SQLException;
	
	
	int getAllRecordsCount() throws SQLException;
	

}
