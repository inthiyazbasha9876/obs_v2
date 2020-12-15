package com.ojas.obs.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.request.SubBusinessUnitRequest;

public interface SubBusinessUnitFacade {
	ResponseEntity<Object> setSubBusinessUnit(SubBusinessUnitRequest subBusinessUnitRequest)throws SQLException;
	ResponseEntity<Object> getSubBusinessUnit(SubBusinessUnitRequest subBusinessUnitRequest)throws SQLException;

}
