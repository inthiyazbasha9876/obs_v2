package com.ojas.obs.dao;

import static com.ojas.obs.constants.UserConstants.DELETESKIL;
import static com.ojas.obs.constants.UserConstants.GETALL;
import static com.ojas.obs.constants.UserConstants.GETBYID;
import static com.ojas.obs.constants.UserConstants.GETCOUNT;
import static com.ojas.obs.constants.UserConstants.SAVESKIL;
import static com.ojas.obs.constants.UserConstants.UPDATESKIL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ojas.obs.model.Skill;
import com.ojas.obs.request.SkillRequest;

@Repository
public class SkillDaoImpl implements SkillDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public int saveSkillInfo(SkillRequest skillRequest) throws SQLException {
		logger.debug("executing save method()...");
		List<Skill> listOfSkill = skillRequest.getListOfSkill();
		List<Object[]> inputList = new ArrayList<>();
		boolean status = true;
		for (Skill skillList : listOfSkill) {

			Object[] save = { skillList.getSkill_name(), status };
			inputList.add(save);
		}
		int[] batchUpdate = jdbcTemplate.batchUpdate(SAVESKIL, inputList);
		if (batchUpdate.length > 0) {
			return 1;
		}
		return 0;

	}

	@Override
	public int updateSkillInfo(SkillRequest skillRequest) throws SQLException {
		logger.debug("enter into the update method()...");

		List<Skill> listSkillInfo = skillRequest.getListOfSkill();
		List<Object[]> inputList = new ArrayList<>();

		for (Skill skillDetails : listSkillInfo) {

			Object[] update = { skillDetails.getSkill_name(), skillDetails.getId() };
			inputList.add(update);
		}
		int[] batchUpdate = jdbcTemplate.batchUpdate(UPDATESKIL, inputList);
		if (batchUpdate.length > 0) {
			return 1;
		}
		return 0;

	}

	@Override
	public List<Skill> showSkillInfo(SkillRequest skillRequest) throws SQLException {
		logger.debug("inside the show method()");

		return jdbcTemplate.query(GETALL, new BeanPropertyRowMapper(Skill.class));

	}

	@Override
	public int getAllCount() throws SQLException {

		return jdbcTemplate.queryForObject(GETCOUNT, Integer.class);

	}

	@Override
	public List<Skill> getById(SkillRequest skillRequest) throws SQLException {
		List<Object[]> inputList = new ArrayList<>();
		List<Skill> listOfSkill = skillRequest.getListOfSkill();
		Object[] update = null;

		for (Skill skill : listOfSkill) {

			update = new Object[] { skill.getId() };
			inputList.add(update);
		}
		return jdbcTemplate.query(GETBYID, update, new BeanPropertyRowMapper<>(Skill.class));
	}

	public boolean deleteSkill(SkillRequest skillRequest) throws SQLException {
		List<Object[]> inputList = new ArrayList<>();
		int[] delete;
		boolean status=false;
		List<Skill> listOfSkill = skillRequest.getListOfSkill();

		for (Skill skill : listOfSkill) {
			Object[] role = new Object[] { skill.getId() };
			inputList.add(role);
		}

		delete = jdbcTemplate.batchUpdate(DELETESKIL, inputList);
		if (delete.length > 0)
			status = true;

		return status;

	}

}
