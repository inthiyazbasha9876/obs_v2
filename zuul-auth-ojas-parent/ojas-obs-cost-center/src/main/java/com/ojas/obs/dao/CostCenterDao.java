package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.CostCenter;
import com.ojas.obs.model.CostCenterRequest;

public interface CostCenterDao {

	public boolean save(CostCenterRequest costCenterRequest) throws SQLException;

	public boolean deleteCostCenter(CostCenterRequest costCenterRequest) throws SQLException;

	public boolean updateCenter(List<CostCenter> list) throws SQLException;

	public List<CostCenter> getAllCostCenter(CostCenterRequest costCenterReq) throws SQLException;

	public int getAllCostCenterCount() throws SQLException;

	// public List<CostCenter> getCountPerPage(List<CostCenter> list, int pageSize,
	// int pageNo);

	// public Connection getConnection() throws SQLException;

}
