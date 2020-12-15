package com.ojas.obs.controller;

import static com.ojas.obs.constants.Constants.GET;

import static com.ojas.obs.constants.Constants.SET;
import static com.ojas.obs.constants.Constants.VALIDATE;
import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.INVALIDEXCEPTION;
import static com.ojas.obs.constants.Constants.INVALIDREQUESTOBJ;
import static com.ojas.obs.constants.Constants.INVALIDSQLEXCEPTION;


import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.facade.CostCenterFacade;
import com.ojas.obs.model.CostCenter;
import com.ojas.obs.model.CostCenterRequest;
import com.ojas.obs.utility.ErrorResponse;

@RestController
//@RequestMapping("/obs/costcenterservice")
public class CostCenterController {

	@Autowired
	private CostCenterFacade costCenterService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> set(@RequestBody CostCenterRequest codeCenterRequest, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			logger.info("The request inside controller set method.." + codeCenterRequest);

			ErrorResponse error = new ErrorResponse();
			int code = 0;
			if (codeCenterRequest == null) {
				error = new ErrorResponse();
				logger.error("The Request object is null ..." + codeCenterRequest);

				error.setMessage(INVALIDREQUESTOBJ);
				error.setStatusCode("422");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}

			List<CostCenter> costCenter = codeCenterRequest.getCostCenter();

			if (!codeCenterRequest.getTransactionType().equalsIgnoreCase("DELETE")) {
				for (CostCenter costCenter2 : costCenter) {
					code = costCenter2.getCostCenterCode();
					if (costCenter2.getCostCenterCode() == 0) {
						logger.error("The costcentercode is zero or ...");
						error.setStatusCode("422");
						error = new ErrorResponse();
						error.setMessage("Request is  invalid????");
						return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					}
				}
			}
			if (null == codeCenterRequest.getTransactionType() || codeCenterRequest.getTransactionType().isEmpty()) {
				logger.error("checking transactiontype equal to null or empty...");

				error = new ErrorResponse();
				error.setMessage("transationType is not valid");
				error.setStatusCode("422");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (code >= 1000 && code <= 9999) {
				if ((codeCenterRequest.getTransactionType().equalsIgnoreCase("SAVE")
						|| codeCenterRequest.getTransactionType().equalsIgnoreCase("UPDATE")
						|| codeCenterRequest.getTransactionType().equalsIgnoreCase("DELETE"))) {
					ResponseEntity<Object> set2 = costCenterService.set(codeCenterRequest);
					return set2;

				} else { 

					error = new ErrorResponse();
					logger.error("The Request is invalid,..,..");
					error.setMessage("Request is  invalid....");
					return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

				}
			} else {
				error = new ErrorResponse();
				// logger.error("The Request is invalid,..,..");
				error.setMessage("Enter the four digit costcenter code.");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} catch (SQLException exception) {
			logger.error("Inside CostCenterController-SQLException catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.error(INVALIDSQLEXCEPTION);
			error.setStatusCode("409");
			error.setMessage("SQLException");
			error.setStatusMessage(exception.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT); 
		} catch (DuplicateKeyException dke) {
			ErrorResponse error = new ErrorResponse();
			logger.error("DuplicateKeyException......");
			error.setMessage("duplicates are not alowed");
			error.setStatusCode("409");
			error.setStatusMessage(dke.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception exception) {
			logger.debug("Inside CostCenterController-SQLException Catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.debug(INVALIDEXCEPTION);
			error.setStatusCode("409");
			error.setMessage("Exception");
			error.setStatusMessage(exception.getMessage());
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@PostMapping(GET)
	public ResponseEntity<Object> get(@RequestBody CostCenterRequest costCenterRequest, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {

			logger.info("Request Enter thecontroller get method..." + costCenterRequest);

			ErrorResponse error = null;

			if (costCenterRequest == null) {
				logger.error("Get Request object is null");
				error = new ErrorResponse();
				error.setMessage(INVALIDREQUESTOBJ);
				error.setStatusCode("409");
				return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
			}

			costCenterService.get(costCenterRequest);

			return costCenterService.get(costCenterRequest);
		} catch (SQLException exception) {
			logger.error("Inside CostCenterController-SQLException catch Block..****");
			ErrorResponse error = new ErrorResponse();
			logger.error(INVALIDSQLEXCEPTION);
			error.setStatusCode("409");
			error.setStatusMessage(exception.getMessage());
			error.setMessage("SQLException Occured");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception exception) {
			logger.error("inside CostCenterController-Exception catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.error(INVALIDEXCEPTION);
			error.setStatusMessage(exception.getMessage());
			error.setStatusCode("409");
			error.setMessage("Exception Occured");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@PostMapping(VALIDATE)
	public ResponseEntity<Object> validatCostCenter(@RequestBody CostCenterRequest costCenterRequest,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {

			logger.info("Request Enter thecontroller get method..." + costCenterRequest);

			ErrorResponse error = null;

			if (costCenterRequest == null) {
				logger.error("Get Request object is null");
				error = new ErrorResponse();
				error.setMessage(INVALIDREQUESTOBJ);
				error.setStatusCode("409");
				return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
			}

			return costCenterService.validateCostCenter(costCenterRequest);

		} catch (SQLException exception) {
			logger.error("Inside CostCenterController SQLException catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.error(INVALIDSQLEXCEPTION);
			error.setStatusCode("409");
			error.setStatusMessage(exception.getMessage());
			error.setMessage("SQLException Occured");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception exception) {
			logger.error("inside CostCenterController-Exception catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.error(INVALIDEXCEPTION);
			error.setStatusMessage(exception.getMessage());
			error.setStatusCode("409");
			error.setMessage("Exception Occured");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}

	@PostMapping(DELETE)
	public ResponseEntity<Object> deleteCenterCode(@RequestBody CostCenterRequest codeCenterRequest,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			logger.info("The request inside controller set method.." + codeCenterRequest);

			ErrorResponse error = new ErrorResponse();
			if (codeCenterRequest == null) {
				error = new ErrorResponse();
				logger.error("The Request object is null ..." + codeCenterRequest);

				error.setMessage("Requestobj is not valid");
				error.setStatusCode("422");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}

			return costCenterService.deleteCenterCode(codeCenterRequest);

		} catch (Exception exception) {
			logger.debug("inside CostCenterController-SQLException catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.debug(INVALIDEXCEPTION);
			error.setStatusCode("409");
			error.setMessage("Exception");
			error.setStatusMessage(exception.getMessage());
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}

	}
}
