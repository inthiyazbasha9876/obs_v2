package com.ojas.obs.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ojas.obs.model.DependentDetails;

public class RowMap implements RowMapper<DependentDetails> {
	@Override
	public DependentDetails mapRow(ResultSet result, int rowNum) throws SQLException {
		DependentDetails dependentDetails = new DependentDetails(); 
		dependentDetails.setId(result.getInt("id"));
        dependentDetails.setDependent_Name(result.getString("dependent_Name"));
        dependentDetails.setRelation(result.getString("relation"));
        //dependentDetails.setDate_Of_Birth(result.getDate("date_Of_Birth"));
        dependentDetails.setDate_Of_Birth(result.getString("date_Of_Birth"));
        
        dependentDetails.setEmployee_Id(result.getString("employee_Id"));
        dependentDetails.setCreated_By(result.getString("created_By"));
        dependentDetails.setCreated_Date(result.getTimestamp("created_Date"));
        dependentDetails.setUpdated_By(result.getString("updated_By"));
        dependentDetails.setUpdated_Date(result.getTimestamp("updated_Date"));
        dependentDetails.setFlag(result.getBoolean("flag"));     
        return dependentDetails;
	

}
}
