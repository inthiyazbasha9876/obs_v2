package com.obs.businessunit.controller;



import static com.obs.businessunit.constants.UserConstants.GET;
import static com.obs.businessunit.constants.UserConstants.GETBYID;
import static com.obs.businessunit.constants.UserConstants.SET;
import static com.obs.businessunit.constants.UserConstants.UPDATE;

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

import com.obs.businessunit.error.ErrorResponse;
import com.obs.businessunit.facade.BusinessUnitFacade;
import com.obs.businessunit.request.BusinessUnitRequest;

@RestController

public class BusinessUnitController {


	@Autowired
	BusinessUnitFacade businessUnitFacade;
	Logger logger = Logger.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> setBusinessUnit(@RequestBody BusinessUnitRequest businessUnitRequestobject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse)  {
		logger.debug("incoming request " + businessUnitRequestobject);
		try {	
			if (null == businessUnitRequestobject) {
				ErrorResponse error = new ErrorResponse();
				logger.debug("businessUnitRequestobject is not valid");
				error.setMessage("invalid data");
				error.setCode("422");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (null == businessUnitRequestobject.getBusinessUnit() || businessUnitRequestobject.getBusinessUnit().isEmpty())
					 {
				ErrorResponse error = new ErrorResponse();
				error.setMessage("fields must not be null");
				logger.debug("data is not valid");
				error.setCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (null == businessUnitRequestobject.getTransactionType() || businessUnitRequestobject.getTransactionType().isEmpty()){
				ErrorResponse error = new ErrorResponse();
				error.setMessage("transationType is not valid");
				error.setCode("422");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if ((businessUnitRequestobject.getTransactionType().equalsIgnoreCase(UPDATE)) && 
					businessUnitRequestobject.getBusinessUnit().get(0).getId() == null)
					{
				ErrorResponse error = new ErrorResponse();
				logger.debug("TransactionType is  invalid");
				error.setMessage("id is invalid");
				error.setCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}
			return businessUnitFacade.setBusinessUnit(businessUnitRequestobject);

		}catch (SQLException e) {
			logger.debug("inside businesscontroller-SQLException catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.debug("SQLException is  invalid");
			error.setMessage("SQLException");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		catch (DuplicateKeyException exception) {
			logger.debug("inside controller catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.debug("data is  invalid");
			error.setStatusMessage(exception.getMessage());
			error.setMessage("duplicates are not allowed");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		catch (Exception exception) {
			logger.debug("inside catch block.*******");
			ErrorResponse error = new ErrorResponse();
			error.setMessage(exception.getMessage());
			error.setCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		
		
	}

	@PostMapping(GET)
	public ResponseEntity<Object> getBusinessUnit(@RequestBody BusinessUnitRequest businessUnitRequest,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			
			logger.debug("requestObject received = " + businessUnitRequest);
	
			
			if (businessUnitRequest == null || businessUnitRequest.getTransactionType().isEmpty() || null == businessUnitRequest.getTransactionType()) {
				ErrorResponse error = new ErrorResponse();
				error.setMessage("statesRequestObj is not valid");
				error.setCode("422");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (businessUnitRequest.getTransactionType().equalsIgnoreCase(GETBYID) && businessUnitRequest.getBusinessUnit().get(0).getId() == null) {
				logger.error("Request is not valid, No id provided");
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setCode("422");
				errorResponse.setMessage("Type must be getall");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}	
			return businessUnitFacade.getBusinessUnit(businessUnitRequest);
		}catch (SQLException exception) {
			logger.debug("inside EmployeeEducationFacde-SQLException catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.debug("SQLException is  invalid");
			error.setMessage("SQLException");
			error.setStatusMessage(exception.getMessage());
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception exception) {
			logger.debug("inside catch block.*******");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}


}
