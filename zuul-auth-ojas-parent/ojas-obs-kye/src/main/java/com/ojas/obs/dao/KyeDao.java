package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.KYE;
import com.ojas.obs.request.KYERequest;


public interface KyeDao {

	boolean saveKYE(KYERequest kyeRequest) throws SQLException, Exception;

	public boolean updateAadharKYE(KYERequest kyeRequest) throws SQLException;
	
	public boolean updatePanKYE(KYERequest kyeRequest) throws SQLException;
	
	public boolean updatePassportKYE(KYERequest kyeRequest) throws SQLException;

	boolean deleteKYE(KYERequest kyeRequest) throws SQLException;

	List<KYE> getAllKYE(KYERequest kyeRequest) throws SQLException;
	
	List<KYE> getByEmpId(KYERequest kyeRequest);
	
	List<KYE> getById(KYERequest kyeRequest);

	int getAllKYECount() throws SQLException;
	
	public boolean updatePanStatus(KYERequest kyeRequest) throws SQLException ;
	public boolean updateAadharStatus(KYERequest kyeRequest) throws SQLException ;
	public boolean updatePassportStatus(KYERequest kyeRequest) throws SQLException ;


	public String getMailId(String employeeId) throws SQLException;


}