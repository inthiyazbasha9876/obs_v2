package com.ojas.obs.controller;


import static com.ojas.obs.constants.Constants.GET;
import static com.ojas.obs.constants.Constants.SET;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.facade.GenderFacade;
import com.ojas.obs.model.ErrorResponse;
import com.ojas.obs.model.Genders;
import com.ojas.obs.request.GenderRequest;
@RestController
//@RequestMapping(GENDERS)
public class GenderController {
	@Autowired
    private GenderFacade genderFacadeImpl;
	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * this method will handle all post mapping request
	 * @param genderRequest
	 * @return
	 */
	@PostMapping(SET)
	public ResponseEntity<Object> setGenders(@RequestBody GenderRequest genderRequestObj){
		logger.info("@@@@Inside setGenders Incoming requests " +genderRequestObj);
		try {
			if (genderRequestObj == null) {
				logger.debug("@@@@Inside setGenderMethod RequestObject is null");
				ErrorResponse error = new ErrorResponse();
				error.setMessage("genderRequestObj is not valid");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
            if (null == genderRequestObj.getTransactionType()|| genderRequestObj.getTransactionType().isEmpty()) {
            	logger.debug("@@@@Inside setGenderMethod transactionType is null");
				ErrorResponse error = new ErrorResponse();
				error.setMessage("transationType is not valid");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
            List<Genders> genders=genderRequestObj.getGender();
			for (Genders gender : genders) {
				if ((genderRequestObj.getTransactionType().equalsIgnoreCase("UPDATE")|| genderRequestObj.getTransactionType().equalsIgnoreCase("DELETE"))&& null == gender.getId()) {
					logger.debug("@@@@Inside setGenderMethod id is null");
					ErrorResponse error = new ErrorResponse();
					logger.debug("data is  invalid");
					error.setMessage("Id should be provided");
					error.setStatusCode("422");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				if (gender.getGender() == null  || gender.getGender().isEmpty()) {
					logger.debug("@@@@Inside setGenderMethod gender is null");
						ErrorResponse error = new ErrorResponse();
						error.setMessage("gender all fields should be provided");
						error.setStatusCode("422");
						return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
			 
			logger.info("@@@@Inside setGenderMethod before returnstmt");
			return genderFacadeImpl.setGender(genderRequestObj);

		}  catch (DuplicateKeyException exception) {
			logger.error("@@@@Inside DuplicateKeyException catch block.*******");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getCause().getMessage());
			error.setStatusCode("409");
			error.setMessage("duplicated are not allowed");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		catch (SQLException exception) {
			logger.error("@@@@Inside SQLException catch block.*******");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			error.setStatusCode("409");
			error.setMessage("SQLException occured");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		catch (Exception exception) { 
		  logger.error("@@@@Inside Exception catch block.*******");
		  ErrorResponse error = new ErrorResponse();
		  error.setStatusMessage(exception.getMessage()); error.setStatusCode("409");
		  error.setMessage("Exception occured");
		 return new ResponseEntity<>(error,
		  HttpStatus.CONFLICT); 
		}
		 
	}
	
	@PostMapping(GET)
	public ResponseEntity<Object> getGenders(@RequestBody GenderRequest genderRequestObj) {
		try {
			logger.debug("requestObject received = " + genderRequestObj);
            if (genderRequestObj == null) {
            	logger.debug("inside getGenderMethod RequestObject is null");
				ErrorResponse error = new ErrorResponse();
				error.setMessage("gendersRequestObj is not valid");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			} 
			return genderFacadeImpl.getGender(genderRequestObj);
		} catch (SQLException exception) {
			logger.error("inside SQLException getGenderMethod ");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			 error.setMessage("SQLException occured");
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}catch (Exception exception) { 
			  logger.error("inside catch block.*******");
			  ErrorResponse error = new ErrorResponse();
			  error.setStatusMessage(exception.getMessage()); error.setStatusCode("409");
			  error.setMessage("Exception occured");
			
			  return new ResponseEntity<>(error,HttpStatus.CONFLICT); 
			}
	}
}
