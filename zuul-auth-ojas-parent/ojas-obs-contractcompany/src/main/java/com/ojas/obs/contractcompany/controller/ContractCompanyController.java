package com.ojas.obs.contractcompany.controller;

import static com.ojas.obs.contractcompany.constants.Constants.DELETE;
import static com.ojas.obs.contractcompany.constants.Constants.GET;
import static com.ojas.obs.contractcompany.constants.Constants.GETBYID;
import static com.ojas.obs.contractcompany.constants.Constants.SAVE;
import static com.ojas.obs.contractcompany.constants.Constants.SET;
import static com.ojas.obs.contractcompany.constants.Constants.UPDATE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.contractcompany.facadeimpl.ContractCompanyFacadeImpl;
import com.ojas.obs.contractcompany.model.ContractCompany;
import com.ojas.obs.contractcompany.request.ContractCompanyRequest;
import com.ojas.obs.contractcompany.response.ErrorResponse;

@RestController
//@RequestMapping(CONTRACTCOMPANY)
public class ContractCompanyController {
	@Autowired
	private ContractCompanyFacadeImpl companyFacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> save(@RequestBody ContractCompanyRequest companyRequest) {
		logger.debug("request coming to the controller");
		try {
			List<ContractCompany> companies = companyRequest.getCompanyList();
			if (companies == null) {
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("request is empty");
				errorResponse.setStatusCode("422");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for (ContractCompany contractCompany : companies) {
				if (companyRequest.getTransactionType() == null || companyRequest.getTransactionType().isEmpty()) {
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setMessage("transaction type is empty");
					errorResponse.setStatusCode("422");
					return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				if (companyRequest.getTransactionType().equalsIgnoreCase(SAVE) 
						&& contractCompany.getCompanyName().isEmpty()) {
					logger.debug("field is empty in save method");
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setMessage("field is empty in save method");
					errorResponse.setStatusCode("422");
					return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				if (companyRequest.getTransactionType().equalsIgnoreCase(UPDATE) && contractCompany.getId() == null
						|| companyRequest.getTransactionType().equalsIgnoreCase(DELETE)
						&& contractCompany.getId() == null) {
					logger.debug("id or status is null");
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setMessage("id or status is null");
					errorResponse.setStatusCode("422");
					return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
			return companyFacadeImpl.saveDetails(companyRequest);
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
	public ResponseEntity<Object> getAllIssues(@RequestBody ContractCompanyRequest request) {
		logger.debug("request coming to the controller");
		try {
			if (request.getTransactionType() == null || request.getTransactionType().isEmpty()) {
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("transaction type is empty");
				errorResponse.setStatusCode("422");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (request.getTransactionType().equalsIgnoreCase(GETBYID)
					&& request.getCompanyList().get(0).getId() == null) {

				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("id is null in getbyid");
				errorResponse.setStatusCode("422");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return companyFacadeImpl.getAllDetails(request);
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
