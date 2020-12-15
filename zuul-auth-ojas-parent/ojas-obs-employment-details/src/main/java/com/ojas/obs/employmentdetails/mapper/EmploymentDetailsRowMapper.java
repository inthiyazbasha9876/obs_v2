package com.ojas.obs.employmentdetails.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ojas.obs.employmentdetails.model.EmploymentDetails;

public class EmploymentDetailsRowMapper implements RowMapper<EmploymentDetails> {

	@Override
	public EmploymentDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		EmploymentDetails employmentDetails = new EmploymentDetails();
		employmentDetails.setId(rs.getInt("id"));
		employmentDetails.setEmployeeId(rs.getString("employee_id"));
		//employmentDetails.setReportingManager(rs.getString("reporting_Manager"));
		//employmentDetails.setJoiningDate(rs.getDate("joining_date"));
		employmentDetails.setJoiningDate(rs.getString("joining_date"));
		
		
		
		
		employmentDetails.setResourceType(rs.getString("resource_type"));
		employmentDetails.setBondStatus(rs.getBoolean("bond_status"));
	
		employmentDetails.setBondTenure(rs.getString("bond_tenure"));
		employmentDetails.setResourceCat(rs.getString("resource_cat"));
		employmentDetails.setConfirmationDate(rs.getString("confirmation_date"));
		/*
		 * employmentDetails.setResignationDate(rs.getDate("resignation_date"));
		 * employmentDetails.setExitDate(rs.getDate("exit_date"));
		 * employmentDetails.setSeparationType(rs.getInt("separation_type"));
		 */
		employmentDetails.setCostCenterId(rs.getString("cost_center_id"));
		employmentDetails.setBuId(rs.getString("bu_id"));
		employmentDetails.setSbuId(rs.getString("sbu_id"));
		employmentDetails.setFlag(rs.getBoolean("flag"));
		employmentDetails.setCreatedBy(rs.getString("created_by"));
		employmentDetails.setUpdatedBy(rs.getString("updated_by"));
		employmentDetails.setCreatedDate(rs.getTimestamp("created_date"));
		employmentDetails.setUpdatedDate(rs.getTimestamp("updated_date"));
		return employmentDetails;
	}

}
