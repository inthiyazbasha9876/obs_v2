package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.Insurance;
import com.ojas.obs.request.InsuranceRequest;

public interface InsuranceDao {

	public boolean saveInsuranceDetails(InsuranceRequest insuranceRequest) throws SQLException;

	public boolean updateInsuranceDetails(InsuranceRequest insurance) throws SQLException;

	public boolean deleteInsuranceDetails(InsuranceRequest insuranceRequest) throws SQLException;

	public List<Insurance> getAllInsuranceDetails(InsuranceRequest insuranceRequest);

	public List<Insurance> getPageRecords(List<Insurance> allInsuranceDetails, int pageSize, int pageNum);

	public int getAllInsuranceDetailsCount() throws SQLException;

}
