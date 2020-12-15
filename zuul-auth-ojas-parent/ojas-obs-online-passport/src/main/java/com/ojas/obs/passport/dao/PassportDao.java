package com.ojas.obs.passport.dao;

import java.sql.SQLException;

import java.util.List;

import com.ojas.obs.passport.Request.PassportRequest;
import com.ojas.obs.passport.model.Passport;

public interface PassportDao {
	
	public boolean savePassport(PassportRequest passportRequest) throws SQLException;

	public List<Passport> getAll(PassportRequest passportRequest) throws SQLException;
	
	public boolean deletePassport(PassportRequest passportRequest) throws SQLException;

	public boolean updatePassport(PassportRequest passportRequest) throws SQLException;
	
	public Integer getcountPassport(PassportRequest passportRequest) throws SQLException;

	//List<Passport> getCountPerPage(List<Passport> stateList, int pageSize, int pageNo) throws SQLException;
	
	public List<Passport> getById(PassportRequest passportRequest) throws SQLException;
}
