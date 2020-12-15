package com.ojas.obs.employmentdetails.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.obs.employmentdetails.controller.EmploymentDetailsController;
import com.ojas.obs.employmentdetails.exception.DataNotInsertedException;
import com.ojas.obs.employmentdetails.mapper.EmploymentDetailsRowMapper;
import com.ojas.obs.employmentdetails.model.EmploymentDetails;
import com.ojas.obs.employmentdetails.model.EmploymentDetailsRequest;
import com.ojas.obs.employmentdetails.util.QueryUtil;

@Repository
public class EmploymentDetailsDAOImpl implements EmploymentDetailsDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmploymentDetailsController.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private QueryUtil queryUtil;

	@Override
	@Transactional(rollbackFor = DataNotInsertedException.class)
	public boolean saveEmploymentDetails(List<EmploymentDetails> employmentDetailsList)
			throws DataNotInsertedException {

		Timestamp created_date = new Timestamp(new Date().getTime());
		int savedRows = 0;

		for (EmploymentDetails employmentObject : employmentDetailsList) {
			employmentObject.setFlag(true);
			savedRows = jdbcTemplate.update(queryUtil.getQuery("INSERT_EMPLOYMENT_DETAILS_STMT"),
					employmentObject.getEmployeeId(), // employmentObject.getReportingManager(),
					employmentObject.getJoiningDate(), employmentObject.getResourceType(),
					employmentObject.isBondStatus(), employmentObject.getBondTenure(),
					employmentObject.getResourceCat(), employmentObject.getConfirmationDate(),
					// employmentObject.getResignationDate(), employmentObject.getExitDate(),
					// employmentObject.getSeparationType(),
					employmentObject.getCostCenterId(), employmentObject.getBuId(), employmentObject.getSbuId(),
					employmentObject.getFlag(), employmentObject.getCreatedBy(), created_date);
			jdbcTemplate.update(queryUtil.getQuery("UPDATERMGEMPINFO"), employmentObject.getResourceCat(),
					employmentObject.getResourceType(), employmentObject.getJoiningDate(),
					employmentObject.getEmployeeId());
		}
		if (savedRows > 0) {
			LOGGER.debug("Employee details record inserted succefully ");
			return true;
		}
		LOGGER.debug("Employee details record not inserted succefully ");
		return false;
	}

	@Override
	public boolean updateEmploymentDetails(List<EmploymentDetails> employmentDetailsList)
			throws DataNotInsertedException {
		int updatedRows = 0;
		Timestamp updatedDate_date = new Timestamp(new Date().getTime());

		for (EmploymentDetails employmentObject : employmentDetailsList) {
			updatedRows = jdbcTemplate.update(queryUtil.getQuery("UPDATE_EMPLOYMENT_DETAILS_STMT"),
					employmentObject.getResourceType(), employmentObject.getJoiningDate(),
					employmentObject.isBondStatus(), employmentObject.getBondTenure(),
					employmentObject.getResourceCat(), employmentObject.getConfirmationDate(),
					employmentObject.getCostCenterId(), employmentObject.getBuId(), employmentObject.getSbuId(),
					employmentObject.getUpdatedBy(), updatedDate_date, employmentObject.getId());
		}
		if (updatedRows > 0) {
			return true;
		}
		return false;
	}

	boolean isEmployeeExits(Integer id) {

		int count = jdbcTemplate.queryForObject("select count(*) from employment_details where id=?", Integer.class,
				id);
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean deleteEmploymentDetails(List<EmploymentDetails> employmentDetailsList)
			throws DataNotInsertedException {
		int deletedRows = 0;
		boolean flag = false;

		for (EmploymentDetails employmentObject : employmentDetailsList) {
			deletedRows = jdbcTemplate.update(queryUtil.getQuery("DELETE_EMPLOYMENT_DETAILS_STMT"), flag,
					employmentObject.getId());
		}
		if (deletedRows > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<EmploymentDetails> getAllEmploymentDetails() {

		RowMapper<EmploymentDetails> rowMapper = new EmploymentDetailsRowMapper();
		List<EmploymentDetails> employmentDetailsList = jdbcTemplate
				.query(queryUtil.getQuery("GET_EMPLOYMENT_DETAILS_STMT"), rowMapper);
		LOGGER.debug("Employee  details list " + employmentDetailsList);
		return employmentDetailsList;
	}

	@Override
	public List<EmploymentDetails> getEmploymentDetailsByEmploymentId(
			EmploymentDetailsRequest employmentDetailsRequest) {

		List<EmploymentDetails> bankDetails = employmentDetailsRequest.getEmploymentDetails();

		List<EmploymentDetails> list = new ArrayList<EmploymentDetails>();
		String query2 = queryUtil.getQuery("GET_EMPLOYMENT_DETAILS_BY_EMPLOYEE_ID_STMT");
		query2 = query2 + "and employee_id = " + employmentDetailsRequest.getEmploymentDetails().get(0).getEmployeeId();
		List<EmploymentDetails> query = jdbcTemplate.query(query2,
				new BeanPropertyRowMapper<EmploymentDetails>(EmploymentDetails.class));
		for (EmploymentDetails bankDetails2 : query) {
			for (EmploymentDetails bankDetails3 : bankDetails) {

				if (!query.isEmpty()) {

					if (bankDetails2.getId().equals(bankDetails3.getId())) {
						list.add(bankDetails2);
						return list;
					} else if (bankDetails2.getEmployeeId().equals(bankDetails3.getEmployeeId())) {
						list.add(bankDetails2);
						return list;
					}
				}
			}
		}

		return query;
	}

	@Override
	public List<EmploymentDetails> getEmploymentDetailsById(Integer id) {

		RowMapper<EmploymentDetails> rowMapper = new EmploymentDetailsRowMapper();
		List<EmploymentDetails> employmentDetailsList = jdbcTemplate
				.query(queryUtil.getQuery("GET_EMPLOYMENT_DETAILS_BY_ID_STMT"), new Object[] { id }, rowMapper);
		LOGGER.debug("Employee  details list " + employmentDetailsList);
		return employmentDetailsList;
	}
}
