package com.ojas.obs.dao;

import static com.ojas.obs.utility.Constants.DELETE_KYE;
import static com.ojas.obs.utility.Constants.GETALL_KYE;
import static com.ojas.obs.utility.Constants.GETALL_KYE_COUNT;
import static com.ojas.obs.utility.Constants.GETBYEMPID_KYE;
import static com.ojas.obs.utility.Constants.GETBYID_KYE;
import static com.ojas.obs.utility.Constants.GETMAILID;
import static com.ojas.obs.utility.Constants.SAVE_KYE;
import static com.ojas.obs.utility.Constants.UPDATE_AADHAR_KYE;
import static com.ojas.obs.utility.Constants.UPDATE_PAN_KYE;
import static com.ojas.obs.utility.Constants.UPDATE_PASSPORT_KYE;
import static com.ojas.obs.utility.Constants.UPDATE_PAN_KYE_STATUS;
import static com.ojas.obs.utility.Constants.UPDATE_AADHAR_KYE_STATUS;
import static com.ojas.obs.utility.Constants.UPDATE_PASSPORT_KYE_STATUS;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.KYE;
import com.ojas.obs.request.KYERequest;

@Repository
public class KyeDaoImpl implements KyeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public boolean saveKYE(KYERequest kyeRequest) throws Exception {
		List<KYE> kyeList = kyeRequest.getKye();
		boolean status = false;

		try {

			List<Object[]> inputList = new ArrayList<>();

			for (KYE kye : kyeList) {

				java.sql.Date issueDate = java.sql.Date.valueOf(kye.getPassport_date_of_Issue());
				java.sql.Date expireDate = java.sql.Date.valueOf(kye.getPassport_date_of_expiry());
				Object[] save = { kye.getkYE_Type(), kye.getUan(), kye.getkYE_address(), kye.getPassport_no(),
						issueDate, expireDate, kye.getPlace_of_issue(), kye.getPassport_address(), kye.getEmployee_Id(),
						false, kye.getCreated_by(), new Timestamp(new Date().getTime()), kye.getPassport_img(),
						kye.getPan_img(), kye.getAadhar_img(), kye.getAadhar_address(), kye.getPan_number(),
						kye.getAadhar_number(), kye.getPassport_status(), kye.getAadhar_status(), kye.getPan_status() };
				inputList.add(save);

				KeyHolder holder = new GeneratedKeyHolder();

				jdbcTemplate.update(new PreparedStatementCreator() {

					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(SAVE_KYE, Statement.RETURN_GENERATED_KEYS);
						ps.setString(1, kye.getkYE_Type());
						ps.setString(2, kye.getUan());
						ps.setString(3, kye.getkYE_address());
						ps.setString(4, kye.getPassport_no());
						ps.setDate(5, issueDate);
						ps.setDate(6, expireDate);
						ps.setString(7, kye.getPlace_of_issue());
						ps.setString(8, kye.getPassport_address());
						ps.setString(9, kye.getEmployee_Id());
						ps.setBoolean(10, true);
						ps.setString(11, kye.getCreated_by());
						ps.setString(12, kye.getCreated_date());
						ps.setString(13, kyeList.get(0).getPassport_img());
						ps.setString(14, kyeList.get(0).getPan_img());
						ps.setString(15, kyeList.get(0).getAadhar_img());
						ps.setString(16, kye.getAadhar_address());
						ps.setString(17, kye.getPan_number());
						ps.setString(18, kye.getAadhar_number());
						ps.setString(19, kye.getPassport_status());
						ps.setString(20, kye.getAadhar_status());
						ps.setString(21, kye.getPan_status());

						return ps;
					}
				}, holder);
				Number key = holder.getKey();
				if (key != null) {
					return true;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}


	
	@Override
	public boolean updateAadharKYE(KYERequest kyeRequest) throws SQLException {
		List<KYE> kyeList = kyeRequest.getKye();
		boolean status = false;

		try {
			List<Object[]> inputList = new ArrayList<>();

			for (KYE kye : kyeList) {
				Object[] update = 
								
					{kye.getAadhar_img(),kye.getUpdated_by(), new Timestamp(new Date().getTime()),kye.getId()};
				inputList.add(update);

				int updateId = jdbcTemplate.update(new PreparedStatementCreator() {

					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(UPDATE_AADHAR_KYE,
								Statement.RETURN_GENERATED_KEYS);
						ps.setString(1, kye.getAadhar_status());
						ps.setString(2, kye.getAadhar_img());
						ps.setString(3, kye.getUpdated_by());
						ps.setString(4, kye.getUpdated_date());
						ps.setString(5, kye.getEmployee_Id());
						ps.setInt(6, kye.getId());

						return ps;
					}
				});

				if (updateId == 1) {
					status = true;
				}

			}
		} catch (Exception e) {
			System.out.println("DataAccessException " + e.getMessage());
		}

		return status;
	}

