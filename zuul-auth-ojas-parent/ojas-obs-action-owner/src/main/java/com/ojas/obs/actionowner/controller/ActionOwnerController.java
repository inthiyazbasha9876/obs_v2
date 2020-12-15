package com.ojas.obs.actionowner.controller;

import static com.ojas.obs.actionowner.constants.Constants.DELETE;
import static com.ojas.obs.actionowner.constants.Constants.GETBYID;
import static com.ojas.obs.actionowner.constants.Constants.SAVE;
import static com.ojas.obs.actionowner.constants.Constants.UPDATE;
import static com.ojas.obs.actionowner.constants.Constants.SET;
import static com.ojas.obs.actionowner.constants.Constants.GET;
import static com.ojas.obs.actionowner.constants.Constants.NULLVALUE;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.actionowner.facadeImpl.ActionOwnerFacadeImpl;
import com.ojas.obs.actionowner.model.ActionOwner;
import com.ojas.obs.actionowner.request.ActionOwnerRequest;
import com.ojas.obs.actionowner.response.ErrorResponse;

@RestController
//@RequestMapping("obs/actionowner")
public class ActionOwnerController {
	@Autowired
	private ActionOwnerFacadeImpl actionOwnerFacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(value =SET)
	public ResponseEntity<Object> saveActionOwner(@RequestBody ActionOwnerRequest actionOwnerRequestObject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		logger.debug("data coming into the controller");

		List<ActionOwner> actionOwnerList = actionOwnerRequestObject.getActionOwnerList();
		try {
			if (actionOwnerRequestObject == null || actionOwnerRequestObject.getActionOwnerList() == null
					|| actionOwnerRequestObject.getTransactionType() == null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage(NULLVALUE);
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for (ActionOwner action : actionOwnerList) {
			if (actionOwnerRequestObject.getTransactionType().equalsIgnoreCase(SAVE)
				
					&& action.getActionowner().isEmpty()) {
						ErrorResponse response = new ErrorResponse();
						response.setStatusCode("422");
						response.setStatusMessage(NULLVALUE);
						return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
					}

			if ((actionOwnerRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)
					||actionOwnerRequestObject.getTransactionType().equalsIgnoreCase(DELETE))
			         && action.getActionownerId()==null){
				     	ErrorResponse response = new ErrorResponse();
						response.setStatusCode("422");
						response.setStatusMessage(NULLVALUE);
						return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
					}
				}
			
			return actionOwnerFacadeImpl.saveActionOwner(actionOwnerRequestObject);

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

	@PostMapping(value = GET)
	public ResponseEntity<Object> getActionOwner(@RequestBody ActionOwnerRequest actionOwnerObjectRequest,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

		logger.info("received data into controller", actionOwnerObjectRequest);
		try {
			if (actionOwnerObjectRequest == null || actionOwnerObjectRequest.getActionOwnerList() == null
					|| actionOwnerObjectRequest.getActionOwnerList().isEmpty()
					|| actionOwnerObjectRequest.getTransactionType() == null
					|| actionOwnerObjectRequest.getTransactionType().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("object is null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (actionOwnerObjectRequest.getTransactionType().equalsIgnoreCase(GETBYID)
					&& actionOwnerObjectRequest.getActionOwnerList().get(0).getActionownerId() == null) {
				logger.error("please provide id");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("action owner must be getAll");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return actionOwnerFacadeImpl.getActionOwner(actionOwnerObjectRequest);
		} catch (Exception e) {
			logger.debug("inside catch block.***");
			ErrorResponse er = new ErrorResponse();
			er.setStatusCode("409");
			er.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(er, HttpStatus.CONFLICT);
		}

	}
}

