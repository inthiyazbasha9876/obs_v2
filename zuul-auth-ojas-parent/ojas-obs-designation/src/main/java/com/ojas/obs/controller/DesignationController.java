package com.ojas.obs.controller;

import static com.ojas.obs.constants.DesignationServiceConstants.GET;

import static com.ojas.obs.constants.DesignationServiceConstants.SET;
import static com.ojas.obs.constants.DesignationServiceConstants.DELETE;
import static com.ojas.obs.constants.DesignationServiceConstants.DESIGNATION;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ojas.obs.facade.DesignationFacade;
import com.ojas.obs.model.Designation;
import com.ojas.obs.request.DesignationRequest;
import com.ojas.obs.utility.ErrorResponse;

/**
 * 
 * @author nsrikanth
 *
 */

@RestController
//@RequestMapping(DESIGNATION)
public class DesignationController {

	@Autowired
	private DesignationFacade designationFacade;
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * @param designationRequest
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */

	@RequestMapping(SET)
	public ResponseEntity<Object> setDesignation(@RequestBody DesignationRequest designationRequest,
			HttpServletRequest request, HttpServletResponse response) {
		String sessionId = null;
		logger.info("The incoming requests inside setDesignation controller method " + designationRequest);
		List<Designation> listDesignation = designationRequest.getDesignation();
		
		try {

		if (listDesignation == null || listDesignation.isEmpty()) {
			ErrorResponse error = new ErrorResponse();
			logger.error("ListDesignation is null " + listDesignation);
			error.setMessage("designationRequestobj is not valid");
			error.setStatusCode("422");
			return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
			for (Designation des : listDesignation) {
				if (null == des) {
					ErrorResponse error = new ErrorResponse();
					logger.error("Designation is null in DesignationController " + designationRequest);
					error.setMessage("Designation should not be null");
					error.setStatusCode("422");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				if ((designationRequest.getTransactionType().equalsIgnoreCase("update")
						|| designationRequest.getTransactionType().equalsIgnoreCase("delete")) && null == des.getId()) {
					ErrorResponse error = new ErrorResponse();
					logger.error("id is null in DesignationController " + designationRequest);
					error.setMessage("id can't be null");
					error.setStatusCode("422");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
			return designationFacade.setDesignation(designationRequest);

		} catch (DuplicateKeyException exception) {
			logger.error("Inside DuplicateKeyException catch block.");
			ErrorResponse error = new ErrorResponse();
			error.setMessage("duplicates are not allowed");
			error.setStatusMessage(exception.getMessage());
			error.setStatusCode("409");
			// exception.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (SQLException exception) {
			logger.error("Inside SQLException catch SQLblock.");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			error.setStatusCode("409");
			error.setMessage("SQLException");
			// exception.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception exception) {
			logger.error("Inside Exception catch Exception block.");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			error.setStatusCode("409");
			error.setMessage("Exception");
			// exception.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}

	/**
	 * 
	 * @param designationRequest
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */

	@RequestMapping(GET)
	public ResponseEntity<Object> getDesignation(@RequestBody DesignationRequest designationRequest,
			HttpServletRequest request, HttpServletResponse response) throws SQLException {
		try {

			logger.info("The incoming requests inside getDesignation controller method " + designationRequest);

			if (designationRequest == null) {
				ErrorResponse error = new ErrorResponse();
				logger.error("designationRequest is  null " + designationRequest);
				error.setMessage("designationRequest is not valid");
				error.setStatusCode("422");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return designationFacade.getDesignation(designationRequest);

		} catch (SQLException exception) {
			logger.error("Inside SQLException catch SQLblock.");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			error.setStatusCode("409");
			error.setMessage("SQLException");
			// exception.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception exception) {
			logger.error("Inside Exception catch Exception block.");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			error.setStatusCode("409");
			error.setMessage("Exception");
			// exception.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}

//	@PostMapping(DELETE)
//	public ResponseEntity<Object> deleteDesignation(@RequestBody DesignationRequest designationRequest,
//			HttpServletRequest request, HttpServletResponse response) throws Exception {
//		try {
//			logger.info("The request inside controller set method.." + designationRequest);
//
//			ErrorResponse error = new ErrorResponse();
//			if (designationRequest == null) {
//				error = new ErrorResponse();
//				logger.error("The Request object is null ..." + designationRequest);
//
//				error.setMessage("Requestobj is not valid");
//				error.setStatusCode("422");
//				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
//
//			}
//
//			return designationFacade.deleteDesignation(designationRequest);
//
//		} catch (Exception exception) {
//			logger.debug("inside CostCenterController-SQLException catch block.****");
//			ErrorResponse error = new ErrorResponse();
//			logger.debug("Exception is  invalid");
//			error.setStatusCode("409");
//			error.setMessage("Exception");
//			error.setStatusMessage(exception.getMessage());
//			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
//		}
//
//	}
}
