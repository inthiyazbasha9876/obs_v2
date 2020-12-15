package com.obs.lms.controller;

import static com.obs.lms.constants.Constants.DELETE;
import static com.obs.lms.constants.Constants.FAILED;
import static com.obs.lms.constants.Constants.GET;
import static com.obs.lms.constants.Constants.GETAllLEAVEINFO;
import static com.obs.lms.constants.Constants.GETBYID;
import static com.obs.lms.constants.Constants.GETFILE;
import static com.obs.lms.constants.Constants.IDNULL;
import static com.obs.lms.constants.Constants.REQUESTOBJECTNULL;
import static com.obs.lms.constants.Constants.SAVE;
import static com.obs.lms.constants.Constants.SET;
import static com.obs.lms.constants.Constants.TRANSATIONTYPENULL;
import static com.obs.lms.constants.Constants.UPDATE;
import static com.obs.lms.constants.Constants.UPDATESTATUS;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.obs.lms.facade.LmsFacadeImpl;
import com.obs.lms.request.LmsRequest;
import com.obs.lms.response.ErrorResponse;

@Controller
public class LmsController {
	@Autowired
	private LmsFacadeImpl lmsFacadeImpl;
	Logger logger = Logger.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> setLeave(@RequestBody LmsRequest lmsReq) {
		if (lmsReq == null || lmsReq.getTransationType() == null || lmsReq.getTransationType().isEmpty()) {
			logger.error(TRANSATIONTYPENULL);
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage(TRANSATIONTYPENULL);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			if (lmsReq.getTransationType().equalsIgnoreCase(UPDATESTATUS) && (lmsReq.getLeaveInfo().getId() == null
					|| lmsReq.getLeaveInfo().getEmpId() == null || lmsReq.getLeaveInfo().getStatus() == null
					|| lmsReq.getLeaveInfo().getUpdatedOn() == null || lmsReq.getLeaveInfo().getUpdatedBy() == null)) {
				logger.error(REQUESTOBJECTNULL);
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage(IDNULL);
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (lmsReq.getTransationType().equalsIgnoreCase(SAVE) && (lmsReq.getLeaveInfo().getAppliedOn() == null
					|| (lmsReq.getLeaveInfo().getApplyTo() == null) ||  (lmsReq.getLeaveInfo().getContactDetails() == null)
					|| (lmsReq.getLeaveInfo().getApplyType() == null)
					|| (lmsReq.getLeaveInfo().getCountNumOfDays() == null) || (lmsReq.getLeaveInfo().getEmpId() == null)
					|| (lmsReq.getLeaveInfo().getFlag() == null) || (lmsReq.getLeaveInfo().getFromDate() == null)
					|| (lmsReq.getLeaveInfo().getLeaveReason() == null) || lmsReq.getLeaveInfo().getLeaveType() == null
					|| lmsReq.getLeaveInfo().getSession1() == null || lmsReq.getLeaveInfo().getSession2() == null
					|| lmsReq.getLeaveInfo().getStatus() == null || lmsReq.getLeaveInfo().getToDate() == null)) {
				logger.error(REQUESTOBJECTNULL);
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage(REQUESTOBJECTNULL);
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (lmsReq.getLeaveInfo() == null
					|| lmsReq.getTransationType().equalsIgnoreCase(UPDATE) && lmsReq.getLeaveInfo().getId() == null
					|| lmsReq.getTransationType().equalsIgnoreCase(DELETE) && lmsReq.getLeaveInfo().getId() == null) {
				logger.error(IDNULL);
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage(IDNULL);
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return lmsFacadeImpl.setLms(lmsReq);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

	}

	@PostMapping(GET)
	public ResponseEntity<Object> getLeave(@RequestBody LmsRequest lmsReq) {
		try {
			if (lmsReq == null  || lmsReq.getTransationType() == null|| lmsReq.getTransationType().isEmpty()) {
				logger.debug(TRANSATIONTYPENULL);
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage("Bad Request");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}
			if ((lmsReq.getTransationType().equalsIgnoreCase(GETBYID) && lmsReq.getLeaveInfo().getId() == null)
					|| lmsReq.getTransationType().equalsIgnoreCase(GETAllLEAVEINFO)
							&& lmsReq.getLeaveInfo().getEmpId() == null
					|| lmsReq.getTransationType().equalsIgnoreCase(GETFILE) && lmsReq.getLeaveInfo().getId() == null) {
				logger.debug(IDNULL);
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage(FAILED);
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return lmsFacadeImpl.getLms(lmsReq);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

	}

}
