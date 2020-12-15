package com.ojas.obs.daoimpl;

import static com.ojas.obs.constants.SubBusinessUnitConstants.GETALLSUBBUSINESSUNITS;
import static com.ojas.obs.constants.SubBusinessUnitConstants.GETBYIDBUSINESSUNITS;
import static com.ojas.obs.constants.SubBusinessUnitConstants.SAVEBUSINESSUNIT;
import static com.ojas.obs.constants.SubBusinessUnitConstants.UPDATEBUSINESSUNITID;
import static com.ojas.obs.constants.SubBusinessUnitConstants.DELETEBUSINESSUNITID;
import static com.ojas.obs.constants.SubBusinessUnitConstants.GETSBUHEADS;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.obs.dao.SubBusinessUnitDao;
import com.ojas.obs.model.SubBusinessUnit;
import com.ojas.obs.request.SubBusinessUnitRequest;

/**
 * 
 * @author asuneel
 *
 */
@Repository
public class SubBusinessUnitDaoImpl implements SubBusinessUnitDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ojas.obs.dao.SubBusinessUnitDao#saveSubBusinessUnit(com.ojas.obs.request.
	 * SubBusinessUnitRequest)
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveSubBusinessUnit(SubBusinessUnitRequest subBusinessUnitRequest) throws SQLException {

		boolean flag = false;
		boolean status = true;
		int[] save;
		List<SubBusinessUnit> subBusinessUnitList = subBusinessUnitRequest.getSubBusinessUnitModel();
		List<Object[]> list = new ArrayList<>();
		for (SubBusinessUnit subBusinessUnit : subBusinessUnitList) {

			Object[] subBusiness = new Object[] { subBusinessUnit.getName(), subBusinessUnit.getBusinessUnitId(),subBusinessUnit.getSbuHead(),
				status };
			list.add(subBusiness);
		}
		save = jdbcTemplate.batchUpdate(SAVEBUSINESSUNIT, list);
		if (save.length > 0) {
			flag = true;
		}
		return flag;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.obs.dao.SubBusinessUnitDao#updateSubBusinessUnit(com.ojas.obs.
	 * request.SubBusinessUnitRequest)
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updateSubBusinessUnit(SubBusinessUnitRequest subBusinessUnitRequest) throws SQLException {

		boolean flag = false;
		int[] update;
		List<SubBusinessUnit> subBusinessUnitList = subBusinessUnitRequest.getSubBusinessUnitModel();
		List<Object[]> list = new ArrayList<>();
		for (SubBusinessUnit subBusinessUnit : subBusinessUnitList) {

			Object[] subBusiness = new Object[] { subBusinessUnit.getName(), subBusinessUnit.getBusinessUnitId(),subBusinessUnit.getSbuHead(),
				 subBusinessUnit.getId() };
			list.add(subBusiness);
		}
		update = jdbcTemplate.batchUpdate(UPDATEBUSINESSUNITID, list);
		if (update.length > 0) {
			flag = true;
		}
		return flag;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.obs.dao.SubBusinessUnitDao#getAllSubBusinessUnitDetails()
	 */
	@Override
	public List<SubBusinessUnit> getAllSubBusinessUnitDetails() throws SQLException {
			return jdbcTemplate.query(GETALLSUBBUSINESSUNITS,
					new BeanPropertyRowMapper<SubBusinessUnit>(SubBusinessUnit.class));
	}
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ojas.obs.dao.SubBusinessUnitDao#getByIdSubBusinessUnitDetails(java.lang.
	 * Integer)
	 */
	@Override
	public List<SubBusinessUnit> getByIdSubBusinessUnitDetails(Integer id) throws SQLException {

		Object[] params = new Object[] { id };
		return jdbcTemplate.query(GETBYIDBUSINESSUNITS, params,
				new BeanPropertyRowMapper<SubBusinessUnit>(SubBusinessUnit.class));

	} 

	@Override
	public boolean deleteSubBusinessUnit(SubBusinessUnitRequest subBusinessUnitRequest) throws SQLException {
		
		boolean status = false;
		int[] delete;
		List<SubBusinessUnit> subBusinessUnitList = subBusinessUnitRequest.getSubBusinessUnitModel();
		List<Object[]> list = new ArrayList<>();
		for (SubBusinessUnit subBusinessUnit : subBusinessUnitList) {

			Object[] subBusiness = new Object[] {subBusinessUnit.getId() };
			list.add(subBusiness);
		}
		delete = jdbcTemplate.batchUpdate(DELETEBUSINESSUNITID, list);
		if (delete.length > 0) {
			status = true;
		}
		
		return status;
	}

	@Override
	public List<String> getSbuHeads() {

		return jdbcTemplate.queryForList(GETSBUHEADS, String.class);
	}

}
