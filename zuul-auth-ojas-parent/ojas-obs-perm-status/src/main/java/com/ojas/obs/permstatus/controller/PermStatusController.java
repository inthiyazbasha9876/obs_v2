package com.ojas.obs.permstatus.controller;

import static com.ojas.obs.permstatus.constants.Constants.DELETE;
import static com.ojas.obs.permstatus.constants.Constants.GET;
import static com.ojas.obs.permstatus.constants.Constants.GETBYID;
import static com.ojas.obs.permstatus.constants.Constants.NULLVALUE;
import static com.ojas.obs.permstatus.constants.Constants.SAVE;
import static com.ojas.obs.permstatus.constants.Constants.SET;
import static com.ojas.obs.permstatus.constants.Constants.UPDATE;

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

import com.ojas.obs.permstatus.facadeimpl.PermStatusFacadeImpl;
import com.ojas.obs.permstatus.model.PermStatus;
import com.ojas.obs.permstatus.request.PermStatusRequest;
import com.ojas.obs.permstatus.response.ErrorResponse;

@RestController
//@RequestMapping("obs/permstatus")
public class PermStatusController {
	@Autowired
	private PermStatusFacadeImpl permStatusFacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> savePermStatus(@RequestBody PermStatusRequest permStatusRequestObject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		logger.debug("data coming into controller");

		List<PermStatus> permStatusList = permStatusRequestObject.getPermStatusList();
		try {
			if ((permStatusRequestObject == null) || (permStatusRequestObject.getTransactionType() == null)
					|| permStatusRequestObject.getTransactionType().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("reqest object is null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			for (PermStatus perm : permStatusList) {
				if (permStatusRequestObject.getTransactionType().equalsIgnoreCase(SAVE)
						&& perm.getPermstatus().isEmpty()) {

					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("422");
					error.setStatusMessage(NULLVALUE);
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				if ((permStatusRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)
						|| permStatusRequestObject.getTransactionType().equalsIgnoreCase(DELETE))
						&& perm.getPermstatusId() == null) {
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("422");
					error.setStatusMessage(NULLVALUE);
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
			return permStatusFacadeImpl.savePermStatus(permStatusRequestObject);

		} catch (DuplicateKeyException e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage("duplicate key Exception");
			error.setStatusMessage(e.getCause().getLocalizedMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}

	@PostMapping(GET)
	public ResponseEntity<Object> getPermStatus(@RequestBody PermStatusRequest permStatusRequestObject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

		logger.debug("received data into controller");
		try {
			if (permStatusRequestObject == null || permStatusRequestObject.getPermStatusList() == null
					|| permStatusRequestObject.getPermStatusList().isEmpty()
					|| permStatusRequestObject.getTransactionType() == null
					|| permStatusRequestObject.getTransactionType().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("object is null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (permStatusRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
					&& permStatusRequestObject.getPermStatusList().get(0).getPermstatusId() == null) {
				logger.error("please provide id");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("Type must be getAll");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return permStatusFacadeImpl.getPermStatus(permStatusRequestObject);
		} catch (Exception e) {
			logger.debug("inside catch block.***");
			ErrorResponse er = new ErrorResponse();
			er.setStatusCode("409");
			er.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(er, HttpStatus.CONFLICT);
		}

	}

}
