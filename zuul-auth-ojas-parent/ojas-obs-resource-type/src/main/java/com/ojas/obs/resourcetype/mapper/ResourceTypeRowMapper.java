package com.ojas.obs.resourcetype.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ojas.obs.resourcetype.model.ResourceType;

public class ResourceTypeRowMapper implements RowMapper<ResourceType> {

	@Override
	public ResourceType mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResourceType employmentDetails = new ResourceType();
		employmentDetails.setId(rs.getInt("resourcetype_id"));
		employmentDetails.setResourceTypeName(rs.getString("resourcetype_name"));
		return employmentDetails;
	}

}
