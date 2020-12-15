package com.ojas.obs.emp_edu.controller;

import com.ojas.obs.emp_edu.facade.EmployeeEducationFacade;

import com.ojas.obs.emp_edu.model.EmployeeEducationDetailsRequest;
import com.ojas.obs.emp_edu.model.ErrorResponse;
import static com.ojas.obs.emp_edu.utility.Constants.SET;
import static com.ojas.obs.emp_edu.utility.Constants.GET;
import static com.ojas.obs.emp_edu.utility.Constants.TRANSACTIONTYPE_NULL;

import java.sql.SQLException;

import static com.ojas.obs.emp_edu.utility.Constants.REQUEST_NULL;
import static com.ojas.obs.emp_edu.utility.Constants.OBJECT_NULL;
import static com.ojas.obs.emp_edu.utility.Constants.EXCEPTION;
import static com.ojas.obs.emp_edu.utility.Constants.SQL_EXCEPTION;
import org.apache.log4j.Logger;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DuplicateKeyException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EmployeeEducationController {
	@Autowired
	EmployeeEducationFacade employeeEducationFacade;

	
	ResponseEntity<Object> responseEntity = null;
	
	Logger logger = Logger.getLogger(this.getClass());
	@PostMapping(value = SET)
	public ResponseEntity<Object> setEmployeeEducationDeatails(@RequestBody EmployeeEducationDetailsRequest  emplEduDetailsRequestObj,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
	{
		
		
		
		logger.debug("the input request contains " + emplEduDetailsRequestObj);
		
		if (null == emplEduDetailsRequestObj) {
			ErrorResponse error = new ErrorResponse();
			error.setStatuscode("422");
			error.setMessage(REQUEST_NULL);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (null == emplEduDetailsRequestObj.getEmployeeEducationDetailsList()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatuscode("422");
			error.setMessage(OBJECT_NULL);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if (null == emplEduDetailsRequestObj.getTransactionType()
				|| emplEduDetailsRequestObj.getTransactionType().isEmpty()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatuscode("422");
			error.setMessage(TRANSACTIONTYPE_NULL);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			responseEntity = employeeEducationFacade.setEmployeeEducationDetails(emplEduDetailsRequestObj);
			
			logger.debug("The response entity contains  " + responseEntity);
			return responseEntity;
		 } catch (SQLException se) {
			ErrorResponse error = new ErrorResponse();
			error.setStatuscode(String.valueOf(se.getErrorCode()));
			error.setStatusMessage(se.getMessage());
			error.setMessage(SQL_EXCEPTION);
			logger.debug("the Response Entity Contains  " + responseEntity);
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatuscode("409");
			error.setStatusMessage(e.getMessage());
			error.setMessage(EXCEPTION);
			logger.debug("The response entity Contains  " + responseEntity);
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		return responseEntity;

	}

	@PostMapping(value = GET)
	public ResponseEntity<Object> getEmployeeEducationDetails(@RequestBody EmployeeEducationDetailsRequest emplEduDetailsRequestObj,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		logger.debug("the input request contains " + emplEduDetailsRequestObj);
		if (null == emplEduDetailsRequestObj) {
			ErrorResponse error = new ErrorResponse();
			error.setStatuscode("422");
			error.setMessage(REQUEST_NULL);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if (null == emplEduDetailsRequestObj.getTransactionType()
				|| emplEduDetailsRequestObj.getTransactionType().isEmpty()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatuscode("422");
			error.setMessage(TRANSACTIONTYPE_NULL);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (null == emplEduDetailsRequestObj.getTransactionType()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatuscode("422");
			error.setMessage(TRANSACTIONTYPE_NULL);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		 try {
			responseEntity = employeeEducationFacade.getEmployeeEducationDetails(emplEduDetailsRequestObj);
		return responseEntity;
	 } catch (SQLException se) {
		ErrorResponse error = new ErrorResponse();
		error.setStatuscode(String.valueOf(se.getErrorCode()));
		error.setStatusMessage(se.getMessage());
		error.setMessage(SQL_EXCEPTION);
		responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
	} catch(DuplicateKeyException dke){
		ErrorResponse error = new ErrorResponse();
		error.setStatuscode("409");
		error.setStatusMessage(dke.getMessage());
		error.setMessage(EXCEPTION);
		responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}catch (Exception e) {
		ErrorResponse error = new ErrorResponse();
		error.setStatuscode("409");
		error.setStatusMessage(e.getMessage());
		error.setMessage(EXCEPTION);
		responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
		return responseEntity;
	}


	
}
