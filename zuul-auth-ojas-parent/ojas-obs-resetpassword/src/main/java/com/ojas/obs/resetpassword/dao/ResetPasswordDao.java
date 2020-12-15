package com.ojas.obs.resetpassword.dao;
import java.sql.SQLException;
import java.sql.Timestamp;


public interface ResetPasswordDao {
	boolean savePassword(String encode, Timestamp updatedOn, String employeeId) throws SQLException;

	public String getMailId(String employeeId) throws SQLException;
	public String getPassword(String empId) throws SQLException;
	public boolean updatePassword(String encode, String updatedBy, String employeeId);
}
