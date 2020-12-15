package com.ojas.obs.passport.controller;
import static com.ojas.obs.passport.utility.Constants.GET;
import static com.ojas.obs.passport.utility.Constants.PASSPOROTBJECTFIELDNULL;
import static com.ojas.obs.passport.utility.Constants.PASSPOROTBJECTNULL;
import static com.ojas.obs.passport.utility.Constants.REQUESTOBJECTNULL;
import static com.ojas.obs.passport.utility.Constants.SAVE;
import static com.ojas.obs.passport.utility.Constants.SET;
import static com.ojas.obs.passport.utility.Constants.TRANSACTIONTYPENULL;
import static com.ojas.obs.passport.utility.Constants.UPDATE;
//import static com.ojas.obs.passport.utility.Constants.PASSPORTSERVICE;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ojas.obs.passport.Request.PassportRequest;
import com.ojas.obs.passport.facade.PassportFacade;
import com.ojas.obs.passport.model.ErrorResponse;
import com.ojas.obs.passport.model.Passport;


@RestController
//@RequestMapping(PASSPORTSERVICE)
public class PassportController {
	Logger logger = Logger.getLogger(this.getClass());
	ResponseEntity<Object> responseEntity = null;

	@Autowired
	PassportFacade passportFacadeImpl;

	@PostMapping(value = SET)
	public ResponseEntity<Object> setPassport(@RequestBody PassportRequest passportRequestObject,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		logger.info("@@@@received input data is in controller" + passportRequestObject);
		ErrorResponse error = new ErrorResponse();
		List<Passport> passport = passportRequestObject.getPassportList();
		try {
			
		if ((null==passportRequestObject)||(null == passportRequestObject.getPassportList())||(null == passportRequestObject.getTransaactionType())) {
			
			logger.debug("@@@@Request is not valid");
			error.setStatusCode("422");
			error.setMessage(REQUESTOBJECTNULL);
			return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		
		if(passportRequestObject.getTransaactionType().equalsIgnoreCase(SAVE)) {
		for(Passport passport2:passport) {	
		if((null == passport2.getCenterName() || passport2.getCenterName().isEmpty())) {
			logger.debug("@@@@this passport contains null object fields");
			error.setStatusCode("422");
			error.setMessage(PASSPOROTBJECTFIELDNULL);
			return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		}
		}
	
		if(passportRequestObject.getTransaactionType().equalsIgnoreCase(UPDATE)) {
		for(Passport passport2:passport) {	
		if((null == passport2.getCenterName() || passport2.getCenterName().isEmpty())) {
			logger.debug("@@@@this passport contains null object fields");
			error.setStatusCode("422");
			error.setMessage(PASSPOROTBJECTFIELDNULL);
			return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		}
		}
		
		
		return  passportFacadeImpl.setPassport(passportRequestObject);

		}
		catch (DuplicateKeyException e) { 
			logger.error("@@@@@@DuplicateKeyException");
			e.printStackTrace();
			error.setStatusMessage(e.getCause().getLocalizedMessage());
			error.setMessage("Duplicates are not allowed.");
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
			
		} catch (SQLException e) { 
			error.setStatusCode(String.valueOf(e.getErrorCode()));
			logger.error("@@@@@@sql exception");
			e.printStackTrace();
			error.setStatusMessage(e.getMessage());
			error.setMessage("sql exception occured");
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
			
		} catch (Exception e) { 
			error.setStatusMessage(e.getMessage());
			error.setMessage("Exception occured");
			logger.error("@@@@@@Exception"+e.getMessage());
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		logger.info("@@@@returned output data from controller" + responseEntity);
		return responseEntity;
		
	}
	
	@PostMapping(value = GET)
	public ResponseEntity<Object> getPaasport(@RequestBody PassportRequest passportRequestObject,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		logger.info("@@@@received input data for GET in controller" + passportRequestObject);
		try {
		if (passportRequestObject == null) {
			
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage(REQUESTOBJECTNULL);
			logger.debug("@@@@ request object is null");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if (null == passportRequestObject.getPassportList()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage(PASSPOROTBJECTNULL);
			logger.debug("@@@@ passport list is null");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (null == passportRequestObject.getTransaactionType()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage(TRANSACTIONTYPENULL);
			logger.debug("@@@@ Transaction type is null");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
			
			responseEntity = passportFacadeImpl.getPassport(passportRequestObject);
		} 
		
		 catch (SQLException e) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode(String.valueOf(e.getErrorCode()));
				logger.error("@@@@@@sql exception");
				e.printStackTrace();
				error.setStatusMessage(e.getMessage());
				error.setMessage("sql exception occured");
				responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} 
		catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			e.printStackTrace();
			error.setStatusMessage(e.getMessage());
			error.setMessage("Exception occured");
			logger.debug("@@@@ Catch Exception" +" "+e.getMessage());
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		logger.info("@@@@@@ returned output data from GET in controller" + passportRequestObject);
		return responseEntity;
	}
	
}
