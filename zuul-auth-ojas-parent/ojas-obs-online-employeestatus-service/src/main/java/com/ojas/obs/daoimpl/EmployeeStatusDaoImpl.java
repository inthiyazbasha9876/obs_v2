package com.ojas.obs.daoimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.obs.dao.EmployeeStatusDao;
import com.ojas.obs.model.EmployeeStatus;
import com.ojas.obs.request.EmployeeStatusRequest;

/**
 * 
 * @author Manohar
 *
 */
@Repository
public class EmployeeStatusDaoImpl implements EmployeeStatusDao {
	public static final String INSERTEMPLOYEESTATUSSTMT = "insert into ojas_obs.obs_employeestatus(status) values (?)";
	public static final String UPDATESTMT = "update ojas_obs.obs_employeestatus set obs_employeestatus.status= ? where obs_employeestatus.id = ?";
	public static final String DELETESTMT = "update ojas_obs.obs_employeestatus set obs_employeestatus.flag= '0' where obs_employeestatus.id = ? and obs_employeestatus.flag= '1'";
	public static final String GETTOTALSTMT = "select * FROM ojas_obs.obs_employeestatus";
	public static final String GETBYIDSTMT = "select * FROM ojas_obs.obs_employeestatus where obs_employeestatus.id = ?";
	@Autowired
	private JdbcTemplate jdbcTemplate;
	Logger logger = Logger.getLogger(this.getClass());

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveEmployeeStatus(EmployeeStatusRequest employeeStatusRequest) throws SQLException {
		logger.debug("inside saveEmployeeStatus method in empstatusDaoImpl: " + employeeStatusRequest);
		boolean b = false;
		int[] save;
		List<EmployeeStatus> employeeStatusList = employeeStatusRequest.getEmployeeStatus();
		List<Object[]> list = new ArrayList<>();
		for (EmployeeStatus employeeStatus : employeeStatusList) {
			Object[] emp = new Object[] { employeeStatus.getStatus() };
			list.add(emp);
		}

		save = jdbcTemplate.batchUpdate(INSERTEMPLOYEESTATUSSTMT, list);
		if (save.length > 0) {
			logger.error("Record not saved in database");
			b = true;

		}
		return b;

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updateEmployeeStatus(EmployeeStatusRequest employeeStatusRequest) throws SQLException {
		logger.debug("inside updateEmployeeStatus method in empstatusDaoImpl: " + employeeStatusRequest);
		List<EmployeeStatus> employeeStatusList = employeeStatusRequest.getEmployeeStatus();
		boolean b = false;
		int[] update;
		List<Object[]> list = new ArrayList<>();
		for (EmployeeStatus employeeStatus : employeeStatusList) {
			Object[] emp = new Object[] { employeeStatus.getStatus(), employeeStatus.getId() };
			list.add(emp);
		}

		update = jdbcTemplate.batchUpdate(UPDATESTMT, list);

		if (update.length > 0) {
			logger.error("Record not updated in database");
			b = true;
		}
		return b;

	}

	@Override
	public boolean deleteEmployeeStatus(EmployeeStatusRequest employeeStatusRequest) {
		boolean b = false;
		int[] delete = null;
		List<EmployeeStatus> employeeStatusList = employeeStatusRequest.getEmployeeStatus();
		List<Object[]> list = new ArrayList<>();
		for (EmployeeStatus employeeStatus : employeeStatusList) {
			Object[] emp = new Object[] { employeeStatus.getId() };
			list.add(emp);
		}
		logger.debug("Inside delete in DAO");
		delete = jdbcTemplate.batchUpdate(DELETESTMT, list);
		int NoOfRecords = delete.length;
		int count = 0;
		if (delete.length > 0) {
			for (int i = 0; i < delete.length; i++) {
				if (delete[i] == 1)
					count++;
			}
			if (NoOfRecords == count)
				b = true;
		}
		return b;
	}

	@Override
	public List<EmployeeStatus> getAllStatus() throws SQLException {
		logger.debug("inside getallEmployeeStatus method in empstatusDaoImpl ");

		return jdbcTemplate.query(GETTOTALSTMT, new BeanPropertyRowMapper<EmployeeStatus>(EmployeeStatus.class));

	}

	@Override
	public List<EmployeeStatus> getById(Integer id) throws SQLException {
		logger.debug("inside saveEmployeeStatus method in empstatusDaoImpl: " + id);
		Object[] param = { id };

		return jdbcTemplate.query(GETBYIDSTMT, param, new BeanPropertyRowMapper<EmployeeStatus>(EmployeeStatus.class));

	}
}