	@Override
	public boolean updatePanKYE(KYERequest kyeRequest) throws SQLException {
		List<KYE> kyeList = kyeRequest.getKye();
		boolean status = false;

		try {
			List<Object[]> inputList = new ArrayList<>();

			for (KYE kye : kyeList) {
				Object[] update = 
								
					{ kye.getPan_img(), kye.getUpdated_by(), new Timestamp(new Date().getTime()),kye.getId()};
					inputList.add(update);

				int updateId = jdbcTemplate.update(new PreparedStatementCreator() {

					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(UPDATE_PAN_KYE,
								Statement.RETURN_GENERATED_KEYS);
						ps.setString(1, kye.getPan_status());
						ps.setString(2, kye.getPan_img());
						ps.setString(3, kye.getUpdated_by());
						ps.setString(4, kye.getUpdated_date());
						ps.setString(5, kye.getEmployee_Id());
						ps.setInt(6, kye.getId());

						return ps;
					}
				});

				if (updateId == 1) {
					status = true;
				}

			}
		} catch (Exception e) {
			System.out.println("DataAccessException " + e.getMessage());
		}

		return status;
	}

	@Override
	public boolean updatePassportKYE(KYERequest kyeRequest) throws SQLException {
		List<KYE> kyeList = kyeRequest.getKye();
		boolean status = false;

		try {
			List<Object[]> inputList = new ArrayList<>();

			for (KYE kye : kyeList) {
				Object[] update = 
								
					{ kye.getPassport_img(), kye.getUpdated_by(), new Timestamp(new Date().getTime()),kye.getId()};
					inputList.add(update);

				int updateId = jdbcTemplate.update(new PreparedStatementCreator() {

					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(UPDATE_PASSPORT_KYE,
								Statement.RETURN_GENERATED_KEYS);
						ps.setString(1, kye.getPassport_status());
						ps.setString(2, kye.getPassport_img());
						ps.setString(3, kye.getUpdated_by());
						ps.setString(4, kye.getUpdated_date());
						ps.setString(5, kye.getEmployee_Id());
						ps.setInt(6, kye.getId());

						return ps;
					}
				});

				if (updateId == 1) {
					status = true;
				}

			}
		} catch (Exception e) {
			System.out.println("DataAccessException " + e.getMessage());
		}

		return status;
	}
	
	@Override
	public boolean updatePanStatus(KYERequest kyeRequest) throws SQLException {
		List<KYE> kyeList = kyeRequest.getKye();
		boolean status = false;

		try {
			List<Object[]> inputList = new ArrayList<>();

			for (KYE kye : kyeList) {
				Object[] update = 
								
					{kye.getPan_status(),kye.getEmployee_Id(),kye.getId()};
					inputList.add(update);

				int updateId = jdbcTemplate.update(new PreparedStatementCreator() {

					@Override
					
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						
						PreparedStatement ps = connection.prepareStatement(UPDATE_PAN_KYE_STATUS,
								Statement.RETURN_GENERATED_KEYS);
						
						ps.setString(1, kye.getPan_status());
						
						ps.setString(2, kye.getEmployee_Id());
						
						ps.setInt(3, kye.getId());

						return ps;
					}
				});

				if (updateId == 1) {
					status = true;
				}

			}
		} catch (Exception e) {
			System.out.println("DataAccessException " + e.getMessage());
		}

		return status;
	}

	@Override
	public boolean deleteKYE(KYERequest kyeRequest) throws SQLException {
		List<Object[]> inputList = new ArrayList<>();
		logger.debug("@kyeRequest in KyeDaoImpl ::" + kyeRequest);
		List<KYE> kyeList = kyeRequest.getKye();
		boolean status = false;

		for (KYE kye : kyeList) {

			Object[] delete = { kye.isFlag(), kye.getId() };
			inputList.add(delete);
		}

		int[] batchUpdate = jdbcTemplate.batchUpdate(DELETE_KYE, inputList);

		if (batchUpdate.length > 0) {
			return true;
		}
		return status;

	}

	
	
	@Override
	public List<KYE> getAllKYE(KYERequest kyeRequest) throws SQLException {
		logger.debug("@kyeRequest in KyeDaoImpl ::" + kyeRequest);

		StringBuilder buffer = new StringBuilder();
		buffer.append(GETALL_KYE);
		List<KYE> query = jdbcTemplate.query(GETALL_KYE, new BeanPropertyRowMapper<>(KYE.class));
		logger.debug("the output from dao  method is " + query);
		return query;

	}

	@Override
	public int getAllKYECount() throws SQLException {

		return jdbcTemplate.queryForObject(GETALL_KYE_COUNT, Integer.class);

	}

	@Override
	public List<KYE> getByEmpId(KYERequest kyeRequest) {

		List<KYE> modelList = kyeRequest.getKye();
		List<Object[]> list = new ArrayList<>();
		Object[] param = null;
		List<KYE> query = null;

		for (KYE details : modelList) {
			param = new Object[] { details.getEmployee_Id() };
			list.add(param);
		}
		query = jdbcTemplate.query(GETBYEMPID_KYE, param, new BeanPropertyRowMapper<>(KYE.class));

		return query;

	}


	@Override
	public String getMailId(String empId) throws SQLException {
		logger.debug("Inside getMail method in dao");
		return jdbcTemplate.queryForObject(GETMAILID + empId, String.class);
	}

	@Override
	public List<KYE> getById(KYERequest kyeRequest) {
		List<KYE> modelList = kyeRequest.getKye();
		List<Object[]> list = new ArrayList<>();
		Object[] param = null;
		List<KYE> query = null;

		for (KYE details : modelList) {
			param = new Object[] { details.getId() };
			list.add(param);
		}
		query = jdbcTemplate.query(GETBYID_KYE, param, new BeanPropertyRowMapper<>(KYE.class));

		return query;

	}



	@Override
	public boolean updateAadharStatus(KYERequest kyeRequest) throws SQLException {
		List<KYE> kyeList = kyeRequest.getKye();
		boolean status = false;

		try {
			List<Object[]> inputList = new ArrayList<>();

			for (KYE kye : kyeList) {
				Object[] update = 
								
					{kye.getAadhar_status(),kye.getEmployee_Id(),kye.getId()};
					inputList.add(update);

				int updateId = jdbcTemplate.update(new PreparedStatementCreator() {

					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(UPDATE_AADHAR_KYE_STATUS,
								Statement.RETURN_GENERATED_KEYS);
						
						ps.setString(1, kye.getAadhar_status());
						
						ps.setString(2, kye.getEmployee_Id());
						
						ps.setInt(3, kye.getId());

						return ps;
					}
				});

				if (updateId == 1) {
					status = true;
				}

			}
		} catch (Exception e) {
			System.out.println("DataAccessException " + e.getMessage());
		}

		return status;
	}



	@Override
	public boolean updatePassportStatus(KYERequest kyeRequest) throws SQLException {
		List<KYE> kyeList = kyeRequest.getKye();
		boolean status = false;

		try {
			List<Object[]> inputList = new ArrayList<>();

			for (KYE kye : kyeList) {
				Object[] update = 
								
					{kye.getPassport_status(),kye.getEmployee_Id(),kye.getId()};
					inputList.add(update);

				int updateId = jdbcTemplate.update(new PreparedStatementCreator() {

					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(UPDATE_PASSPORT_KYE_STATUS,
								Statement.RETURN_GENERATED_KEYS);
						
						ps.setString(1, kye.getPassport_status());
						
						ps.setString(2, kye.getEmployee_Id());
						
						ps.setInt(3, kye.getId());

						return ps;
					}
				});

				if (updateId == 1) {
					status = true;
				}

			}
		} catch (Exception e) {
			System.out.println("DataAccessException " + e.getMessage());
		}

		return status;
	}

}
