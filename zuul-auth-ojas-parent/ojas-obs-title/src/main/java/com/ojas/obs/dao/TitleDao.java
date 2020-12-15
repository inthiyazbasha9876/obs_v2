package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.Model;
import com.ojas.request.Request;

/**
 * 
 * @author uyashwanth
 *
 */
public interface TitleDao {
	boolean saveTitle(Request request) throws SQLException;

	List<Model> getAllTitle(Request request) throws SQLException;

	boolean updateTitle(Request request) throws SQLException;

	List<Model> getById(Request request) throws SQLException;
	List<Model> getByEmpId(Request request) throws SQLException;

	boolean deleteEmployeeRecord(Integer courseId) throws SQLException;

	public int getAllRecordsCount() throws SQLException;
}
