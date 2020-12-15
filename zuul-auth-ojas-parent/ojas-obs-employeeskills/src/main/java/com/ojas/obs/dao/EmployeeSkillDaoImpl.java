
package com.ojas.obs.dao;

import static com.ojas.obs.constants.UserConstants.GETALL;
import static com.ojas.obs.constants.UserConstants.GETCOUNT;
import static com.ojas.obs.constants.UserConstants.SAVEEMPLOYEESKILLINFOSTMT;
import static com.ojas.obs.constants.UserConstants.UPDATEBYID;
import static com.ojas.obs.constants.UserConstants.GETBYID;
import static com.ojas.obs.constants.UserConstants.GETBYEMPID;


import java.util.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.EmployeeSkillInfo;
import com.ojas.obs.model.EmployeeSkillInfoRequest;


@Repository
public class EmployeeSkillDaoImpl implements EmployeeSkillDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	Logger logger = Logger.getLogger(this.getClass());



	@Override
	public int saveEmployeeSkillInfo(EmployeeSkillInfoRequest employeeSkillInfoRequest) throws SQLException {
		List<EmployeeSkillInfo> listEmployeeSkillInfo = employeeSkillInfoRequest.getSkillInfoModel();
		List<Object[]> inputList = new ArrayList<>();
		for(EmployeeSkillInfo skillDetails : listEmployeeSkillInfo) {
 
			logger.info("@@@@@Inside the save method()....");
			Object[] save = {skillDetails.getSkill_id(),
				skillDetails.getLevel_id(),
				skillDetails.getEmployee_id(),
				skillDetails.getCreated_by(),
				false,
				new Timestamp(new Date().getTime()) };
			inputList.add(save);
	}

		int[] batchUpdate = jdbcTemplate.batchUpdate(SAVEEMPLOYEESKILLINFOSTMT, inputList );
		if(batchUpdate.length > 0) {
			return 1;
		}
		return 0;

	}
	

	@Override
	public int updateEmployeeSkillInfo(EmployeeSkillInfoRequest employeeSkillInfoRequest) throws SQLException {

		List<EmployeeSkillInfo> listEmployeeSkillInfo = employeeSkillInfoRequest.getSkillInfoModel();
		List<Object[]> inputList = new ArrayList<>();
		for(EmployeeSkillInfo skillDetails : listEmployeeSkillInfo) {
			logger.debug("@@@@@Inside the update method()....");
		
				Object[] update = { 
						skillDetails.getEmployee_id(),
						skillDetails.getSkill_id(),
						skillDetails.getLevel_id(),
						skillDetails.getUpdate_by(),
				new Timestamp(new Date().getTime()),
				skillDetails.getId()};
	inputList.add(update);
		}

		int[] batchUpdate = jdbcTemplate.batchUpdate(UPDATEBYID, inputList );
		if(batchUpdate.length>0) {
			return 1;
		}
		return 0;
	}

	@Override
	public List<EmployeeSkillInfo> showEmployeeSkillInfo(EmployeeSkillInfoRequest employeeSkillInfoRequest)throws SQLException {
		logger.debug("@@@@@Inside the show  method()....");

		return jdbcTemplate.query(GETALL, new BeanPropertyRowMapper<EmployeeSkillInfo>(EmployeeSkillInfo.class));
	
}

	@Override
	
	public int getAllCount() throws SQLException {
		logger.debug("@@@@@Inside the getAll method()....");
		

		return jdbcTemplate.queryForObject(GETCOUNT, Integer.class);
	}

	




	@Override
	public List<EmployeeSkillInfo> getById(EmployeeSkillInfoRequest employeeSkillInfoRequest) throws SQLException {
		logger.debug("@@@@@Inside the getById method()....");
	
		List<EmployeeSkillInfo> modelList = employeeSkillInfoRequest.getSkillInfoModel();
		List<Object[]> list = new ArrayList<>();
		Object[] param = null;
		List<EmployeeSkillInfo> query = null;

		for (EmployeeSkillInfo details : modelList) {
			param = new Object[] { details.getId() };
			list.add(param);
		}
		query = jdbcTemplate.query(GETBYID, param, new BeanPropertyRowMapper<>(EmployeeSkillInfo.class));

		return query;
		
	}

    @Override
	public List<EmployeeSkillInfo> getByEmpId(EmployeeSkillInfoRequest employeeSkillInfoRequest) throws SQLException {
    	logger.debug("@@@@@Inside the empId method()....");
	
    	List<EmployeeSkillInfo> modelList = employeeSkillInfoRequest.getSkillInfoModel();
		List<Object[]> list = new ArrayList<>();
		Object[] param = null;
		List<EmployeeSkillInfo> query = null;

		for (EmployeeSkillInfo details : modelList) {
			param = new Object[] { details.getEmployee_id() };
			list.add(param);
		}
		query = jdbcTemplate.query(GETBYEMPID, param, new BeanPropertyRowMapper<>(EmployeeSkillInfo.class));

		return query;
    		
	}

}
