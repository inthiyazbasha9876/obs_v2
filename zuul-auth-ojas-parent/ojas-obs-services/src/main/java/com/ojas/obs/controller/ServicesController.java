package com.ojas.obs.controller;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.GET;
import static com.ojas.obs.constants.Constants.SET;
import static com.ojas.obs.constants.Constants.UPDATE;

import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ojas.obs.facade.ServicesFacade;
import com.ojas.obs.model.ErrorResponse;
import com.ojas.obs.model.Services;
import com.ojas.obs.request.ServicesRequest;

@RestController
//@RequestMapping("/obs/states")
public class ServicesController {

	@Autowired
	private ServicesFacade servicesFacade;
	Logger logger = Logger.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> setServices(@RequestBody ServicesRequest servicesRequestObj,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		logger.info("@@@@Inside setServices " + servicesRequestObj);

		try {
			if (servicesRequestObj == null) {
				logger.debug("@@@@Inside Requst Object is null " + servicesRequestObj);
				ErrorResponse error = new ErrorResponse();
				logger.debug("request is not valid");
				error.setMessage("servicesRequestObj is not valid");
				error.setStatusCode("422");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (servicesRequestObj.getTransactionType() == null) {
				logger.debug("@@@@Inside setServices TransactionType is null ");
				ErrorResponse error = new ErrorResponse();
				logger.debug("transaction type check");
				error.setMessage("transactionType must be provided");
				error.setStatusCode("422");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for (Services services : servicesRequestObj.getServices()) {
				if (null == services.getServiceName() || services.getServiceName().isEmpty()) {
					logger.debug("@@@@Inside setServices Service Name is null ");
					ErrorResponse error = new ErrorResponse();
					error.setMessage("services can't be null or empty");
					error.setStatusCode("422");
					return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
			for (Services services : servicesRequestObj.getServices()) {
				if ((servicesRequestObj.getTransactionType().equalsIgnoreCase(UPDATE)
						|| servicesRequestObj.getTransactionType().equalsIgnoreCase(DELETE)) && 0 == services.getId()) {
					logger.debug("@@@@Inside setServices id is null ");
					ErrorResponse error = new ErrorResponse();
					error.setMessage("Please provide id");
					error.setStatusCode("422");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}

			return servicesFacade.setServices(servicesRequestObj);

		} catch (DuplicateKeyException exception) {
			System.out.println("ServicesController.setServices()@@@@DuplicateKeyException");
			logger.error("@@@@Inside DuplicateKeyException catch block.");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getCause().getMessage());
			error.setMessage("duplicates are not allowed.");
			error.setStatusCode("409");
			exception.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (SQLException exception) {
			logger.error("@@@@Inside SQLException catch SQLblock.");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			error.setStatusCode("409");
			error.setMessage("SQLException");
			exception.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception exception) {
			logger.error("@@@@Inside Exception catch Exception block.");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			error.setMessage("Exception");
			error.setStatusCode("409");
			exception.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}

	@PostMapping(GET)
	public ResponseEntity<Object> getServices(@RequestBody ServicesRequest servicesRequestObj) {
		logger.info("@@@@Inside getServices " + servicesRequestObj);

		try {
			if (servicesRequestObj == null) {
				logger.debug("@@@@Inside getServices servicesRequestObj is null");
				ErrorResponse error = new ErrorResponse();
				error.setMessage("servicesRequestObj is not valid");
				error.setStatusCode("422");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return servicesFacade.getServices(servicesRequestObj);
		} catch (SQLException exception) {
			logger.error("@@@@Inside SQLException catch block");
			ErrorResponse error = new ErrorResponse();
			error.setMessage("SQLException");
			error.setStatusCode("409");
			error.setStatusMessage(exception.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception exception) {
			logger.error("@@@@Inside Exception catch block");
			ErrorResponse error = new ErrorResponse();
			error.setMessage("Exception");
			error.setStatusCode("409");

			error.setStatusMessage(exception.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}
}