package com.ojas.obs.projectDetails.controller;

import static com.ojas.obs.projectDetails.constants.UrlConstants.GET;
import static com.ojas.obs.projectDetails.constants.UrlConstants.SET;
import static com.ojas.obs.projectDetails.constants.UserConstants.DELETE;
import static com.ojas.obs.projectDetails.constants.UserConstants.IDISNULL;
import static com.ojas.obs.projectDetails.constants.UserConstants.PROJECTDETAILSOBJECTNULL;
import static com.ojas.obs.projectDetails.constants.UserConstants.REQUESTOBJECTNULL;
import static com.ojas.obs.projectDetails.constants.UserConstants.SAVE;
import static com.ojas.obs.projectDetails.constants.UserConstants.TRANSACTIONTYPENOTEXPECTED;
import static com.ojas.obs.projectDetails.constants.UserConstants.TRANSACTIONTYPENULL;
import static com.ojas.obs.projectDetails.constants.UserConstants.UPDATE;

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

import com.ojas.obs.projectDetails.Exception.ProjectDetailsException;
import com.ojas.obs.projectDetails.facade.ProjectDetailsFacade;
import com.ojas.obs.projectDetails.model.ErrorResponse;
import com.ojas.obs.projectDetails.request.ProjectDetailsRequest;
import com.ojas.obs.projectDetails.response.ProjectDetailsResponse;

@RestController
public class ProjectDetailsController {

	ResponseEntity<Object> responseEntity = null;
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	ProjectDetailsFacade projectDetailsFacade;

	@PostMapping(value = SET)
	public ResponseEntity<Object> setProjectDetails(@RequestBody ProjectDetailsRequest projectDetailsRequestObject,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws ProjectDetailsException, SQLException {
		logger.debug(
				" Received input data in ProjectDetailsController/setProjectDetails :" + projectDetailsRequestObject);

		if (projectDetailsRequestObject == null) {
			logger.error(" In ProjectDetailsController/ setProjectDetails:" + REQUESTOBJECTNULL);
			throw new ProjectDetailsException(REQUESTOBJECTNULL);
		}

		if (projectDetailsRequestObject.getTransactionType() == null) {
			logger.error(" In ProjectDetailsController :" + TRANSACTIONTYPENULL);
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage(TRANSACTIONTYPENULL);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

		}
		if (projectDetailsRequestObject.getProjectDetailsList() == null) {
			logger.error(" In ProjectDetailsController :" + PROJECTDETAILSOBJECTNULL);
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage(PROJECTDETAILSOBJECTNULL);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

		}
		if ((projectDetailsRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)
				|| projectDetailsRequestObject.getTransactionType().equalsIgnoreCase(DELETE))
				&& (projectDetailsRequestObject.getProjectDetailsList().stream()
						.anyMatch(projectDetails -> projectDetails.getId() == null))) {
			logger.error(" In ProjectDetailsController/setProjectDetails :" + IDISNULL);
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage(IDISNULL);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		if (!(projectDetailsRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)
				|| projectDetailsRequestObject.getTransactionType().equalsIgnoreCase(SAVE)
				|| projectDetailsRequestObject.getTransactionType().equalsIgnoreCase(DELETE))) {
			logger.error(" In ProjectDetailsController/setProjectDetails :" + TRANSACTIONTYPENOTEXPECTED);
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage(TRANSACTIONTYPENOTEXPECTED);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		try {

			ProjectDetailsResponse setProjectDetails = projectDetailsFacade
					.setProjectDetails(projectDetailsRequestObject);
			responseEntity = new ResponseEntity<>(setProjectDetails, HttpStatus.OK);
		}

		catch (SQLException exception) {
			ErrorResponse error = new ErrorResponse();
			
			error.setMessage("exception");
			error.setStatusMessage(exception.getMessage());
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);

	}catch (Exception exception) {
		logger.debug("inside CostCenterController-SQLException catch block.****");
		ErrorResponse error = new ErrorResponse();
		logger.debug("Exception is  invalid");
		error.setMessage("Exception");
		error.setStatusMessage(exception.getMessage());
		error.setStatusCode("409");
		return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
	}

		return responseEntity;

	}

	
	@PostMapping(GET)
	public ResponseEntity<Object> getKey(@RequestBody ProjectDetailsRequest projectDetailsRequestObject, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("@request in string" + projectDetailsRequestObject);
		try {
			logger.debug("@kyeRequestObj::" + projectDetailsRequestObject);
			if (projectDetailsRequestObject == null) {
				logger.debug("@request is not valid");
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("projectDetailsRequestObject is null");
				errorResponse.setStatusCode("422");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);

			}
			if (null == projectDetailsRequestObject.getTransactionType() || projectDetailsRequestObject.getTransactionType().isEmpty()) {
				logger.debug("transaction is not valid");
				ErrorResponse error = new ErrorResponse();
				error.setMessage("transactionType can't be null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return projectDetailsFacade.getProjectDetails(projectDetailsRequestObject);
		} catch (Exception exception) {
			logger.debug("inside CostCenterController-SQLException catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.debug("Exception is  invalid");
			error.setMessage("Exception");
			error.setStatusMessage(exception.getCause().getMessage());
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	
	

}
