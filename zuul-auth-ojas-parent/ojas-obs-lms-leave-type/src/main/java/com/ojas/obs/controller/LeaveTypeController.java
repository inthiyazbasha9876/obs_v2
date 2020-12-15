package com.ojas.obs.controller;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.UPDATE;

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
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.facadeimpl.LeaveTypeFacadeImpl;
import com.ojas.obs.model.LeaveType;
import com.ojas.obs.request.LeaveTypeRequest;
import com.ojas.obs.response.ErrorResponse;

@RestController
public class LeaveTypeController {
	@Autowired
	private LeaveTypeFacadeImpl leavetypefacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(value = "/set")
	public ResponseEntity<Object> saveDetails(@RequestBody LeaveTypeRequest leaveTypeRequestObject,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("data coming into controller");

		List<LeaveType> leave = leaveTypeRequestObject.getLeaveTypeList();
		try {
			if (leaveTypeRequestObject.getLeaveTypeList() == null || leave.isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("reqest object is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for (LeaveType g : leave) {
				if (leaveTypeRequestObject.getTransactionType().equalsIgnoreCase(SAVE)
						&& g.getLeaveTypeName().isEmpty()) {
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("422");
					error.setStatusMessage(" Field is null");
					return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

				}

				if (leaveTypeRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)
						&& leaveTypeRequestObject.getLeaveTypeList().get(0).getLeaveTypeId() == null
						|| leaveTypeRequestObject.getTransactionType().equalsIgnoreCase(DELETE)
								&& leaveTypeRequestObject.getLeaveTypeList().get(0).getLeaveTypeId() == null) {
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("422");
					error.setStatusMessage(" Field is null");
					return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

				}
			}
			return leavetypefacadeImpl.saveDetails(leaveTypeRequestObject);

		} catch (DuplicateKeyException e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage("duplicate key Exception");
			error.setStatusMessage(e.getCause().getLocalizedMessage());
			return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
		}
	}

	@PostMapping(value = "/get")
	public ResponseEntity<Object> getDetails(@RequestBody LeaveTypeRequest leaveTypeRequestObject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

		try {
			if (leaveTypeRequestObject == null || leaveTypeRequestObject.getLeaveTypeList() == null
					|| leaveTypeRequestObject.getLeaveTypeList().isEmpty()
					|| leaveTypeRequestObject.getTransactionType() == null
					|| leaveTypeRequestObject.getTransactionType().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("object is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (leaveTypeRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
					&& leaveTypeRequestObject.getLeaveTypeList().get(0).getLeaveTypeId() == null) {
				logger.error("please provide id");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("Type must be getAll");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return leavetypefacadeImpl.getDetails(leaveTypeRequestObject);
		} catch (Exception e) {
			logger.debug("inside catch block.***");
			ErrorResponse er = new ErrorResponse();
			er.setStatusCode("409");
			er.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(er, HttpStatus.CONFLICT);
		}

	}

}
