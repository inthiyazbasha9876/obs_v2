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

import com.ojas.obs.facadeimpl.LocationTypeFacadeImpl;
import com.ojas.obs.model.LocationType;
import com.ojas.obs.request.LocationTypeRequest;
import com.ojas.obs.response.ErrorResponse;

@RestController
public class LocationTypeController {
	@Autowired
	private LocationTypeFacadeImpl locationfacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(value = "/set")
	public ResponseEntity<Object> saveDetails(@RequestBody LocationTypeRequest locationTypeRequestObject,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("data coming into controller");

		List<LocationType> locationtype = locationTypeRequestObject.getLocationTypeList();

		try {
			if (locationTypeRequestObject.getLocationTypeList() == null || locationtype.isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("reqest object is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for (LocationType g : locationtype) {
				if (locationTypeRequestObject.getTransactionType().equalsIgnoreCase(SAVE)
						&& g.getLocationType().isEmpty()) {
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("422");
					error.setStatusMessage(" Field is null");
					return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

				}

				if (locationTypeRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)
						&& locationTypeRequestObject.getLocationTypeList().get(0).getLocationtypeId() == null
						|| locationTypeRequestObject.getTransactionType().equalsIgnoreCase(DELETE)
								&& locationTypeRequestObject.getLocationTypeList().get(0).getLocationtypeId() == null) {
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("422");
					error.setStatusMessage(" Field is null");
					return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

				}
			}
			return locationfacadeImpl.saveDetails(locationTypeRequestObject);

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
	public ResponseEntity<Object> getDetails(@RequestBody LocationTypeRequest locationTypeRequestObject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

		try {
			if (locationTypeRequestObject == null || locationTypeRequestObject.getLocationTypeList() == null
					|| locationTypeRequestObject.getLocationTypeList().isEmpty()
					|| locationTypeRequestObject.getTransactionType() == null
					|| locationTypeRequestObject.getTransactionType().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("object is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (locationTypeRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
					&& locationTypeRequestObject.getLocationTypeList().get(0).getLocationtypeId() == null) {
				logger.error("please provide id");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("Type must be getAll");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return locationfacadeImpl.getDetails(locationTypeRequestObject);
		} catch (Exception e) {
			logger.debug("inside catch block.***");
			ErrorResponse er = new ErrorResponse();
			er.setStatusCode("409");
			er.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(er, HttpStatus.CONFLICT);
		}

	}

}
