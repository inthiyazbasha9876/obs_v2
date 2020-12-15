package com.ojas.obs.regforgot.dao;

import java.sql.SQLException;
import java.time.LocalDateTime;

import com.ojas.obs.regforgot.model.ForgotPassword;

public interface ForgotPasswordDao {
	public boolean update(ForgotPassword forgotRequest) throws SQLException;

	public String getMailId(String employeeId) throws SQLException;

	public boolean saveOtp(Integer otp, LocalDateTime expTime, String employeeId) throws SQLException;

	public ForgotPassword getForgotData(String employeeId) throws SQLException;

	public int deleteForgotData(String employeeId) throws SQLException;
}
