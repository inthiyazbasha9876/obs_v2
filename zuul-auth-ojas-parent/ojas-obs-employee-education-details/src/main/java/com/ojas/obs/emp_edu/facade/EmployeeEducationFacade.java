package com.ojas.obs.emp_edu.facade;

import java.sql.SQLException;
import org.springframework.http.ResponseEntity;
import com.ojas.obs.emp_edu.model.EmployeeEducationDetailsRequest;
public interface EmployeeEducationFacade {

	ResponseEntity<Object> setEmployeeEducationDetails(EmployeeEducationDetailsRequest emplEduDetailsRequestObj) throws SQLException;


	ResponseEntity<Object> getEmployeeEducationDetails(EmployeeEducationDetailsRequest emplEduDetailsRequestObj) throws SQLException;

}
