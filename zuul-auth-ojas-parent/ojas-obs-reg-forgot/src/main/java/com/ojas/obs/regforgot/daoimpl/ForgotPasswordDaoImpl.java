package com.ojas.obs.regforgot.daoimpl;

import static com.ojas.obs.regforgot.constants.UserConstants.GETOTPDATA;
import static com.ojas.obs.regforgot.constants.UserConstants.GETMAILID;
import static com.ojas.obs.regforgot.constants.UserConstants.SAVEOTP;
import static com.ojas.obs.regforgot.constants.UserConstants.UPDATEPASSWORD;
import static com.ojas.obs.regforgot.constants.UserConstants.DELETEFORGOTDATA;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.obs.regforgot.dao.ForgotPasswordDao;
import com.ojas.obs.regforgot.model.ForgotPassword;

@Repository
public class ForgotPasswordDaoImpl implements ForgotPasswordDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private PasswordEncoder passwordEncode;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Bean
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public String getMailId(String empId) throws SQLException{
		logger.debug("Inside getMail method in dao");
		return jdbcTemplate.queryForObject(GETMAILID + empId, String.class);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveOtp(Integer otp, LocalDateTime expTime, String empId) throws SQLException{
		boolean b = false;

		logger.debug("Inside saveOtp method in dao");
		int saveOtp = jdbcTemplate.update(SAVEOTP, empId, otp, expTime);
		if (saveOtp > 0) {
			logger.info("OTP saved successfully");
			b = true;
		}
		return b;
	}

	@Override
	public ForgotPassword getForgotData(String empId) throws SQLException{
		logger.debug("Inside getForgotData method in dao");
		ForgotPassword forgotPassword =  jdbcTemplate.queryForObject(GETOTPDATA + empId, new BeanPropertyRowMapper<ForgotPassword>(ForgotPassword.class));
		logger.debug("Fetched OTP data");
		 return forgotPassword;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean update(ForgotPassword forgotPassword) throws SQLException{
		logger.debug("Inside update method in dao");
		boolean b = false;
		int update = jdbcTemplate.update(UPDATEPASSWORD, passwordEncode.encode(forgotPassword.getNewPassword()), forgotPassword.getEmployeeId());
		if (update > 0) {
			b = true;
		}
		return b;
	}

	@Override
	public int deleteForgotData(String employeeId) throws SQLException{
		logger.debug("Inside delete data method in dao");
		return jdbcTemplate.update(DELETEFORGOTDATA + employeeId);
	}

}
