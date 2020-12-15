package com.ojas.obs.resetpassword.daoimpl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.obs.resetpassword.dao.ResetPasswordDao;

@Repository
public class ResetPasswordDaoImpl implements ResetPasswordDao {
	public static final String UPDATE_PASSWORD = "update ojas_obs.obs_employee_login set password = ?, updatedOn=? where employeeId=?";
	private static final String RESETPWDSTMT = "update ojas_obs.obs_employee_login set password = ?, pwdChanged=?, updatedBy=?, updatedOn=? where employeeId=?";
	private static final String GETPWDSTMT = "select password from ojas_obs.obs_employee_login where employeeId=";
	public static final String GETMAILID = "select email from ojas_obs.obs_employeeinfo where employee_Id= ";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	Logger logger = Logger.getLogger(this.getClass());

@Override
	public String getMailId(String employeeId) throws SQLException {
		logger.debug("Inside getMail method in dao");

		StringBuffer bufffer = new StringBuffer();

		bufffer.append(GETMAILID + "'" + employeeId + "'");
		logger.info(bufffer);

		String string = jdbcTemplate.queryForObject(bufffer.toString(), String.class);
		System.out.println(string);
		return string;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean savePassword(String encode, Timestamp updatedOn, String employeeId) throws SQLException {
		boolean b = false;
		int save = 0;
		logger.debug("entered into update password");
		save = jdbcTemplate.update(UPDATE_PASSWORD, encode, updatedOn, employeeId);
		if (save > 0) {
			logger.debug("entered into update password true value");
			b = true;
		}
		return b;
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updatePassword(String encoded, String updatedBy, String empId) {

		boolean b = false;
		int update = 0;
		/*
		 * List<Object[]> list = new ArrayList<>();
		 * 
		 * Object[] pwd = new Object[] {
		 * this.passwordEncode.encode(password.getNewPassword()),
		 * password.getUpdatedBy(), new Timestamp(new Date().getTime()),
		 * password.getEmployeeId(),
		 * this.passwordEncode.encode(password.getCurruntPassword()) }; list.add(pwd);
		 */
			update = jdbcTemplate.update(RESETPWDSTMT, encoded, true,
					updatedBy, new Timestamp(new Date().getTime()), empId);
		if (update > 0) {
			b = true;
		}
		return b;
	}
	@Override
	public String getPassword(String empId) {
		
		return jdbcTemplate.queryForObject(GETPWDSTMT + empId, String.class);
	}


}
