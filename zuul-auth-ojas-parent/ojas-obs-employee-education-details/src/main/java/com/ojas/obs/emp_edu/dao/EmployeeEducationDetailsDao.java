package com.ojas.obs.emp_edu.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.emp_edu.model.EmployeeEducationDetails;
import com.ojas.obs.emp_edu.model.EmployeeEducationDetailsRequest;

public interface EmployeeEducationDetailsDao {

	public int[] saveEmployeeEducationDetails(EmployeeEducationDetailsRequest empEducationDetailsRequest)
			throws SQLException;

	public int[] deleteEmployeeEducationDetails(EmployeeEducationDetailsRequest empEducationDetailsRequest)
			throws SQLException;

	public int[] updateEmployeeEducationDetails(EmployeeEducationDetailsRequest empEducationDetailsRequest)
			throws SQLException;

	public List<EmployeeEducationDetails> getEmployeeEducationDetails(
			) throws SQLException;

	public List<EmployeeEducationDetails> getEmployeeEducationDetailsById(
			EmployeeEducationDetailsRequest empEducationDetailsRequest) throws SQLException;
	
	List<EmployeeEducationDetails> getDetailByEmpId(EmployeeEducationDetailsRequest empEducationDetailsRequest)
			throws SQLException;
	public String getMailId(String empId) throws SQLException;

	public int[] statusUpdate(EmployeeEducationDetailsRequest empEducationDetailsRequest) throws SQLException;
}
