package com.ojas.obs.emp_edu.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ojas.obs.emp_edu.model.EmployeeEducationDetails;

public class EmployeeEducationDetailsRowMapper implements RowMapper<EmployeeEducationDetails>{

	@Override
	public EmployeeEducationDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
	
		EmployeeEducationDetails employeeEducationDetails =  new EmployeeEducationDetails();
		employeeEducationDetails.setId(rs.getInt(1));
		employeeEducationDetails.setEmployeeId(rs.getString(2));
		employeeEducationDetails.setQualification(rs.getString(3));
		employeeEducationDetails.setYear_of_passing(rs.getString(4));
		employeeEducationDetails.setPercentage_marks(rs.getString(5));
		employeeEducationDetails.setInstitution_name(rs.getString(6));
		employeeEducationDetails.setFlag(rs.getBoolean(7));
		employeeEducationDetails.setCreatedBy(rs.getString(8));
		employeeEducationDetails.setUpdatedBy(rs.getString(9));
		employeeEducationDetails.setCreatedDate(rs.getTimestamp(10));
		employeeEducationDetails.setUpdatedDate(rs.getTimestamp(11));
		employeeEducationDetails.setImage(rs.getString(12));
		employeeEducationDetails.setStatus(rs.getString(13));
		employeeEducationDetails.setComment(rs.getString(14));
		
		return employeeEducationDetails;
	}


}
