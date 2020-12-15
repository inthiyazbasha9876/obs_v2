package com.ojas.security.auth.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class AppUserMapper implements RowMapper<AppUser> {


	public static final String BASE_SQL = "Select e.employeeId, e.password, e.pwdChanged From obs_employee_login e ";



	@Override
	public AppUser mapRow(ResultSet rs, int arg1) throws SQLException {
		
		String userId = rs.getString("employeeId"); 
		String encrytedPassword = rs.getString("password"); 
		boolean pwdChanged = rs.getBoolean("pwdChanged");
		return new AppUser(userId,encrytedPassword, pwdChanged);
	}

}
