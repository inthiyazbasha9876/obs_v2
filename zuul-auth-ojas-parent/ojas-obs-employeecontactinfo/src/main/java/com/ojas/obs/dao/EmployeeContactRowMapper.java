package com.ojas.obs.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ojas.obs.model.EmployeeContactInfo;

public class EmployeeContactRowMapper implements RowMapper<EmployeeContactInfo>{

	@Override
	public EmployeeContactInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		EmployeeContactInfo emp=new EmployeeContactInfo();
		if(rs==null) { 
		 return null;	
		}else {
		emp.setAlternateMobileNo(rs.getString("alternate_mobileNo"));
		emp.setCreatedBy(rs.getString("created_By")); 
		emp.setCreatedDate(rs.getTimestamp("created_date"));
		emp.setCurrentAddressLine1(rs.getString("current_Address_Line1"));
		emp.setCurrentAddressLine2(rs.getString("current_Address_Line2"));
		emp.setCurrentCity(rs.getString("current_City")); 
		emp.setCurrentPin(rs.getInt("current_Pin"));
		emp.setCurrentState(rs.getString("current_State"));
		emp.setEmpId(rs.getString("emp_Id"));
		emp.setFlag(rs.getBoolean("flag"));
		emp.setId(rs.getInt("id"));
		emp.setPermanentAddressLine1(rs.getString("permanent_Address_Line_1"));
		emp.setCreatedBy(rs.getString("created_By"));
		emp.setUpdatedBy(rs.getString("updated_By"));
		emp.setCreatedDate(rs.getTimestamp("created_date"));
		emp.setUpdatedDate(rs.getTimestamp("updated_date"));
		
		
		return emp;
		}
	}

}
