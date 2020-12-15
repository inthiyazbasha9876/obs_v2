package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.Designation;
import com.ojas.obs.request.DesignationRequest;

/**
 * 
 * @author nsrikanth
 *
 */
public interface DesignationDao {

	boolean saveDesignation(DesignationRequest designationRequest) throws SQLException;

	boolean updateDesignation(DesignationRequest designationRequest) throws SQLException;

	 boolean deleteDesignation(int id) throws SQLException;

	List<Designation> getAll(DesignationRequest designationRequest) throws SQLException;

	int getAllDesignationCount() throws SQLException;

	public List<Designation> getById(DesignationRequest designationRequest) throws SQLException;


	// List<Designation> getCountPerPage(List<Designation> list, int pageSize, int
	// pageNo);

}

/*
 * public Boolean saveDesignation(DesignationRequest designationRequest) throws
 * SQLException {
 * 
 * List<Designation> designation = designationRequest.getDesignation(); for
 * (Designation designation2 : designation) { boolean status = false; try
 * (Connection connection = getConnection(); PreparedStatement prepareStatement
 * = connection.prepareStatement(SAVEDesignation)) {
 * connection.setAutoCommit(false); prepareStatement.setInt(1,
 * designation2.getId()); prepareStatement.setString(2,
 * designation2.getDesignation()); int i = prepareStatement.executeUpdate();
 * connection.commit(); if (i > 0) { return true; } else { return status; } } }
 * return null; }
 */
