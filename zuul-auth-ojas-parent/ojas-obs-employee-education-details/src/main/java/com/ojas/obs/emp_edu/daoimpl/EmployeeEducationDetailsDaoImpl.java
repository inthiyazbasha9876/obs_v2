package com.ojas.obs.emp_edu.daoimpl;

import static com.ojas.obs.emp_edu.utility.Constants.DELETE_STMT;
import static com.ojas.obs.emp_edu.utility.Constants.GETALL_STMT;
import static com.ojas.obs.emp_edu.utility.Constants.GETBYIDQ;
import static com.ojas.obs.emp_edu.utility.Constants.GETBYEMPID;
import static com.ojas.obs.emp_edu.utility.Constants.GETMAILID;
import static com.ojas.obs.emp_edu.utility.Constants.INSERT_STMT;
import static com.ojas.obs.emp_edu.utility.Constants.UPDATE_STMT;
import static com.ojas.obs.emp_edu.utility.Constants.STATUS_UPDATE_STMT;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ojas.obs.emp_edu.dao.EmployeeEducationDetailsDao;
import com.ojas.obs.emp_edu.model.EmployeeEducationDetails;
import com.ojas.obs.emp_edu.model.EmployeeEducationDetailsRequest;

@Repository
public class EmployeeEducationDetailsDaoImpl implements EmployeeEducationDetailsDao {
	@Autowired
	private JdbcTemplate jdbctemplate;

	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public int[] saveEmployeeEducationDetails(EmployeeEducationDetailsRequest empEducationDetailsRequest)
			throws SQLException {

		Timestamp stamp = new Timestamp(new java.util.Date().getTime());
		logger.debug("the input to dao save method is " + empEducationDetailsRequest);
		logger.debug("jdbc template " + jdbctemplate);
		List<EmployeeEducationDetails> employeeEducationDetailsList = empEducationDetailsRequest
				.getEmployeeEducationDetailsList();
		List<Object[]> paramsList = new ArrayList<>();

		for (EmployeeEducationDetails employeeEducationDetails : employeeEducationDetailsList) {
			Object[] param = new Object[] { employeeEducationDetails.getEmployeeId(),
					employeeEducationDetails.getQualification(), employeeEducationDetails.getYear_of_passing(),
					employeeEducationDetails.getPercentage_marks(), employeeEducationDetails.getInstitution_name(),
					true, employeeEducationDetails.getCreatedBy(), employeeEducationDetails.getUpdatedBy(), stamp,
					stamp, employeeEducationDetails.getImage(), employeeEducationDetails.getStatus() };
			paramsList.add(param);

		}

		int[] batchUpdate = jdbctemplate.batchUpdate(INSERT_STMT, paramsList);
		logger.debug("the output from dao save method is " + batchUpdate);
		return batchUpdate;
	}

	@Override
	public int[] deleteEmployeeEducationDetails(EmployeeEducationDetailsRequest empEducationDetailsRequest)
			throws SQLException {

		logger.debug("the input from dao delete method is " + empEducationDetailsRequest);
		List<EmployeeEducationDetails> employeeEducationDetailsList = empEducationDetailsRequest
				.getEmployeeEducationDetailsList();
		List<Object[]> paramsList = new ArrayList<>();
		for (EmployeeEducationDetails employeeEducationDetails : employeeEducationDetailsList) {
			Object[] params = new Object[] { employeeEducationDetails.getId() };
			paramsList.add(params);
		}
		int[] batchUpdate = jdbctemplate.batchUpdate(DELETE_STMT, paramsList);
		logger.debug("the output from dao delete method is " + batchUpdate);
		return batchUpdate;

	}

	@Override
	public int[] updateEmployeeEducationDetails(EmployeeEducationDetailsRequest empEducationDetailsRequest)
			throws SQLException {
		List<EmployeeEducationDetails> employeeEducationDetailsList = empEducationDetailsRequest
				.getEmployeeEducationDetailsList();

		List<Object[]> paramsList = new ArrayList<>();
		for (EmployeeEducationDetails employeeEducationDetails : employeeEducationDetailsList) {
			Object[] param = new Object[] { employeeEducationDetails.getEmployeeId(), true,
					employeeEducationDetails.getUpdatedBy(), new Timestamp(new java.util.Date().getTime()),
					employeeEducationDetails.getImage(), employeeEducationDetails.getStatus(),
					employeeEducationDetails.getId() };
			paramsList.add(param);

		}
		return jdbctemplate.batchUpdate(UPDATE_STMT, paramsList);

	}
	
	
	// status update
	
	@Override
	public int[] statusUpdate(EmployeeEducationDetailsRequest empEducationDetailsRequest)
			throws SQLException {
		List<EmployeeEducationDetails> employeeEducationDetailsList = empEducationDetailsRequest
				.getEmployeeEducationDetailsList();

		List<Object[]> paramsList = new ArrayList<>();
		for (EmployeeEducationDetails employeeEducationDetails : employeeEducationDetailsList) {
			Object[] param = new Object[] { employeeEducationDetails.getEmployeeId(), true,
					employeeEducationDetails.getUpdatedBy(), new Timestamp(new java.util.Date().getTime()),
					 employeeEducationDetails.getStatus(), employeeEducationDetails.getComment(),
					employeeEducationDetails.getId() };
			paramsList.add(param);

		}
		return jdbctemplate.batchUpdate(STATUS_UPDATE_STMT, paramsList);

	}

	@Override
	public List<EmployeeEducationDetails> getEmployeeEducationDetails() throws SQLException {

		List<EmployeeEducationDetails> query = jdbctemplate.query(GETALL_STMT,
				new BeanPropertyRowMapper<>(EmployeeEducationDetails.class));
		logger.debug("the output from dao  method is " + query);
		return query;

	}

	@Override
	public List<EmployeeEducationDetails> getEmployeeEducationDetailsById(
			EmployeeEducationDetailsRequest empEducationDetailsRequest) throws SQLException {
		List<EmployeeEducationDetails> modelList = empEducationDetailsRequest.getEmployeeEducationDetailsList();
		List<Object[]> list = new ArrayList<>();
		Object[] param = null;

		for (EmployeeEducationDetails details : modelList) {
			param = new Object[] { details.getId() };
			list.add(param);
		}
		return jdbctemplate.query(GETBYIDQ, param, new BeanPropertyRowMapper<>(EmployeeEducationDetails.class));

	}

	@Override
	public List<EmployeeEducationDetails> getDetailByEmpId(EmployeeEducationDetailsRequest empEducationDetailsRequest)
			throws SQLException {
		List<EmployeeEducationDetails> modelList = empEducationDetailsRequest.getEmployeeEducationDetailsList();
		List<Object[]> list = new ArrayList<>();
		Object[] param = null;

		for (EmployeeEducationDetails details : modelList) {
			param = new Object[] { details.getEmployeeId() };
			list.add(param);
		}
		return jdbctemplate.query(GETBYEMPID, param, new BeanPropertyRowMapper<>(EmployeeEducationDetails.class));

	}

	@Override
	public String getMailId(String empId) throws SQLException {
		logger.debug("Inside getMail method in dao");
		return jdbctemplate.queryForObject(GETMAILID + empId, String.class);
	}

}
