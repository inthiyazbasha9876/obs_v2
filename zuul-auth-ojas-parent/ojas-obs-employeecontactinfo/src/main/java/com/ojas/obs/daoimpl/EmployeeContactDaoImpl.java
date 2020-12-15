package com.ojas.obs.daoimpl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.obs.dao.EmployeeContactDao;
import com.ojas.obs.dao.EmployeeContactRowMapper;
import com.ojas.obs.model.EmployeeContactInfo;
import com.ojas.obs.requests.EmployeeContactInfoRequest;

/**
 * 
 * @author ksaiKrishna
 *
 */

@Repository
public class EmployeeContactDaoImpl implements EmployeeContactDao {

	Logger logger = Logger.getLogger(this.getClass());
	public static final String INSERTEMPLOYEECONTACTINFOSTMT = "INSERT INTO obs_employeecontactinfo(alternate_mobileNo,current_Address_line1,current_Address_line2, current_City,current_State,current_Pin,permanent_Address_Line_1,emp_Id,created_By,created_date,flag) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	public static final String GETCONTACTBYID = "SELECT * FROM obs_employeecontactinfo WHERE id=? and flag = 1";
	public static final String GETBYEMPID = "SELECT * FROM obs_employeecontactinfo WHERE emp_Id=? and flag = 1";
	public static final String UPDATESTMT = "UPDATE obs_employeecontactinfo set alternate_mobileNo = ?,current_Address_line1 = ?,current_Address_line2 = ?, current_City = ?,current_State = ?,current_Pin = ?,permanent_Address_Line_1 = ?,emp_Id=?,updated_By = ?,updated_date = ? WHERE id = ? ";
	public static final String DELETECONTACT = "UPDATE obs_employeecontactinfo set flag = ?,updated_date = ?,updated_By = ? where id = ?";
	public static final String GETALLDETAILS = "select * from obs_employeecontactinfo where flag=true";
	public static final String GETCOUNT = "select count(*) from  obs_employeecontactinfo";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveEmployeeContactInfo(EmployeeContactInfoRequest empRequest) throws SQLException {
		boolean status = false;
		int[] save;
		logger.debug("@Saving employee contact info dao method");
		Timestamp sqlDate = new Timestamp(new java.util.Date().getTime());

		List<Object[]> inputList = new ArrayList<>();
		for (EmployeeContactInfo emp : empRequest.getEmpInfo()) {
			Object[] tmp = { emp.getAlternateMobileNo(), emp.getCurrentAddressLine1(), emp.getCurrentAddressLine2(),
					emp.getCurrentCity(), emp.getCurrentState(), emp.getCurrentPin(), emp.getPermanentAddressLine1(),
					emp.getEmpId(), emp.getCreatedBy(), sqlDate, 1 };
			inputList.add(tmp);

		}
		save = jdbcTemplate.batchUpdate(INSERTEMPLOYEECONTACTINFOSTMT, inputList);
		if (save.length > 0)
			status = true;
		return status;
	}

	@Override
	public boolean updateEmployeeContactInfo(EmployeeContactInfoRequest empRequest) throws SQLException {

		List<EmployeeContactInfo> modelList = empRequest.getEmpInfo();
		List<Object[]> list = new ArrayList<>();
		int count = 0;
		Timestamp sqlUpdateDate = new Timestamp(new java.util.Date().getTime());

		for (EmployeeContactInfo emp : modelList) {
			Object[] model = { emp.getAlternateMobileNo(), emp.getCurrentAddressLine1(), emp.getCurrentAddressLine2(),
					emp.getCurrentCity(), emp.getCurrentState(), emp.getCurrentPin(), emp.getPermanentAddressLine1(),
					emp.getEmpId(), emp.getUpdatedBy(), sqlUpdateDate, emp.getId() };
			list.add(model);
		}
		int[] batchUpdate = jdbcTemplate.batchUpdate(UPDATESTMT, list);
		int len = batchUpdate.length;
		for (int i : batchUpdate) {
			if (i > 0) {
				count++;
			}
		}
		if (count == len) {
			logger.debug("updated successfully through DaoImpl");
			return true;
		}

		logger.debug("failed to update through daoImpl Method");
		return false;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean deleteEmployeeContactInfo(EmployeeContactInfoRequest employeeContactInfoRequest) {

		logger.debug("@Deleting employee contact info" + employeeContactInfoRequest);
		Timestamp sqlDeleteDate = new Timestamp(new java.util.Date().getTime());
		int[] delete;
		boolean status = false;
		List<Object[]> inputList = new ArrayList<>();
		for (EmployeeContactInfo emp : employeeContactInfoRequest.getEmpInfo()) {
			Object[] tmp = { false, sqlDeleteDate, emp.getUpdatedBy(), emp.getId() };
			inputList.add(tmp);
		}
		delete = jdbcTemplate.batchUpdate(DELETECONTACT, inputList);
		if (delete.length > 0) {
			status = true;
		}
		return status;
	}

	@Override
	public List<EmployeeContactInfo> showEmployeeContactInfoById(EmployeeContactInfoRequest empRequest) {
		List<EmployeeContactInfo> modelList = empRequest.getEmpInfo();
		List<Object[]> list = new ArrayList<>();
		Object[] param = null;
		List<EmployeeContactInfo> query = null;

		for (EmployeeContactInfo details : modelList) {
			param = new Object[] { details.getId() };
			list.add(param);
		}
		query = jdbcTemplate.query(GETCONTACTBYID, param, new EmployeeContactRowMapper());

		return query;
	}

	@Override
	public List<EmployeeContactInfo> showEmployeeContactInfoByEmpId(EmployeeContactInfoRequest empRequest) {
		List<EmployeeContactInfo> modelList = empRequest.getEmpInfo();
		List<Object[]> list = new ArrayList<>();
		Object[] param = null;
		List<EmployeeContactInfo> query = null;

		for (EmployeeContactInfo details : modelList) {
			param = new Object[] { details.getEmpId() };
			list.add(param);
		}
		query = jdbcTemplate.query(GETBYEMPID, param, new EmployeeContactRowMapper());

		return query;
	}

	@Override
	public List<EmployeeContactInfo> getAllContacctDetails() throws SQLException {
		List<EmployeeContactInfo> list = null;

		list = jdbcTemplate.query(GETALLDETAILS, new EmployeeContactRowMapper());

		return list;
	}

	@Override
	public int getAllContactDetailsCount() throws SQLException {
		int count = 0;

		count = jdbcTemplate.queryForObject(GETCOUNT, Integer.class);

		return count;
	}

}
