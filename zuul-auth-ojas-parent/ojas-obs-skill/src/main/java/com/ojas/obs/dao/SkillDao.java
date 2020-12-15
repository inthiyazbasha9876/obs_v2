package com.ojas.obs.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.model.Skill;
import com.ojas.obs.request.SkillRequest;

public interface SkillDao {

	public int saveSkillInfo(SkillRequest skillRequest)throws SQLException ;

	public int updateSkillInfo(SkillRequest skillRequest)throws SQLException ;

	public List<Skill> showSkillInfo(SkillRequest skillRequest)throws SQLException ;
	
	public List<Skill> getById(SkillRequest skillRequest)throws SQLException ;
	
	public boolean deleteSkill(SkillRequest skillRequest) throws SQLException;

	public int getAllCount() throws SQLException;

	

}
