package com.ojas.obs.billingtype.controller;

import static com.ojas.obs.billingtype.constants.Constants.DELETE;
import static com.ojas.obs.billingtype.constants.Constants.GET;
import static com.ojas.obs.billingtype.constants.Constants.GETBYID;
import static com.ojas.obs.billingtype.constants.Constants.SAVE;
import static com.ojas.obs.billingtype.constants.Constants.SET;
import static com.ojas.obs.billingtype.constants.Constants.UPDATE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.billingtype.facadeimpl.BillingTypeFacadeImpl;
import com.ojas.obs.billingtype.model.BillingType;
import com.ojas.obs.billingtype.request.BillingTypeRequest;
import com.ojas.obs.billingtype.response.ErrorResponse;

@RestController
//@RequestMapping(BILLINGTYPE)
public class BillingTypeController {
	@Autowired
	private BillingTypeFacadeImpl billingTypeFacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> save(@RequestBody BillingTypeRequest billingTypeRequest) {
		logger.debug("request coming to the controller");
		try {
			List<BillingType> billingTypes = billingTypeRequest.getBillingList();
			if (billingTypes == null) {
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("request is empty");
				errorResponse.setStatusCode("422");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for (BillingType billingType : billingTypes) {
				if (billingTypeRequest.getTransactionType() == null || billingTypeRequest.getTransactionType().isEmpty()) {
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setMessage("transaction type is empty");
					errorResponse.setStatusCode("422");
					return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				if (billingTypeRequest.getTransactionType().equalsIgnoreCase(SAVE) &&
						 billingType.getName().isEmpty()) {
					logger.debug("field is empty in save method");
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setMessage("field is empty in save method");
					errorResponse.setStatusCode("422");
					return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				if ((billingTypeRequest.getTransactionType().equalsIgnoreCase(UPDATE)
						|| billingTypeRequest.getTransactionType().equalsIgnoreCase(DELETE)) && billingType.getId() == null
						|| billingType.getStatus() == null) {
					logger.debug("id or status is null");
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setMessage("id or status is null");
					errorResponse.setStatusCode("422");
					return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
			return billingTypeFacadeImpl.saveDetails(billingTypeRequest);
		} catch (DuplicateKeyException e) {
			logger.debug("DuplicateException");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("DuplicateException");
			errorResponse.setStatusCode("409");
			errorResponse.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

		} catch (Exception e) {
			logger.debug("Main Exception");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("Exception is occured");
			errorResponse.setStatusCode("409");
			errorResponse.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		}
	}

	@PostMapping(GET)
	public ResponseEntity<Object> getAllIssues(@RequestBody BillingTypeRequest request) {
		logger.debug("request coming to the controller");
		try {
			if (request.getTransactionType() == null || request.getTransactionType().isEmpty()) {
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("transaction type is empty");
				errorResponse.setStatusCode("422");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (request.getTransactionType().equalsIgnoreCase(GETBYID) && request.getBillingList().get(0).getId() == null) {

				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("id is null in getbyid");
				errorResponse.setStatusCode("422");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return billingTypeFacadeImpl.getAllDetails(request);
		} catch (DuplicateKeyException e) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("DuplicateKeyException is occured");
			errorResponse.setStatusCode("409");
			errorResponse.setStatusMessage("DuplicateKeyException");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		} catch (Exception e) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("Main Exception is occured");
			errorResponse.setStatusCode("409");
			errorResponse.setStatusMessage("Exception");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		}
	}
}
