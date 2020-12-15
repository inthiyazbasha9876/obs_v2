package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.EmployeeSkillInfo;
import com.ojas.obs.model.EmployeeSkillInfoRequest;

public interface EmployeeSkillDao {

	
	
	public int saveEmployeeSkillInfo(EmployeeSkillInfoRequest employeeSkillInfoRequest) throws SQLException;
	public int updateEmployeeSkillInfo(EmployeeSkillInfoRequest employeeSkillInfoRequest) throws SQLException;
	public List<EmployeeSkillInfo> showEmployeeSkillInfo(EmployeeSkillInfoRequest employeeSkillInfoRequest) throws SQLException;
	public int getAllCount() throws SQLException;
	public List<EmployeeSkillInfo> getById(EmployeeSkillInfoRequest employeeSkillInfoRequest) throws SQLException;
	public List<EmployeeSkillInfo> getByEmpId(EmployeeSkillInfoRequest employeeSkillInfoRequest) throws SQLException;



}
