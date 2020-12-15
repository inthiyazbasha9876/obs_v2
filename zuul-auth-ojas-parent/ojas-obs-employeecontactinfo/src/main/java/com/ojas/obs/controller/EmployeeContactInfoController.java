package com.ojas.obs.controller;

import static com.ojas.obs.constants.UserConstants.GET;
import static com.ojas.obs.constants.UserConstants.SET;

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

import com.ojas.obs.facadeimpl.EmployeeContactFacadeImpl;
import com.ojas.obs.model.EmployeeContactInfo;

import com.ojas.obs.requests.EmployeeContactInfoRequest;
import com.ojas.obs.response.ErrorResponse;

/**
 * 
 * @author ksaiKrishna
 * 
 */

@RestController
public class EmployeeContactInfoController {

	@Autowired
	private EmployeeContactFacadeImpl empFacade;

	Logger logger = Logger.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> setEmployeeContactInfo(
			@RequestBody EmployeeContactInfoRequest employeeContactInfoRequest, HttpServletRequest request,
			HttpServletResponse response) {
		ErrorResponse errorResponse = new ErrorResponse();
		logger.info("The request inside controller set method " + employeeContactInfoRequest);
		if ((employeeContactInfoRequest == null) || employeeContactInfoRequest.getEmpInfo().isEmpty()
				|| null == employeeContactInfoRequest.getTransactionType()
				|| employeeContactInfoRequest.getTransactionType().isEmpty()) {
			logger.error("List feild is null " + employeeContactInfoRequest);
			errorResponse.setStatusMessage("Request object is null");
			errorResponse.setStatusCode("422");
			return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
		} else if (employeeContactInfoRequest.getTransactionType().equalsIgnoreCase("save")
				|| employeeContactInfoRequest.getTransactionType().equalsIgnoreCase("update")) {

			for (EmployeeContactInfo empRequest : employeeContactInfoRequest.getEmpInfo()) {
				if (((empRequest.getAlternateMobileNo() == null) || (empRequest.getAlternateMobileNo().length() != 10)
						|| (empRequest.getAlternateMobileNo().isEmpty()))
						|| ((empRequest.getCurrentAddressLine1() == null)
								|| (empRequest.getCurrentAddressLine1().isEmpty()))
						|| ((empRequest.getCurrentAddressLine2() == null)
								|| (empRequest.getCurrentAddressLine2().isEmpty()))
						|| ((empRequest.getCurrentCity() == null) || (empRequest.getCurrentCity().isEmpty()))
						|| (empRequest.getCurrentState() == null) || (empRequest.getCurrentPin() == null)
						|| ((empRequest.getEmpId() == null) || (empRequest.getEmpId()).isEmpty())
						|| ((empRequest.getPermanentAddressLine1() == null)
								|| (empRequest.getPermanentAddressLine1().isEmpty()))) {

					errorResponse.setStatusCode("422");
					errorResponse.setStatusMessage("Improper save data");
					return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
		}
		if ((employeeContactInfoRequest.getTransactionType().equalsIgnoreCase("update")
				&& employeeContactInfoRequest.getEmpInfo().get(0).getId() == null
				|| employeeContactInfoRequest.getTransactionType().equalsIgnoreCase("delete")
						&& employeeContactInfoRequest.getEmpInfo().get(0).getId() == null)) {

			errorResponse.setStatusCode("422");
			errorResponse.setMessage("Id is not given");
			return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);

		}

		try {
			return empFacade.setEmployeeContactInfo(employeeContactInfoRequest);

		} catch (DuplicateKeyException exception) {
			logger.info(exception.getClass().getName());
			errorResponse.setStatusCode("409");
			errorResponse.setMessage("duplicate's are not allowed");
			errorResponse.setStatusMessage(exception.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		} catch (SQLException exception1) {
			errorResponse.setStatusCode("409");
			errorResponse.setMessage("duplicate's are not allowed");
			errorResponse.setStatusMessage(exception1.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		} catch (Exception exception2) {
			logger.error("Inside catch block");
			errorResponse.setStatusCode("409");
			errorResponse.setMessage("Request is Invalid");
			errorResponse.setStatusMessage(exception2.getMessage());
			logger.error("Exception raised");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		}
	}

	@PostMapping(GET)
	public ResponseEntity<Object> getEmployeeContactInfo(
			@RequestBody EmployeeContactInfoRequest employeeContactInfoRequest, HttpServletRequest request,
			HttpServletResponse response) {

		ErrorResponse error = new ErrorResponse();
		logger.debug("Enter the controller get method...");

		try {
			if (null == employeeContactInfoRequest) {
				logger.debug("request is not valid");
				error = new ErrorResponse();
				error.setMessage("request Object is null");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return empFacade.getEmployeeContactInfo(employeeContactInfoRequest);
		} catch (SQLException exception) {
			error = new ErrorResponse();
			logger.debug("SQL exception");
			error.setMessage("Exception");
			error.setStatusMessage(exception.getMessage());
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);

		} catch (Exception exception) {
			logger.debug("inside CostCenterController-SQLException catch block.****");
			error = new ErrorResponse();
			logger.debug("Exception is  invalid");
			error.setMessage("Exception");
			error.setStatusMessage(exception.getMessage());
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

	}
}
