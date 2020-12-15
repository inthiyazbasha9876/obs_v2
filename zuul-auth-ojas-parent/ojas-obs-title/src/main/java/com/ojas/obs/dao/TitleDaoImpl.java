package com.ojas.obs.dao;

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

import com.ojas.obs.model.Model;
import com.ojas.request.Request;

/**
 * 
 * @author uyashwanth
 *
 */
@Repository
public class TitleDaoImpl implements TitleDao {
	private static final String INSERT = "Insert into obs_title( Role, Title, EmployeeId, Createdby, Createddate, flag) values(?,?,?,?,?,?)";
	private static final String GET = "Select * from obs_title where flag='1' ";
	private static final String UPDATE = "Update obs_title set Role=?,Title=?,EmployeeId=?, Updatedby=?, Updateddate=? WHERE Id=?";
	private static final String DELETE = "Update obs_title set flag='0' where Id = ?";
	private static final String GETCOUNT = "select count(*) from obs_title where flag='1'";
	private static final String GETBYID = "Select * from obs_title where flag = 1 and id = ?";
	private static final String GETEMP = "Select * from obs_title where flag = 1 and EmployeeId = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	Logger logger = Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.obs.dao.TitleDao#saveTitle(com.ojas.request.Request)
	 */
	@Override
	public boolean saveTitle(Request request) throws SQLException {
		
		Timestamp timestamp = new Timestamp(new Date().getTime());
		int count = 0;
		boolean status = false;
		List<Model> modellist = request.getModel();
		for (Model model : modellist) {
			count = jdbcTemplate.update(INSERT, model.getRole(), model.getTitle(), model.getEmployeeId(),
					model.getCreatedby(), timestamp, model.isFlag());
		}
		if (count > 0)
			return true;
		else
			return status;
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.obs.dao.TitleDao#getAllTitle(com.ojas.request.Request)
	 */
	@Override
	public List<Model> getAllTitle(Request request) throws SQLException {
		
		return jdbcTemplate.query(GET, new BeanPropertyRowMapper<Model>(Model.class));

	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.obs.dao.TitleDao#updateTitle(com.ojas.request.Request)
	 */
	@Override
	public boolean updateTitle(Request request) throws SQLException {
		
		Timestamp timestamp = new Timestamp(new Date().getTime());
		int count = 0;
		List<Model> modellist = request.getModel();
		for (Model model : modellist) {
			count = jdbcTemplate.update(UPDATE, model.getRole(), model.getTitle(), model.getEmployeeId(),
					model.getUpdatedby(), timestamp, model.getId());
		}
		if (count > 0)
			return true;
		else
			return false;
	
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.obs.dao.TitleDao#deleteEmployeeRecord(java.lang.Integer)
	 */
	@Override
	public boolean deleteEmployeeRecord(Integer courseId) throws SQLException {
		
		int count = 0;
		boolean status = false;
		count = jdbcTemplate.update(DELETE, courseId);
		if (count > 0)
			return true;
		else
			return status;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.obs.dao.TitleDao#getAllRecordsCount()
	 */
	@Override
	public int getAllRecordsCount() throws SQLException {
		
		return jdbcTemplate.queryForObject(GETCOUNT, Integer.class);
	
		}

	@Override
	public List<Model> getById(Request request) throws SQLException {
		
		List<Model> modelList = request.getModel();
		List<Object[]> list = new ArrayList<Object[]>();
		Object[] param = null;
		for (Model details : modelList) {
			param = new Object[] { details.getId() };
			list.add(param);

		}
		List<Model> query = jdbcTemplate.query(GETBYID, param, new BeanPropertyRowMapper<>(Model.class));
		return query;
//		return jdbcTemplate.query(GETBYID, new BeanPropertyRowMapper<Model>(Model.class));
	
	}

	@Override
	public List<Model> getByEmpId(Request request) throws SQLException {
		
			List<Model> modelList = request.getModel();
			List<Object[]> list = new ArrayList<Object[]>();
			Object[] param = null;
			for (Model details : modelList) {
				param = new Object[] { details.getEmployeeId() };
				list.add(param);

			}
			List<Model> query = jdbcTemplate.query(GETEMP, param, new BeanPropertyRowMapper<>(Model.class));
			return query;
//			return jdbcTemplate.query(GETBYID, new BeanPropertyRowMapper<Model>(Model.class));
		
	}
}


