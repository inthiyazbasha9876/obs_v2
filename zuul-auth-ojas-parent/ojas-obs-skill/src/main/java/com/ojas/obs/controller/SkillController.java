package com.ojas.obs.controller;

import static com.ojas.obs.constants.Constants.GET;
import static com.ojas.obs.constants.Constants.SET;


import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.facade.SkillFacade;
import com.ojas.obs.model.ErrorResponse;
import com.ojas.obs.request.SkillRequest;

@RestController
//@RequestMapping(SKILL)
public class SkillController {

	@Autowired
	private SkillFacade skillFacade;
	Logger logger = Logger.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> setSkill(@RequestBody SkillRequest skillRequest, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Enter the set method controller...");
		ErrorResponse errorResponse = new ErrorResponse();
		ResponseEntity<Object> responseEntity = null;
		try {
			// checking requestobj

			if (null == skillRequest) {
				logger.debug("checkimg request object  null...");
				errorResponse.setStatusCode("422");
				errorResponse.setMessage("Request object is null");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}

		

			if (null == skillRequest.getTransactionType() || skillRequest.getTransactionType().isEmpty()) {
				logger.debug("checking transactiontype null....");
				errorResponse.setMessage("transationType is not valid");
				errorResponse.setStatusCode("422");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		
			responseEntity = skillFacade.setSkillInfo(skillRequest);

		} catch (DuplicateKeyException dke) {
			ErrorResponse error = new ErrorResponse();
			error.setMessage("duplicates are not alowed");
			error.setStatusCode("409");
			error.setStatusMessage(dke.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (SQLException se) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(se.getMessage());
			error.setStatusCode("409");
			error.setMessage("SQLException");
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Exception occurr...." + e.getMessage());
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(e.getMessage());
			error.setStatusCode("409");
			error.setMessage("Exception");
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		return responseEntity;
	}

	@PostMapping(GET)
	public ResponseEntity<Object> getSkillInfo(@RequestBody SkillRequest skillRequest, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("Enter the get method controller...");
		ErrorResponse error = new ErrorResponse();
		ResponseEntity<Object> responseEntity = null;
		try {
		

			if (skillRequest == null) {
				logger.debug("checkimg request object is null...");
				error.setMessage("Requestobj is not valid");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}


			if (null == skillRequest.getTransactionType() || skillRequest.getTransactionType().isEmpty()) {
				logger.debug("checkimg Request object Is null...");
				error.setMessage("transationType is not valid");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			responseEntity = skillFacade.getSkillInfo(skillRequest);

		} catch (SQLException se) {
			error.setStatusMessage(se.getMessage());
			error.setStatusCode("409");
			error.setMessage("SQLException");
			responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			logger.error(" Exception .....");
			error = new ErrorResponse();
			error.setStatusCode("409");
			error.setMessage("Exception");
			error.setStatusMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

		return responseEntity;
	}

}
