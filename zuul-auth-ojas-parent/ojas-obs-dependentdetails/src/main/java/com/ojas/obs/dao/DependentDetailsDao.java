package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.DependentDetails;
import com.ojas.obs.request.DependentDetailsRequest;

/**
 * 
 * @author srinukummari
 *
 */
public interface DependentDetailsDao {
	public int  saveDependentDetails(DependentDetailsRequest dependentDetailsRequest) throws SQLException;
	public int updateDependentDetails(DependentDetailsRequest dependentDetailsRequest) throws SQLException;
	public int deleteDependentDetails(DependentDetailsRequest dependentDetailsRequest) throws SQLException;
	public List<DependentDetails> getAll(DependentDetailsRequest DependentDetailsRequest) throws SQLException;
	public List<DependentDetails> getById(Integer id) throws SQLException;
	List<DependentDetails> getByEmpId(String s) throws SQLException;
 }
