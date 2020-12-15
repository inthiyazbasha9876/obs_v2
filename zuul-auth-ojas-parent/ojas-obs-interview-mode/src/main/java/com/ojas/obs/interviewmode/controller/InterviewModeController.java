package com.ojas.obs.interviewmode.controller;

import static com.ojas.obs.interviewmode.constants.Constants.DELETE;
import static com.ojas.obs.interviewmode.constants.Constants.GET;
import static com.ojas.obs.interviewmode.constants.Constants.GETBYID;
import static com.ojas.obs.interviewmode.constants.Constants.SAVE;
import static com.ojas.obs.interviewmode.constants.Constants.SET;
import static com.ojas.obs.interviewmode.constants.Constants.UPDATE;

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

import com.ojas.obs.interviewmode.facadeimpl.InterviewModeFacadeImpl;
import com.ojas.obs.interviewmode.model.InterviewMode;
import com.ojas.obs.interviewmode.request.InterviewModeRequest;
import com.ojas.obs.interviewmode.response.ErrorResponse;

@RestController
//@RequestMapping(BILLINGTYPE)
public class InterviewModeController {
	@Autowired
	private InterviewModeFacadeImpl interviewModeFacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> save(@RequestBody InterviewModeRequest interviewModeRequest) {
		logger.debug("request coming to the controller");
		try {
			List<InterviewMode> interviewModes = interviewModeRequest.getInterviewmodeList();
			if (interviewModes == null) {
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("request is empty");
				errorResponse.setStatusCode("422");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for (InterviewMode interviewMode : interviewModes) {
				if (interviewModeRequest.getTransactionType() == null
						|| interviewModeRequest.getTransactionType().isEmpty()) {
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setMessage("transaction type is empty");
					errorResponse.setStatusCode("422");
					return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				if (interviewModeRequest.getTransactionType().equalsIgnoreCase(SAVE)
						&& interviewMode.getInterviewMode().isEmpty()) {
					logger.debug("field is empty in save method");
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setMessage("field is empty in save method");
					errorResponse.setStatusCode("422");
					return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				if ((interviewModeRequest.getTransactionType().equalsIgnoreCase(UPDATE)
						|| interviewModeRequest.getTransactionType().equalsIgnoreCase(DELETE))
						&& interviewMode.getInterviewmodeId() == null || interviewMode.getStatus() == null) {
					logger.debug("id or status is null");
					ErrorResponse errorResponse = new ErrorResponse();
					errorResponse.setMessage("id or status is null");
					errorResponse.setStatusCode("422");
					return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
			return interviewModeFacadeImpl.saveDetails(interviewModeRequest);
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
	public ResponseEntity<Object> getAllIssues(@RequestBody InterviewModeRequest request) {
		logger.debug("request coming to the controller");
		try {
			if (request.getTransactionType() == null || request.getTransactionType().isEmpty()) {
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("transaction type is empty");
				errorResponse.setStatusCode("422");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (request.getTransactionType().equalsIgnoreCase(GETBYID)
					&& request.getInterviewmodeList().get(0).getInterviewmodeId() == null) {

				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("id is null in getbyid");
				errorResponse.setStatusCode("422");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return interviewModeFacadeImpl.getAllDetails(request);
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
