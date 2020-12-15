package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.RoleManagement;
import com.ojas.obs.request.RoleManagementRequest;

public interface RoleManagementDao {
	boolean saveRoleManagement(RoleManagementRequest roleManagementRequest)throws SQLException;
	boolean updateRoleManagement(RoleManagementRequest roleManagementRequest)throws SQLException;
	boolean deleteRoleManagement(RoleManagementRequest roleManagementRequest)throws SQLException;
	
	List<RoleManagement> getAllRollManagements() throws SQLException;
	List<RoleManagement> getByIdRollManagement(Integer id) throws SQLException;
}
