package com.ojas.obs.controller;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.GETALLRECORDS;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.SETEMPLOYEEBUDETAILS;
import static com.ojas.obs.constants.Constants.UPDATE;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.EmpoyeeBUFacade;
import com.ojas.obs.model.EmployeeBUDetails;
import com.ojas.obs.request.EmployeeBUDetailsRequest;

/**
 * 
 * @author uyashwanth
 *
 */
@RestController
public class EmployeeBUController {

	@Autowired
	EmpoyeeBUFacade employeebuFacade;

	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * @param employeeBURequest
	 * @param servletRequest
	 * @param servletResponse
	 * @return
	 */
	@RequestMapping(SETEMPLOYEEBUDETAILS)
	public ResponseEntity<Object> setEmployeeBUDetails(@RequestBody EmployeeBUDetailsRequest employeeBUDetailsRequest,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {
		logger.debug("incoming requests " + employeeBUDetailsRequest);
		try {
			if (null == employeeBUDetailsRequest) {
				logger.error("Request is not valid");
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Request is Invalid");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			List<EmployeeBUDetails> budetails = employeeBUDetailsRequest.getEmployeeBUDeatils();
			if (null == budetails) {
				logger.error("Request is not valid");
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Request is Invalid");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}
			for(EmployeeBUDetails employeebudetails : budetails) {
			if ((employeeBUDetailsRequest.getTransactionType().equalsIgnoreCase(SAVE))
					&& ((employeebudetails.getEmployeeId() == null || employeebudetails.getEmployeeId().isEmpty())
							|| (employeebudetails.getSbu() == null))) {
				logger.error("Request is not valid");
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Request is Invalid");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if ((employeeBUDetailsRequest.getTransactionType().equalsIgnoreCase(UPDATE)
					|| employeeBUDetailsRequest.getTransactionType().equalsIgnoreCase(DELETE))
					&& (employeebudetails.getId() == null)) {
				logger.error("Request is not valid");
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Request is Invalid");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			}
			return employeebuFacade.setEmployeeBU(employeeBUDetailsRequest);
		} catch (SQLException exception) {
			ErrorResponse error = new ErrorResponse();
			exception.printStackTrace();
			error.setMessage("Exception");
			error.setStatusMessage(exception.getCause().getMessage());
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);

	}catch (Exception exception) {
		logger.debug("inside CostCenterController-SQLException catch block.****");
		ErrorResponse error = new ErrorResponse();
		logger.debug("Exception is  invalid");
		error.setMessage("Exception");
		error.setStatusMessage(exception.getCause().getMessage());
		error.setStatusCode("409");
		return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	}

	/**
	 * 
	 * @param courseRequest
	 * @param servletRequest
	 * @param servletResponse
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(GETALLRECORDS)
	public ResponseEntity<Object> getEmployeeBUDetails(@RequestBody EmployeeBUDetailsRequest employeeRequest,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {
		ErrorResponse error = null;
		try {
			if (null == employeeRequest) {
				logger.debug("request is not valid");
				error = new ErrorResponse();
				error.setMessage("REQUEST_NULL");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return employeebuFacade.getEmployeeBUDetails(employeeRequest);
		} 
		catch (SQLException exception) {
			error = new ErrorResponse();
			exception.printStackTrace();
			error.setMessage(exception.getMessage());
			error.setStatusMessage("SQL Exception");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);

	}catch (Exception exception) {
		logger.debug("inside CostCenterController-SQLException catch block.****");
		error = new ErrorResponse();
		logger.debug("Exception is  invalid");
		error.setStatusCode("409");
		error.setStatusMessage("Exception");
		error.setMessage(exception.getCause().getMessage());
		return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
}