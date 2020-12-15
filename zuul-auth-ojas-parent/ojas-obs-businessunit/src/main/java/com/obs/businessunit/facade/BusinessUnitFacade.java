package com.obs.businessunit.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.obs.businessunit.request.BusinessUnitRequest;

public interface BusinessUnitFacade {
	ResponseEntity<Object> setBusinessUnit(BusinessUnitRequest businessUnitRequest) throws SQLException;
	ResponseEntity<Object> getBusinessUnit(BusinessUnitRequest businessUnitRequest) throws SQLException;
}
