package com.ojas.obs.daoimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.ojas.obs.constants.RoleServiceConstants.SAVEROLE;
import static com.ojas.obs.constants.RoleServiceConstants.UPDATEROLE;
import static com.ojas.obs.constants.RoleServiceConstants.DELETEROLE;

import static com.ojas.obs.constants.RoleServiceConstants.GETALLRECORDS;
import static com.ojas.obs.constants.RoleServiceConstants.GETBYIDRECORDS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ojas.obs.dao.RoleManagementDao;
import com.ojas.obs.model.RoleManagement;
import com.ojas.obs.request.RoleManagementRequest;

/**
 * 
 * @author asuneel
 *
 */
@Repository
public class RoleManagementDaoImpl implements RoleManagementDao {

	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean saveRoleManagement(RoleManagementRequest roleManagementRequest) throws SQLException {
		

			int[] save;
			List<RoleManagement> roleManagementList = roleManagementRequest.getRoleManagement();
			List<Object[]> list = new ArrayList<>();
			for (RoleManagement roleManagementItem : roleManagementList) {
				Object[] role = new Object[] { roleManagementItem.getRoleName(),roleManagementItem.getStatus() };
				list.add(role);

			}
			save = jdbcTemplate.batchUpdate(SAVEROLE, list);
			if (save.length > 0) {
				return true;
			} else {
			
			return false;
			}
	}

	@Override
	public boolean updateRoleManagement(RoleManagementRequest roleManagementRequest) throws SQLException {
		

			int[] update;
			List<RoleManagement> roleManagementList = roleManagementRequest.getRoleManagement();
			List<Object[]> list = new ArrayList<>();

			for (RoleManagement roleManagementItem : roleManagementList) {
				Object[] role = new Object[] { roleManagementItem.getRoleName(),roleManagementItem.getStatus(), roleManagementItem.getId() };
				list.add(role);
			}
			update = jdbcTemplate.batchUpdate(UPDATEROLE, list);

			if (update.length > 0) {
				return true;
			}
			return false;
		
	}

	@Override
	public List<RoleManagement> getAllRollManagements() throws SQLException {	
		return jdbcTemplate.query(GETALLRECORDS, new BeanPropertyRowMapper<RoleManagement>(RoleManagement.class));	
	}

	@Override
	public List<RoleManagement> getByIdRollManagement(Integer id) throws SQLException {		
		Object[] params = new Object[] { id };
		return jdbcTemplate.query(GETBYIDRECORDS, params,
				new BeanPropertyRowMapper<RoleManagement>(RoleManagement.class));
		
	}
	
	@Override
	public boolean deleteRoleManagement(RoleManagementRequest roleManagementRequest) throws SQLException {
		

			int[] delete;
			List<RoleManagement> roleManagementList = roleManagementRequest.getRoleManagement();
			List<Object[]> list = new ArrayList<>();

			for (RoleManagement roleManagementItem : roleManagementList) {
				Object[] role = new Object[] { roleManagementItem.getId() };
				list.add(role);
			}
			delete = jdbcTemplate.batchUpdate(DELETEROLE, list);

			if (delete.length > 0) {
				return true;
			}
			return false;
		
	}

}
