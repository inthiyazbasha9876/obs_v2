package com.ojas.security.auth.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ojas.security.auth.dao.UserDao;
import com.ojas.security.auth.model.AppUser;
import com.ojas.security.auth.model.AppUserMapper;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public AppUser findUserAccount(String userName) {

		
		String sql = AppUserMapper.BASE_SQL + " where e.employeeId = ? ";
		Object[] params = new Object[] { userName };
		AppUserMapper mapper = new AppUserMapper();
		try {
			AppUser userInfo = jdbcTemplate.queryForObject(sql, params, mapper);
			return userInfo;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

 }
