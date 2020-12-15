package com.ojas.security.auth.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ojas.security.auth.dao.RoleDao;

@Repository
public class RoleDaoImpl implements RoleDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<String> getRoleNames(String employeeId)//(Long userId)
	{
		
		String sql = "Select r.role_name from user_role ur, app_role r where ur.role_id = r.role_id and ur.employee_Id = ? ";
		Object[] params = new Object[] { employeeId };
		List<String> roles = jdbcTemplate.queryForList(sql, params, String.class);
		return roles;
	}
	
	
}
