package com.ojas.obs.dao;

import java.util.List;
import java.sql.SQLException;


import com.ojas.obs.model.SeparationType;
import com.ojas.obs.request.SeparationTypeRequest;
//import com.ojas.obs.request.SeparationTypeRequest;

/**
 * 
 * @author nsrikanth
 *
 */

public interface SeparationTypeDao {
	
boolean saveSeparationType(SeparationTypeRequest separationTypeReq) throws SQLException;
	
	boolean updateSeparationType(SeparationTypeRequest separationType) throws SQLException;
	
	boolean deleteSeparationType(int separationTypeId) throws SQLException;
	public List<SeparationType> getById(SeparationTypeRequest separationTypeRequest) throws SQLException;
	
	List<SeparationType> getAllSeparationType() throws SQLException;



}
