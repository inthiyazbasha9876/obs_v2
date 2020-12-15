package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.EmployeeStatus;
import com.ojas.obs.request.EmployeeStatusRequest;
/**
 * 
 * @author Manohar
 *
 */
public interface EmployeeStatusDao {
	public boolean saveEmployeeStatus(EmployeeStatusRequest employeeStatusRequest) throws SQLException;
	public boolean updateEmployeeStatus(EmployeeStatusRequest employeeStatusRequest)throws SQLException;
	public boolean deleteEmployeeStatus(EmployeeStatusRequest employeeStatusRequest);
	public List<EmployeeStatus> getAllStatus()throws SQLException;
	public List<EmployeeStatus> getById(Integer id)throws SQLException;
}
