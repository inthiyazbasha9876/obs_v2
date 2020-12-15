package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.SubBusinessUnit;
import com.ojas.obs.request.SubBusinessUnitRequest;

public interface SubBusinessUnitDao {

	boolean saveSubBusinessUnit(SubBusinessUnitRequest subBusinessUnitRequest)throws SQLException;
	boolean updateSubBusinessUnit(SubBusinessUnitRequest subBusinessUnitRequest)throws SQLException;
	boolean deleteSubBusinessUnit(SubBusinessUnitRequest subBusinessUnitRequest)throws SQLException;
	
	List<SubBusinessUnit> getAllSubBusinessUnitDetails()throws SQLException;
	List<SubBusinessUnit> getByIdSubBusinessUnitDetails(Integer id)throws SQLException;
	List<String> getSbuHeads();
}
