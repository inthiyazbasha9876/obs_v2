package com.ojas.obs.controller;

import static com.ojas.obs.utility.Constants.GET;
import static com.ojas.obs.utility.Constants.SAVE;
import static com.ojas.obs.utility.Constants.SET;
import static com.ojas.obs.utility.Constants.UPDATE_AADHAR_IMG;
import static com.ojas.obs.utility.Constants.UPDATE_AADHAR_STATUS;
import static com.ojas.obs.utility.Constants.UPDATE_PAN_IMG;
import static com.ojas.obs.utility.Constants.UPDATE_PAN_STATUS;
import static com.ojas.obs.utility.Constants.UPDATE_PASSPORT_STATUS;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.facade.KyeFacade;
import com.ojas.obs.model.KYE;
import com.ojas.obs.request.KYERequest;
import com.ojas.obs.utility.ErrorResponse;



@RestController
//@RequestMapping(KYE)
public class KyeController {

	@Autowired
	private KyeFacade kyeFacade;
	Logger logger = Logger.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> setKye(@RequestBody KYERequest kyeRequestObj, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		logger.debug("@request in string" + kyeRequestObj);
		ResponseEntity<Object> res = null;

		List<KYE> kyeList = kyeRequestObj.getKye();

		for (KYE kye : kyeList) {

			try {
				if ((kyeRequestObj.getTransactionType().equalsIgnoreCase(SAVE) && null == kye)) {
					logger.debug("@fields is not valid");
					ErrorResponse error = new ErrorResponse();
					error.setMessage("fields can't be null");
					error.setStatusCode("422");
					res = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					return res;

				}
				if ((kyeRequestObj.getTransactionType().equalsIgnoreCase(UPDATE_AADHAR_IMG) && kye.getId() == 0)
						&& (null==kye.getAadhar_img())) {
					logger.debug("@fields is not valid");
					ErrorResponse error = new ErrorResponse();
					error.setMessage("fields can't be null");
					error.setStatusCode("422");
					res = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					return res;

				}
				if ((kyeRequestObj.getTransactionType().equalsIgnoreCase(UPDATE_PAN_IMG) && kye.getId() == 0)
						&& (null==kye.getPan_img())) {
					logger.debug("@fields is not valid");
					ErrorResponse error = new ErrorResponse();
					error.setMessage("fields can't be null");
					error.setStatusCode("422");
					res = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					return res;

				}
				if ((kyeRequestObj.getTransactionType().equalsIgnoreCase(UPDATE_AADHAR_IMG) && kye.getId() == 0)
						&& (null==kye.getAadhar_img())) {
					logger.debug("@fields is not valid");
					ErrorResponse error = new ErrorResponse();
					error.setMessage("fields can't be null");
					error.setStatusCode("422");
					res = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					return res;

				}
				
				if ((kyeRequestObj.getTransactionType().equalsIgnoreCase(UPDATE_AADHAR_STATUS) && kye.getId() == 0)) {
					logger.debug("@fields is not valid");
					ErrorResponse error = new ErrorResponse();
					error.setMessage("fields can't be null");
					error.setStatusCode("422");
					res = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					return res;

				}
				if ((kyeRequestObj.getTransactionType().equalsIgnoreCase(UPDATE_PAN_STATUS) && kye.getId() == 0)) {
					logger.debug("@fields is not valid");
					ErrorResponse error = new ErrorResponse();
					error.setMessage("fields can't be null");
					error.setStatusCode("422");
					res = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					return res;

				}
				if ((kyeRequestObj.getTransactionType().equalsIgnoreCase(UPDATE_PASSPORT_STATUS) && kye.getId() == 0)) {
					logger.debug("@fields is not valid");
					ErrorResponse error = new ErrorResponse();
					error.setMessage("fields can't be null");
					error.setStatusCode("422");
					res = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					return res;

				}
				
				if (null == kyeRequestObj.getTransactionType() || kyeRequestObj.getTransactionType().isEmpty()) {
					logger.debug("@transactionType is not valid");
					ErrorResponse error = new ErrorResponse();
					error.setMessage("transactionType can't be null");

					res = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					return res;
				}
				if (kyeRequestObj.getTransactionType().equalsIgnoreCase("delete") && kye.getId() == 0) {
					logger.debug("@id is null");
					ErrorResponse error = new ErrorResponse();
					error.setMessage("id can't be null");
					error.setStatusCode("422");
					res = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					return res;
				}
				return kyeFacade.setKYE(kyeRequestObj);

			} catch (Exception exception) {
				logger.debug("inside CostCenterController-SQLException catch block.****");
				ErrorResponse error = new ErrorResponse();
				logger.debug("Exception is  invalid");
				error.setMessage("Exception");
				error.setStatusMessage(exception.getCause().getMessage());
				error.setStatusCode("409");
				exception.printStackTrace();
				res = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				return res;
			}

		}
		return res;
	}

	@PostMapping(GET)
	public ResponseEntity<Object> getKey(@RequestBody KYERequest kyeRequestObj, HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		logger.debug("@kyeRequestObj::" + kyeRequestObj);
		if (null == kyeRequestObj) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage("kyeRequestObj is null");
			return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if (null == kyeRequestObj.getTransactionType()
				|| kyeRequestObj.getTransactionType().isEmpty()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage("transaction type is null");
			return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (null == kyeRequestObj.getTransactionType()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage("transaction type is null");
			return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return kyeFacade.getKYE(kyeRequestObj);

	

	}


}
