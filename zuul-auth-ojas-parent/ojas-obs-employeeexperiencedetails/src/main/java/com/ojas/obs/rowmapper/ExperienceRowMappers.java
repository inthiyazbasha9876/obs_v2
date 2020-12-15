package com.ojas.obs.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ojas.obs.model.EmployeeExperienceDetails;

public class ExperienceRowMappers  implements RowMapper<EmployeeExperienceDetails> {
	

	@Override
	public EmployeeExperienceDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		

		
			EmployeeExperienceDetails employeeExperienceDetails = new EmployeeExperienceDetails();
			employeeExperienceDetails.setId(rs.getInt(1));
			employeeExperienceDetails.setCompany_name(rs.getString(2));
			employeeExperienceDetails.setJoining_date(rs.getDate(3));
			employeeExperienceDetails.setExit_date(rs.getDate(4));
			employeeExperienceDetails.setSalary(rs.getDouble(5));
			employeeExperienceDetails.setLocation(rs.getString(6));
			employeeExperienceDetails.setIs_current_company(rs.getString(7));
			employeeExperienceDetails.setEmployee_id(rs.getString(8));
			employeeExperienceDetails.setReference_1_name(rs.getString(9));
			employeeExperienceDetails.setReference_1_contact(rs.getString(10));
			employeeExperienceDetails.setReference_2_name(rs.getString(11));
			employeeExperienceDetails.setReference_2_contact(rs.getString(12));
			employeeExperienceDetails.setFlag(rs.getBoolean(13));
			employeeExperienceDetails.setCreated_by(rs.getString(14));
			employeeExperienceDetails.setUpdated_by(rs.getString(15));
			employeeExperienceDetails.setCreated_date(rs.getTimestamp(16));
			employeeExperienceDetails.setUpdated_date(rs.getTimestamp(17));
			employeeExperienceDetails.setImage(rs.getString(18));
			employeeExperienceDetails.setExperience(rs.getDouble(19));
			employeeExperienceDetails.setStatus(rs.getBoolean(20));
			employeeExperienceDetails.setDocumentsstatus(rs.getString(21));
			
			return employeeExperienceDetails;
		}}


