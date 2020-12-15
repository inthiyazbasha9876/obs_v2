package com.ojas.obs.controller;

import static com.ojas.obs.constants.UrlConstants.GET;
import static com.ojas.obs.constants.UrlConstants.SET;
import static com.ojas.obs.constants.UserConstants.EMPLOYEEEXPERINCEDETAILSOBJECTNULL;
import static com.ojas.obs.constants.UserConstants.REQUESTOBJECTNULL;
import static com.ojas.obs.constants.UserConstants.TRANSACTIONTYPENULL;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.facade.ExperienceFacade;
import com.ojas.obs.model.ErrorResponse;
import com.ojas.obs.request.ExperienceRequest;
import com.ojas.obs.response.ExperienceResponse;


@RestController
public class ExprienceController {

	ResponseEntity<Object> responseEntity = null;
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	ExperienceFacade experienceFacade;

	@PostMapping(value = SET)
	public ResponseEntity<Object> setEmployeeExprienceDetails(@RequestBody ExperienceRequest experienceRequest,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		logger.info("entered into set method in controller class");
		logger.debug(" Received input data in EmployeeExperienceDetailssController :" + experienceRequest);
	
		try {
			if (experienceRequest == null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage(REQUESTOBJECTNULL);
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (experienceRequest.getTransactionType() == null) {
				ErrorResponse error = new ErrorResponse();
				logger.error("TransactionType is not valid");
				error.setStatusCode("422");
				error.setMessage(TRANSACTIONTYPENULL);
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (experienceRequest.getEmployeeExperienceDetails() == null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage(EMPLOYEEEXPERINCEDETAILSOBJECTNULL);
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			responseEntity = experienceFacade
					.setEmployeeExperienceDetails(experienceRequest);
		
			
			return responseEntity ;
		} catch (SQLException e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode(String.valueOf(e.getErrorCode()));
			error.setMessage(e.getMessage());
			error.setStatusMessage("SQLException");
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

		catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setStatusMessage("Exception");
			error.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

		return responseEntity;
	}

	@PostMapping(value = GET)
	public ResponseEntity<Object> getEmployeeExprienceDetails(@RequestBody ExperienceRequest experienceRequest,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		logger.info("entered into get method in controller class");
		logger.debug(" Received input data in EmployeeExperienceDetailssController :" + experienceRequest);
		try {
			if (experienceRequest == null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage(REQUESTOBJECTNULL);
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (experienceRequest.getTransactionType() == null) {
				ErrorResponse error = new ErrorResponse();
				logger.error("TransactionType is not valid");
				error.setStatusCode("422");
				error.setMessage(TRANSACTIONTYPENULL);
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			responseEntity= experienceFacade
					.getEmployeeExperienceDetails(experienceRequest);
			return responseEntity ;
		} catch (SQLException e) {
			ErrorResponse error = new ErrorResponse();
			logger.error("Request is not valid" + error);
			error.setStatusCode(String.valueOf(e.getErrorCode()));
			error.setMessage("sql exception");
			error.setStatusMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			logger.error("Request is not valid" + error);
			error.setStatusCode("422");
			error.setStatusMessage("Exception");
			error.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		return responseEntity;
	}

}
