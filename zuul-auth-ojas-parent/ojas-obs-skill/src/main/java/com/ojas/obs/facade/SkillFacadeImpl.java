package com.ojas.obs.facade;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.dao.SkillDao;
import com.ojas.obs.model.ErrorResponse;
import com.ojas.obs.model.Skill;
import com.ojas.obs.request.SkillRequest;
import com.ojas.obs.response.SkillResponse;

@Service
public class SkillFacadeImpl implements SkillFacade {

	@Autowired
	private SkillDao skillDao;
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> setSkillInfo(SkillRequest skillRequest) throws SQLException {
		logger.debug("Enter the set method facade...");
		SkillResponse response = new SkillResponse();
		ResponseEntity<Object> responseEntity = null;
	

		if (skillRequest.getTransactionType().equalsIgnoreCase("save")) {
			logger.debug("checking transaction type save...");
			 skillDao.saveSkillInfo(skillRequest);
			response.setListOfSkill(new ArrayList<>());
			response.setStatusCode("200");
			response.setMessage("Successfully record added");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		if (skillRequest.getTransactionType().equalsIgnoreCase("update")) {
			List<Skill> listOfSkill = skillRequest.getListOfSkill();
			logger.debug("checking transaction type update...");
			for (Skill skillDetails : listOfSkill) {
				if (0 != skillDetails.getId()) {
					skillDao.updateSkillInfo(skillRequest);
					response.setListOfSkill(new ArrayList<>());
					response.setStatusCode("200");
					response.setMessage("Successfully record updated");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
			}

		}
		if (skillRequest.getTransactionType().equalsIgnoreCase("delete")) {
			boolean deleteSkill=skillDao.deleteSkill(skillRequest);
			logger.debug("checking transaction type delete...");
			
			if (deleteSkill) {
				 
				response.setListOfSkill(new ArrayList<>());
				response.setMessage("record deleted successfully");
				response.setStatusCode("200");
			
				return new ResponseEntity<>(response, HttpStatus.OK);
			}else {
				  ErrorResponse error = new ErrorResponse();
				  response.setListOfSkill(new ArrayList<>());
	                error.setMessage("not deleted");
	                return new ResponseEntity<>(error, HttpStatus.CONFLICT);
			}
			
		}
			
		
		return responseEntity;
		
	}

	@Override
	public ResponseEntity<Object> getSkillInfo(SkillRequest skillRequest) throws SQLException {

		logger.debug("enter the get method facade...");
		SkillResponse response = new SkillResponse();
		ResponseEntity<Object> responseEntity = null;

		if (skillRequest.getTransactionType().equalsIgnoreCase("getAll")) {
			List<Skill> listOfSkillInfo = skillDao.showSkillInfo(skillRequest);
			response.setListOfSkill(listOfSkillInfo);
			response.setStatusCode("200");
			response.setMessage("success");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		if (skillRequest.getTransactionType().equalsIgnoreCase("getById")) {
			List<Skill> listOfSkillInfo = skillDao.getById(skillRequest);
			response.setListOfSkill(listOfSkillInfo);
			response.setStatusCode("200");
			response.setMessage("success");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		return responseEntity;

	}

}
