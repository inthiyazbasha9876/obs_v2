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

import com.ojas.obs.facadeimpl.ServiceTypeFacadeImpl;
import com.ojas.obs.model.ServiceType;
import com.ojas.obs.request.ServiceTypeRequest;
import com.ojas.obs.response.ErrorResponse;

@RestController
public class ServiceTypeController {

	@Autowired
	private ServiceTypeFacadeImpl serfacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(value = "/set")
	public ResponseEntity<Object> saveDetails(@RequestBody ServiceTypeRequest serRequestObject,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("data coming into controller");

		List<ServiceType> servicetype = serRequestObject.getServicetypeList();

		try {
			if (serRequestObject.getServicetypeList() == null || servicetype.isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("reqest object is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for(ServiceType s:servicetype) {
			if (serRequestObject.getTransactionType().equalsIgnoreCase(SAVE)
					&&s.getServiceTypeName().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage(" Field is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}
			for(ServiceType s1:servicetype) {
		
				if ((serRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)
						&&(s1.getServiceTypeName().isEmpty()
					
						|| serRequestObject.getServicetypeList().get(0).getId() == null))
						|| (serRequestObject.getTransactionType().equalsIgnoreCase(DELETE)
								&& serRequestObject.getServicetypeList().get(0).getId() == null) ){
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("422");
					error.setStatusMessage(" Field is null");
					return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

				}}
			}
				return serfacadeImpl.saveDetails(serRequestObject);

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
	public ResponseEntity<Object> getDetails(@RequestBody ServiceTypeRequest serRequestObject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

		try {
			if (serRequestObject == null || serRequestObject.getServicetypeList() == null
					|| serRequestObject.getServicetypeList().isEmpty() || serRequestObject.getTransactionType() == null
					|| serRequestObject.getTransactionType().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("object is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (serRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
					&& serRequestObject.getServicetypeList().get(0).getId() == null) {
				logger.error("please provide id");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("Type must be getAll");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return serfacadeImpl.getDetails(serRequestObject);
		} catch (Exception e) {
			logger.debug("inside catch block.***");
			ErrorResponse er = new ErrorResponse();
			er.setStatusCode("409");
			er.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(er, HttpStatus.CONFLICT);
		}

	}

}
