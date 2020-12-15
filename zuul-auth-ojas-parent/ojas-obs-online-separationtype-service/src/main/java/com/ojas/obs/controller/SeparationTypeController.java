package com.ojas.obs.controller;

import static com.ojas.obs.constants.SeparationTypeConstants.GET;
import static com.ojas.obs.constants.SeparationTypeConstants.SET;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import com.ojas.model.Designation;

//port com.google.gson.Gson;

import com.ojas.obs.constants.SeparationTypeConstants;
import com.ojas.obs.facade.SeparationTypeFacade;
import com.ojas.obs.model.SeparationType;
import com.ojas.obs.request.SeparationTypeRequest;
import com.ojas.obs.utility.ErrorResponse;

/**
 * 
 * @author nsrikanth
 *
 */
@RestController
//@RequestMapping("/separationType")
public class SeparationTypeController {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SeparationTypeFacade separationTypeFacade;

	@RequestMapping(value = SET, method = RequestMethod.POST)
	public ResponseEntity<Object> setSeparationType(@RequestBody SeparationTypeRequest separationTypeRequest,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		ErrorResponse errorResponse = null;
		String transactionType = null;

		logger.info("The incoming request inside setSeparationType controller method "
				+ separationTypeRequest);
		// String transactionType = null;

		List<SeparationType> listSeparationType = separationTypeRequest.getSeparationType();

		try {
			if (null == listSeparationType || listSeparationType.isEmpty()) {
				errorResponse = new ErrorResponse();
				logger.error("ListSeparationType is null" + listSeparationType);
				errorResponse.setMessage("separationtypeRequest obj is not valid");
				errorResponse.setStatusCode("422");
				/*
				 * errorResponse.setStatusCode("422");
				 * errorResponse.setMessage(SeparationTypeConstants.INVALID_REQUEST);
				 */
				return new ResponseEntity<Object>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for (SeparationType sep : listSeparationType) {
				if (null == sep) {
				    ErrorResponse error = new ErrorResponse();
					logger.error("SeparationType is null in SeparationTypeController " + separationTypeRequest );
					error.setMessage("SeparationType should not be null");
					error.setStatusCode("422");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				if ((separationTypeRequest.getTransactionType().equalsIgnoreCase("update")
						|| separationTypeRequest.getTransactionType().equalsIgnoreCase("delete"))
						&& null == sep.getSeparationTypeId()) {
					ErrorResponse error = new ErrorResponse();
					logger.error("id is null in SeparationTypeController " + separationTypeRequest );
					error.setMessage("id can't be null");
					error.setStatusCode("422");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
			return separationTypeFacade.setSeparationTypeDetails(separationTypeRequest);

		} catch (DuplicateKeyException exception) {
			
			logger.error("Inside DuplicateKeyException catch block.");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			error.setMessage("duplicates are not allowed");
			error.setStatusCode("409");
			//exception.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (SQLException exception) {
			logger.error("Inside SQLException catch SQLblock.");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			error.setMessage("SQLException");
			error.setStatusCode("409");
			//exception.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception exception) {
			logger.error("Inside Exception catch Exception block.");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			error.setMessage("Exception");
			error.setStatusCode("422");
			//exception.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

	}

	@RequestMapping(value = GET, method = RequestMethod.POST)
	public ResponseEntity<Object> getAllSeparationType(@RequestBody SeparationTypeRequest separationTypeRequest,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		try {
			logger.info("The request Inside getAllSeparationType controller method " + separationTypeRequest);

			ErrorResponse errorResponse = null;
			if (null == separationTypeRequest) {
				errorResponse = new ErrorResponse();
				errorResponse.setStatusCode("422");
				errorResponse.setMessage(SeparationTypeConstants.INVALID_REQUEST);
				logger.error("separationTypeRequest is null" + separationTypeRequest);
				return new ResponseEntity<Object>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			return separationTypeFacade.getSeparationType(separationTypeRequest);

		} catch (SQLException exception) {
			logger.error("Inside SQLException catch Exception block.");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			error.setMessage("SQLException");
			error.setStatusCode("409");
			//exception.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception e) {
			//e.printStackTrace();
			logger.error("Inside Exception catch SQLblock.");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatusMessage(e.getMessage());
			errorResponse.setMessage("Exception");
			errorResponse.setStatusCode("409");

			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		}
	}

}
