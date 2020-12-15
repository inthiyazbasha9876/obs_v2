package com.ojas.obs.employmentdetails.dao;

import java.util.List;

import com.ojas.obs.employmentdetails.exception.DataNotInsertedException;
import com.ojas.obs.employmentdetails.model.EmploymentDetails;
import com.ojas.obs.employmentdetails.model.EmploymentDetailsRequest;

public interface EmploymentDetailsDAO {
	boolean saveEmploymentDetails(List<EmploymentDetails> employmentDetails) throws DataNotInsertedException;

	boolean updateEmploymentDetails(List<EmploymentDetails> employmentDetailsList) throws DataNotInsertedException;

	boolean deleteEmploymentDetails(List<EmploymentDetails> employmentDetailsList) throws DataNotInsertedException;

	List<EmploymentDetails> getAllEmploymentDetails();

	List<EmploymentDetails> getEmploymentDetailsByEmploymentId(EmploymentDetailsRequest employmentDetailsRequest);
	
	List<EmploymentDetails> getEmploymentDetailsById(Integer employeeId);
}
