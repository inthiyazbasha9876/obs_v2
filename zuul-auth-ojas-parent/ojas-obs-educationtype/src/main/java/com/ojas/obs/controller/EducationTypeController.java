package com.ojas.obs.controller;

import java.sql.SQLException;
import java.util.List;

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

import com.ojas.obs.constants.UserConstants;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.EmployeeEducationFacade;
import com.ojas.obs.model.EmployeeEducation;
import com.ojas.obs.modelrequest.EmployeeEducationRequest;

/**
 * 
 * @author mpraneethguptha
 *
 */
@RestController
//@RequestMapping("/obs/educationdetails")
public class EducationTypeController {

	@Autowired
	EmployeeEducationFacade employeeEducationFacade;
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * @param employeeEducationRequestObject
	 * @param httpServletRequest
	 * @param servletresponse
	 * @return
	 */
	@PostMapping("/set")
	public ResponseEntity<Object> setEmployeeEductaionInfo(
			@RequestBody EmployeeEducationRequest employeeEducationRequestObject, HttpServletRequest httpServletRequest,
			HttpServletResponse servletresponse) {

		logger.info("The request inside controller set method " + employeeEducationRequestObject);
		try {
			List<EmployeeEducation> listEmployeeEducations = employeeEducationRequestObject.getListEmployeeEducations();
			if (null == listEmployeeEducations || listEmployeeEducations.isEmpty()) {

				ErrorResponse error = new ErrorResponse();
				logger.error("List feild is null " + listEmployeeEducations);
				error.setMessage("Request is not valid");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			for (EmployeeEducation employeeEducation : listEmployeeEducations) {

				if (!employeeEducationRequestObject.getTransactionType().equalsIgnoreCase(UserConstants.DELETE) && (null == employeeEducation.getEducationType()
							|| employeeEducation.getEducationType().isEmpty())) {
						ErrorResponse error = new ErrorResponse();
						logger.error("Request does not contain transactiontype");
						error.setMessage("Request is invalid");
						error.setStatusCode("422");
						return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

				}
				if ((employeeEducationRequestObject.getTransactionType().equalsIgnoreCase(UserConstants.UPDATE)
						|| employeeEducationRequestObject.getTransactionType().equalsIgnoreCase(UserConstants.DELETE))
						&& (employeeEducation.getId() == null)) {
					logger.error("Request object id is null");
					ErrorResponse error = new ErrorResponse();
					error.setMessage("Request is InvalidRequest");
					error.setStatusCode("422");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
			return employeeEducationFacade.setEmployeeEducationInfo(employeeEducationRequestObject);

		} catch (DuplicateKeyException e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setMessage("duplicate's are not allowed");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (SQLException exception) {
			logger.error(" Inside catch block");
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage("Connection establishing error");
			error.setStatusMessage(exception.getMessage());
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception exception) {
			logger.error("Inside catch block");
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage("Request is Invalid");
			error.setStatusMessage(exception.getMessage());
			logger.error("Exception raised");
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
	@PostMapping("/get")
	public ResponseEntity<Object> getEductionDetails(@RequestBody EmployeeEducationRequest employeeEducationRequest,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws SQLException {

		logger.info("The request details in service get method"+employeeEducationRequest);
		try {
			logger.debug("requestObject received = " + employeeEducationRequest);
			if (null == employeeEducationRequest) {
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Data is invalid");
				logger.error("The request object is null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (!employeeEducationRequest.getTransactionType().equalsIgnoreCase("getAll")) {
				ErrorResponse error = new ErrorResponse();
				error.setMessage("Data is invalid");
				logger.error("Requested transaction type is not getall");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			return employeeEducationFacade.getEmployeeEducationInfo(employeeEducationRequest);

		} catch (DuplicateKeyException e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage("duplicate's are not allowed");
			error.setMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (SQLException exception) {
			logger.error(" Inside catch block");
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setStatusMessage("Connection establishing error");
			error.setMessage(exception.getMessage());
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception exception) {
			logger.error("Inside catch block");
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setStatusMessage("Request is Invalid");
			error.setMessage(exception.getMessage());
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
}
