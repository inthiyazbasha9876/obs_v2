package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.EmployeeInfo;
import com.ojas.obs.model.EmployeeSkills;
import com.ojas.obs.request.EmployeeInfoRequest;

/**
 * 
 * @author sdileep
 *
 */
public interface EmployeeInfoDao {

	/**
	 * 
	 * @param employeeInfoRequest
	 * @return
	 * @throws SQLException
	 */
	public boolean saveEmployeeInfo(EmployeeInfoRequest employeeInfoRequest) throws SQLException;

	/**
	 * 
	 * @param employeeInfoRequest
	 * @return
	 * @throws SQLException
	 */
	public boolean updateEmployeeInfo(EmployeeInfoRequest employeeInfoRequest) throws SQLException;

	/**
	 * 
	 * @param employeeInfoRequest
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteEmployeeInfo(EmployeeInfoRequest employeeInfoRequest) throws SQLException;
	public boolean picUpload(EmployeeInfo employeeInfo) throws SQLException;
	public boolean updateStatus(EmployeeInfo employeeInfo) throws SQLException;

	/**
	 * 
	 * @param employeeInfoRequest
	 * @return
	 * @throws SQLException
	 */
	public List<EmployeeInfo> getAllEmployeeDetails(EmployeeInfoRequest employeeInfoRequest) throws SQLException;
	public List<EmployeeInfo> getReporties(EmployeeInfoRequest employeeInfoRequest) throws SQLException;

	/**
	 * 
	 * @param allemployeeDetails
	 * @param pageSize
	 * @param pageNum
	 * @return
	 * @throws SQLException 
	 */

	/**
	 * 
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<EmployeeInfo> getAllEmployeeInfos() throws SQLException;
	public List<EmployeeInfo> getById(EmployeeInfoRequest employeeInfoRequest) throws SQLException;

	public String getMngrMail(String mngrId) throws SQLException;
	
	public List<String> getEmails(String empId, String mngrId) throws SQLException;

	public List<EmployeeSkills> getSkillsByEmpInfo(List<String> employeeSkills);
	
	
}
