package com.ojas.obs.dao;


import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.EmployeeBUDetails;
import com.ojas.obs.request.EmployeeBUDetailsRequest;
/**
 * 
 * @author uyashwanth
 *
 */
public interface EmployeeBUDao {

	boolean saveEmployeebu(EmployeeBUDetailsRequest employeebuRequest) throws SQLException;

	List<EmployeeBUDetails> getAllEmployeebu(EmployeeBUDetailsRequest employeebuRequest) throws SQLException;
	List<EmployeeBUDetails> getById(EmployeeBUDetailsRequest employeebuRequest) throws SQLException;

	boolean updateEmployeebu(EmployeeBUDetailsRequest employeebuRequest) throws SQLException;

	boolean deleteEmployeeRecord(Integer courseId) throws SQLException;

	public int getAllRecordsCount() throws SQLException;

	List<EmployeeBUDetails> getByEmpId(EmployeeBUDetailsRequest employeebuRequest) throws SQLException;


}
