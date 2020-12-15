package com.ojas.obs.controller;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.UPDATE;
import static com.ojas.obs.constants.Constants.SAVE;

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

import com.ojas.obs.facadeimpl.GeoFacadeImpl;
import com.ojas.obs.model.Geo;
import com.ojas.obs.request.GeoRequest;
import com.ojas.obs.response.ErrorResponse;

@RestController

public class GeoController {
	@Autowired
	private GeoFacadeImpl geofacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(value = "/set")
	public ResponseEntity<Object> saveDetails(@RequestBody GeoRequest geoRequestObject, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("data coming into controller");

		List<Geo> geo = geoRequestObject.getGeoList();
		try {
		if (geoRequestObject.getGeoList() == null || geo.isEmpty()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setStatusMessage("reqest object is null");
			return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		for(Geo g:geo) {
		if (geoRequestObject.getTransactionType().equalsIgnoreCase(SAVE)
				&&g.getGeoname().isEmpty()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setStatusMessage(" Field is null");
			return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

		}

	
			if (geoRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)
					&& geoRequestObject.getGeoList().get(0).getGeoId() == null
					|| geoRequestObject.getTransactionType().equalsIgnoreCase(DELETE)
							&& geoRequestObject.getGeoList().get(0).getGeoId() == null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage(" Field is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}
		}
			return geofacadeImpl.saveDetails(geoRequestObject);

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
	public ResponseEntity<Object> getDetails(@RequestBody GeoRequest geoRequestObject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

		try {
			if (geoRequestObject == null || geoRequestObject.getGeoList() == null
					|| geoRequestObject.getGeoList().isEmpty() || geoRequestObject.getTransactionType() == null
					|| geoRequestObject.getTransactionType().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("object is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (geoRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
					&& geoRequestObject.getGeoList().get(0).getGeoId() == null) {
				logger.error("please provide id");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("Type must be getAll");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return geofacadeImpl.getDetails(geoRequestObject);
		} catch (Exception e) {
			logger.debug("inside catch block.***");
			ErrorResponse er = new ErrorResponse();
			er.setStatusCode("409");
			er.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(er, HttpStatus.CONFLICT);
		}

	}

}
