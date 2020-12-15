package com.obs.businessunit.dao;

import java.sql.SQLException;
import java.util.List;

import com.obs.businessunit.model.BusinessUnit;
import com.obs.businessunit.request.BusinessUnitRequest;

public interface BusinessUnitDao {
	Boolean saveBusinessUnit(BusinessUnitRequest businessUnitRequest) throws SQLException;

	Boolean updateBusinessUnit(BusinessUnitRequest businessUnitRequestobject) throws SQLException;
	
	boolean deleteBusinessUnit(BusinessUnitRequest businessUnitRequest) throws SQLException;

	List<BusinessUnit> getAllBussinessDetails(BusinessUnitRequest request) throws SQLException;

	public List<BusinessUnit> getById(BusinessUnitRequest request) throws SQLException;

	List<String> getBuHeads(BusinessUnitRequest businessUnitRequest)throws SQLException;

	
	
}
