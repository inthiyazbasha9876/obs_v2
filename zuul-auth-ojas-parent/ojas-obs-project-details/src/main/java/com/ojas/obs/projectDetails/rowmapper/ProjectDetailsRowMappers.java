package com.ojas.obs.projectDetails.rowmapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ojas.obs.projectDetails.model.ProjectDetails;

public class ProjectDetailsRowMappers implements RowMapper<ProjectDetails> {

	public ProjectDetails mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		ProjectDetails projectDetails = new ProjectDetails();
		projectDetails.setId(resultSet.getInt(1));
		projectDetails.setProjectName(resultSet.getString(2));
		projectDetails.setContractId(resultSet.getInt(3));
		projectDetails.setRateId(resultSet.getInt(4));
		
		//projectDetails.setStartDate(resultSet.getDate(5));
		projectDetails.setStartDate(resultSet.getString(5));
		//projectDetails.setEndDate(resultSet.getDate(6));
		projectDetails.setEndDate(resultSet.getString(6));
		
		projectDetails.setBillingId(resultSet.getInt(7));
		projectDetails.setEmployeeId(resultSet.getString(8));
		projectDetails.setProjectTechStack(resultSet.getString(9));
		projectDetails.setCustomer(resultSet.getString(10));
		projectDetails.setLocation(resultSet.getString(11));
		projectDetails.setGstApplicable(resultSet.getBoolean(12));
		projectDetails.setProjectType(resultSet.getString(13));
		projectDetails.setProjectStatus(resultSet.getString(14));
		projectDetails.setBdmContact(resultSet.getString(15));
		projectDetails.setInternal(resultSet.getBoolean(16));
		projectDetails.setFlag(resultSet.getBoolean(17));
		projectDetails.setCreatedBy(resultSet.getString(18));
		projectDetails.setUpdatedBy(resultSet.getString(19));
		projectDetails.setCreatedDate(resultSet.getTimestamp(20));
		projectDetails.setUpdatedDate(resultSet.getTimestamp(21));

		return projectDetails;
	}
}
