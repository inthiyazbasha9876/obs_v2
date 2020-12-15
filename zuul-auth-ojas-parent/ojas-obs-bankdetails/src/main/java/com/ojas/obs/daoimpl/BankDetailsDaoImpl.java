package com.ojas.obs.daoimpl;

import static com.ojas.obs.constants.UserConstants.ALLRECORDS;
import static com.ojas.obs.constants.UserConstants.DELETEBANKDETAILS;
import static com.ojas.obs.constants.UserConstants.SAVEBANKDETAILS;
import static com.ojas.obs.constants.UserConstants.UPDATEBANKDETAILS;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ojas.obs.dao.BankDetailsDAO;
import com.ojas.obs.model.BankDetails;
import com.ojas.obs.request.BankDetailsRequest;

/**
 * 
 * @author akrishna
 *
 */
@Repository
public class BankDetailsDaoImpl implements BankDetailsDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	Logger logger = Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ojas.obs.dao.BankDetailsDAO#saveEmployeeBankDetails(com.ojas.obs.request.
	 * BankDetailsRequest)
	 */
	@Override
	public boolean saveEmployeeBankDetails(BankDetailsRequest bankDetailsRequest) throws SQLException {
		logger.debug("incoming request in DAO ");
		boolean b = false;
		Timestamp timestamp = new Timestamp(new Date().getTime());
		List<BankDetails> bankDetails = bankDetailsRequest.getBankDetails();
		List<Object[]> list = new ArrayList<>();

		for (BankDetails employeeBankDetails : bankDetails) {
			employeeBankDetails.setFlag(true);

			Object[] object = { employeeBankDetails.getBank_account_no(), employeeBankDetails.getBank_name(),
					employeeBankDetails.getBank_city(), employeeBankDetails.getBank_branch(),
					employeeBankDetails.getBank_ifsc_code(), employeeBankDetails.getBank_account_status(),
					employeeBankDetails.getEmployee_id(), employeeBankDetails.isIs_active(),
					employeeBankDetails.getCreated_by(), timestamp, employeeBankDetails.isFlag() };
			logger.debug("Number of records in Object[] : " + object.length);
			list.add(object);
		}

		int[] batchsave = jdbcTemplate.batchUpdate(SAVEBANKDETAILS, list);
		if (batchsave.length > 0) {
			b = true;
		}
		return b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.obs.dao.BankDetailsDAO#updateEmployeeBankDetails(com.ojas.obs.
	 * request.BankDetailsRequest)
	 */
	@Override
	public boolean updateEmployeeBankDetails(BankDetailsRequest bankDetailsRequest) throws SQLException {
		Boolean b = false;
		Timestamp timestamp = new Timestamp(new Date().getTime());
		List<BankDetails> bankDetails = bankDetailsRequest.getBankDetails();
		List<Object[]> list = new ArrayList<>();
		for (BankDetails employeeBankDetails : bankDetails) {
			employeeBankDetails.setFlag(true);
			Object[] object = { employeeBankDetails.getBank_account_no(), employeeBankDetails.getBank_name(),
					employeeBankDetails.getBank_city(), employeeBankDetails.getBank_branch(),
					employeeBankDetails.getBank_ifsc_code(), employeeBankDetails.getBank_account_status(),
					employeeBankDetails.isIs_active(), employeeBankDetails.getUpdated_by(), timestamp,
					employeeBankDetails.isFlag(), employeeBankDetails.getEmployee_id() };
			list.add(object);
		}
		int[] batchUpdate = jdbcTemplate.batchUpdate(UPDATEBANKDETAILS, list);
		if (batchUpdate.length > 0) {
			b = true;
		}
		return b;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.obs.dao.BankDetailsDAO#deleteEmployeeBankDetails(int)
	 */
	@Override
	public boolean deleteEmployeeBankDetails(BankDetailsRequest bankDetailsRequest) throws SQLException {
		List<BankDetails> bankDetails = bankDetailsRequest.getBankDetails();
		List<Object[]> list = new ArrayList<>();
		boolean flag = false;
		boolean b = false;
		for (BankDetails employeeBankDetails : bankDetails) {
			employeeBankDetails.setFlag(true);
			Object[] object = { flag, employeeBankDetails.getEmployee_id() };
			list.add(object);
		}

		int[] delete = jdbcTemplate.batchUpdate(DELETEBANKDETAILS, list);

		if (delete.length > 0) {
			b = true;
		}
		return b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.obs.dao.BankDetailsDAO#getAllBankDetails()
	 */
	@Override
	public List<BankDetails> getAllBankDetails(BankDetailsRequest bankDetailsRequest) throws SQLException {
		logger.debug("incoming request in dao " + bankDetailsRequest);
		StringBuilder builder = new StringBuilder();
		builder.append(ALLRECORDS);

		List<BankDetails> bankDetails = bankDetailsRequest.getBankDetails();
		for (BankDetails bankObject : bankDetails) {
			if (bankDetailsRequest.getTransactionType().equalsIgnoreCase("getall")
					&& bankObject.getEmployee_id() != null) {
				builder.append(" and employee_id = " + bankObject.getEmployee_id());
				List<BankDetails> query = jdbcTemplate.query(builder.toString(),
						new BeanPropertyRowMapper<BankDetails>(BankDetails.class));
				logger.debug("response1 from dao dao " + query);
				return query;
			}

			if (bankDetailsRequest.getTransactionType().equalsIgnoreCase("getall") && bankObject.getId() != null) {
				builder.append(" and id = " + bankObject.getId());
				List<BankDetails> query = jdbcTemplate.query(builder.toString(),
						new BeanPropertyRowMapper<BankDetails>(BankDetails.class));
				logger.debug("response1 from dao dao " + query);
				return query;
			}
		}
		List<BankDetails> query = jdbcTemplate.query(builder.toString(),
				new BeanPropertyRowMapper<BankDetails>(BankDetails.class));
		logger.debug("response2 from dao dao " + query);
		return query;
	}

}
