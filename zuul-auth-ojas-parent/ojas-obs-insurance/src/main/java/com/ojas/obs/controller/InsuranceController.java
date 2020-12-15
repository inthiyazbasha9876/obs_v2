package com.ojas.obs.controller;

import static com.ojas.obs.constants.URLconstants.GET;
import static com.ojas.obs.constants.URLconstants.SET;
import static com.ojas.obs.constants.InsuranceConstants.DELETE;
import static com.ojas.obs.constants.InsuranceConstants.UPDATE;
import static com.ojas.obs.constants.InsuranceConstants.SAVE;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ojas.obs.errorMessage.ErrorResponse;
import com.ojas.obs.facade.InsuranceFacade;
import com.ojas.obs.model.Insurance;
import com.ojas.obs.request.InsuranceRequest;
import com.google.gson.Gson;

@RestController
public class InsuranceController {

	@Autowired
	InsuranceFacade insuranceFacade;

	String transactionType = null;

	Logger logger = Logger.getLogger(this.getClass());

	@RequestMapping(SET)
	public ResponseEntity<Object> saveInsuranceDetails(@RequestBody String insuranceRequest,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws SQLException {

		ResponseEntity<Object> responseEntity = null;

		InsuranceRequest insuranceRequestObject = null;

		try {

			Gson gson = new Gson();
			logger.debug("requestObject received = ");
			insuranceRequestObject = gson.fromJson(insuranceRequest, InsuranceRequest.class);

			if (null == insuranceRequestObject) {

				ErrorResponse error = new ErrorResponse();
				logger.debug("data is not valid");
				error.setMessage("Request is not valid");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}

			Insurance insurance = insuranceRequestObject.getInsurance();

			if (null == insurance) {
				ErrorResponse error = new ErrorResponse();
				error.setMessage("data must not be null");
				logger.debug("data is not valid");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			transactionType = insuranceRequestObject.getTransactionType();

			if (transactionType.equalsIgnoreCase(SAVE)

					&& ((null == insurance.getAge_id())
							|| ((null == insurance.getAge_band() || insurance.getAge_band().isEmpty()))
							|| ((null == insurance.getPlan1()))
							|| ((null == insurance.getPlan2()) || (insuranceRequestObject.getTransactionType() == null
									|| (insuranceRequestObject.getTransactionType().isEmpty()))))) {

				ErrorResponse error = new ErrorResponse();
				logger.debug("data is  invalid");
				error.setMessage("Request is  invalid");

				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}

			if ((transactionType.equalsIgnoreCase(UPDATE) || transactionType.equalsIgnoreCase(DELETE))
					&& null == insuranceRequestObject.getInsurance().getId()) {

				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse = new ErrorResponse();
				errorResponse.setStatusCode("422");
				errorResponse.setMessage("id cannot be null");

				logger.info("Request is not valid, No id provided");
				return new ResponseEntity<Object>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			responseEntity = insuranceFacade.saveInsuranceDetails(insuranceRequestObject);
			return responseEntity;

		} catch (Exception e) {
			logger.debug("inside catch block.*******");
			ErrorResponse error = new ErrorResponse();
			error.setMessage(e.getMessage());
		}
		return responseEntity;

	}

	@RequestMapping(GET)

	public ResponseEntity<Object> getEmpDetails(@RequestBody String insuranceRequest, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) throws SQLException {

		ResponseEntity<Object> responseEntity = null;
		InsuranceRequest insuranceRequestObject = null;
		// String sessionId = null;
		try {
			Gson gson = new Gson();
			logger.debug("requestObject received = ");
			insuranceRequestObject = gson.fromJson(insuranceRequest, InsuranceRequest.class);

			if (null == insuranceRequestObject) {

				ErrorResponse error = new ErrorResponse();
				logger.debug("data is not valid");
				error.setMessage("Request is not valid");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			// sessionId = employeeRequest.getSessionId();
			// logger.debug("incoming request " + sessionId);
			if (null == insuranceRequestObject.getTransactionType()
					|| insuranceRequestObject.getTransactionType().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				logger.debug("data is not valid");
				error.setMessage("transactiontype can't be null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return insuranceFacade.getAllInsuranceDetails(insuranceRequestObject);

		} catch (Exception exception) {
			logger.debug("inside catch block.*******");

			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
		}
		return responseEntity;
	}

}