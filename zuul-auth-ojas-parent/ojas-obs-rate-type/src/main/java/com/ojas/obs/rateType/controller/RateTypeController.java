package com.ojas.obs.rateType.controller;


import static com.ojas.obs.rateType.constant.Constant.DELETE;
import static com.ojas.obs.rateType.constant.Constant.GET;
import static com.ojas.obs.rateType.constant.Constant.GETBYID;
import static com.ojas.obs.rateType.constant.Constant.SAVE;
import static com.ojas.obs.rateType.constant.Constant.SET;
import static com.ojas.obs.rateType.constant.Constant.UPDATE;

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

import com.ojas.obs.rateType.facade.RateTypeFacade;
import com.ojas.obs.rateType.model.RateType;
import com.ojas.obs.rateType.request.RateTypeRequest;
import com.ojas.obs.rateType.response.ErrorResponse;

@RestController
public class RateTypeController {
	@Autowired
	private RateTypeFacade rateTypeFacade;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(value = SET)
	public ResponseEntity<Object> saveRateType(@RequestBody RateTypeRequest rateTypeRequestObject,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		List<RateType> rateType = rateTypeRequestObject.getRateType();

		try {
			if (rateTypeRequestObject.getRateType() == null || rateType.isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setCode("422");
				error.setStatusMessage("reqest object is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for(RateType s:rateType) {
			if (rateTypeRequestObject.getTransactionType().equalsIgnoreCase(SAVE)
					&&s.getRateType().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setCode("422");
				error.setStatusMessage(" Field is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}

		
				if (rateTypeRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)
						&& rateTypeRequestObject.getRateType().get(0).getId() == null
						|| rateTypeRequestObject.getTransactionType().equalsIgnoreCase(DELETE)
								&& rateTypeRequestObject.getRateType().get(0).getId() == null) {
					ErrorResponse error = new ErrorResponse();
					error.setCode("422");
					error.setStatusMessage(" Field is null");
					return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

				}
			}
				return rateTypeFacade.saveRateType(rateTypeRequestObject);

			} catch (DuplicateKeyException e) {
				ErrorResponse error = new ErrorResponse();
				error.setCode("409");
				error.setStatusMessage("duplicate key Exception");
				error.setStatusMessage(e.getCause().getLocalizedMessage());
				return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
			} catch (Exception e) {
				ErrorResponse error = new ErrorResponse();
				error.setCode("409");
				error.setStatusMessage(e.getMessage());
				return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
			}
		}

	@PostMapping(value = GET)
	public ResponseEntity<Object> getCustomerDetails(@RequestBody RateTypeRequest rateTypeRequestObject,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		
		try {
			if (rateTypeRequestObject == null || rateTypeRequestObject.getRateType() == null
					|| rateTypeRequestObject.getRateType().isEmpty()
					|| rateTypeRequestObject.getTransactionType() == null
					|| rateTypeRequestObject.getTransactionType().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setCode("422");
				error.setStatusMessage("REQUESTOBJECTNULL");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (rateTypeRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
					&& rateTypeRequestObject.getRateType().get(0).getId() == null) {
				logger.error("please provide id");
				ErrorResponse error = new ErrorResponse();
				error.setCode("422");
				error.setStatusMessage("Type must be getAll");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return rateTypeFacade.getCustomerDetails(rateTypeRequestObject);
		} catch (Exception e) {
			logger.debug("inside catch block.***");
			ErrorResponse er = new ErrorResponse();
			er.setCode("409");
			er.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(er, HttpStatus.CONFLICT);
		}

	}
}
