package com.ojas.obs.controller;
import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.GET;
import static com.ojas.obs.constants.Constants.SET;
import static com.ojas.obs.constants.Constants.UPDATE;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ojas.obs.facade.StatesFacade;
import com.ojas.obs.model.ErrorResponse;
import com.ojas.obs.model.States;
import com.ojas.obs.request.StatesRequest;

/**
 * @author spuja
 *
 */
@RestController
//@RequestMapping("/obs/states")
public class StatesController {

	@Autowired
	private StatesFacade statesFacade;
	Logger logger = Logger.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> setStates(@RequestBody StatesRequest statesRequestObj,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse){
		logger.info("@@@@Inside setStates " + statesRequestObj);
		
		try {
			if (statesRequestObj == null) {
				logger.debug("@@@@Inside Requst Object is null " + statesRequestObj);
				ErrorResponse error = new ErrorResponse();
				logger.debug("request is not valid");
				error.setMessage("statesRequestObj is not valid");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			} 
			if (statesRequestObj.getTransactionType() == null) {
				logger.debug("@@@@Inside setStates TransactionType is null ");
				ErrorResponse error = new ErrorResponse();
				logger.debug("transaction type check");
				error.setMessage("transactionType must be provided");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for(States states: statesRequestObj.getStates()) {
					if (null ==states.getStateName()|| states.getStateName().isEmpty()) {
						logger.debug("@@@@Inside setStates State Name is null ");
						ErrorResponse error = new ErrorResponse();
						error.setMessage("states can't be null or empty");
						error.setStatusCode("422");
						return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					}
			}
			for(States states: statesRequestObj.getStates()) {
				if ((statesRequestObj.getTransactionType().equalsIgnoreCase(UPDATE)|| statesRequestObj.getTransactionType().equalsIgnoreCase(DELETE))&& 0 == states.getId()) {
				logger.debug("@@@@Inside setStates id is null ");
			    ErrorResponse error = new ErrorResponse();
				error.setMessage("Please provide id");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			}
			
            return statesFacade.setStates(statesRequestObj);

		}catch (DuplicateKeyException exception) {
			
			logger.error("@@@@Inside DuplicateKeyException catch block.");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getCause().getMessage());
			error.setMessage("duplicates are not allowed.");
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	  }
		catch (SQLException exception) {
			logger.error("@@@@Inside SQLException catch SQLblock.");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			error.setStatusCode("409");
			error.setMessage("SQLException");
		
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	  }
		catch (Exception exception) {
			logger.error("@@@@Inside Exception catch Exception block.");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			error.setMessage("Exception");
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	  }
	}

	@PostMapping(GET)
	public ResponseEntity<Object> getStates(@RequestBody StatesRequest statesRequestObj) {
		logger.info("@@@@Inside getStates " + statesRequestObj);
        
		try {
			if (statesRequestObj == null) {
				logger.debug("@@@@Inside getStates statesRequestObj is null");
				ErrorResponse error = new ErrorResponse();
				error.setMessage("statesRequestObj is not valid");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			} 
		return statesFacade.getStates(statesRequestObj);
		} catch (SQLException exception) {
			logger.error("@@@@Inside SQLException catch block");
			ErrorResponse error = new ErrorResponse();
			error.setMessage("SQLException");
			error.setStatusCode("409");
			error.setStatusMessage(exception.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
		catch (Exception exception) {
			logger.error("@@@@Inside Exception catch block");
			ErrorResponse error = new ErrorResponse();
			error.setMessage("Exception");
			error.setStatusCode("409");
			error.setStatusMessage(exception.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}
}