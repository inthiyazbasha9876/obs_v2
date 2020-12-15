package com.ojas.obs.controller;

import static com.ojas.obs.constants.UserConstants.SET;
import static com.ojas.obs.constants.UserConstants.UPDATE;
import static com.ojas.obs.constants.UserConstants.GET;
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
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.EmailCommunicationFacadeImpl;
import com.ojas.obs.request.EmailCommunicationRequest;

@RestController
public class EmailCommunicationController {

	@Autowired
	private EmailCommunicationFacadeImpl emailCommunicationFacade;

	Logger logger = Logger.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> setEmail(@RequestBody EmailCommunicationRequest EmailCommunicationRequestobject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

		logger.debug("incoming request " + EmailCommunicationRequestobject);

		try {

			if (null == EmailCommunicationRequestobject) {

				ErrorResponse error = new ErrorResponse();
				logger.debug("EmailCommunicationRequestobject is not valid");
				error.setMessage("invalid data");
				error.setCode("422");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}

			if (null == EmailCommunicationRequestobject.getEmail()
					|| EmailCommunicationRequestobject.getEmail().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setMessage("fields must not be null");
				logger.debug("data is not valid");
				error.setCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (null == EmailCommunicationRequestobject.getTransactionType()
					|| EmailCommunicationRequestobject.getTransactionType().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setMessage("transationType is not valid");
				error.setCode("422");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if ((EmailCommunicationRequestobject.getTransactionType().equalsIgnoreCase(UPDATE)))

			{
				ErrorResponse error = new ErrorResponse();
				logger.debug("TransactionType is  invalid");
				error.setMessage("data not valid");
				error.setCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}

			return emailCommunicationFacade.setEmail(EmailCommunicationRequestobject);

		} catch (SQLException e) {
			logger.debug("inside businesscontroller-SQLException catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.debug("SQLException is  invalid");
			error.setMessage("SQLException");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (DuplicateKeyException exception) {
			logger.debug("inside controller catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.debug("data is  invalid");
			error.setStatusMessage(exception.getCause().getLocalizedMessage());
			error.setMessage("duplicates are not allowed");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception exception) {
			logger.debug("inside catch block.*******");
			ErrorResponse error = new ErrorResponse();
			error.setMessage(exception.getMessage());
			error.setCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

	}

	@PostMapping(GET)
	public ResponseEntity<Object> getEmail(@RequestBody EmailCommunicationRequest emailCommunicationRequest,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			System.out.println("in controller class");

			logger.debug("requestObject received = " + emailCommunicationRequest);

			if (emailCommunicationRequest == null || emailCommunicationRequest.getTransactionType().isEmpty()
					|| null == emailCommunicationRequest.getTransactionType()) {
				ErrorResponse error = new ErrorResponse();
				error.setMessage("statesRequestObj is not valid");
				error.setCode("422");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
//			if (emailCommunicationRequest.getTransactionType().equalsIgnoreCase(GETALL)
//					&& emailCommunicationRequest.getEmail().get(0).getFrom() == null) {
//				logger.error("Request is not valid, No id provided");
//				ErrorResponse errorResponse = new ErrorResponse();
//				errorResponse.setCode("422");
//				errorResponse.setMessage("Type must be getall");
//				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
//			}
			return emailCommunicationFacade.getEmail(emailCommunicationRequest);
		} catch (SQLException exception) {
			logger.debug("inside EmployeeEducationFacde-SQLException catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.debug("SQLException is  invalid");
			error.setMessage("SQLException");
			error.setStatusMessage(exception.getCause().getLocalizedMessage());
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception exception) {
			logger.debug("inside catch block.*******");
			ErrorResponse error = new ErrorResponse();
			error.setStatusMessage(exception.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}
}
