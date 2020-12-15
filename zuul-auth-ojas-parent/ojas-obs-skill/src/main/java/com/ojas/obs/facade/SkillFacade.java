package com.ojas.obs.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.request.SkillRequest;



public interface SkillFacade {
     ResponseEntity<Object> setSkillInfo(SkillRequest skillRequest) throws SQLException;
	
	ResponseEntity<Object> getSkillInfo(SkillRequest skillRequest) throws SQLException;

}
