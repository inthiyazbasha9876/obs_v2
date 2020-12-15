package com.ojas.obs.regforgot.controller;

import static com.ojas.obs.regforgot.constants.URLconstants.FORGOT;
import static com.ojas.obs.regforgot.constants.URLconstants.SET;
import static com.ojas.obs.regforgot.constants.UserConstants.SENDMAIL;
import static com.ojas.obs.regforgot.constants.UserConstants.UPDATE;
import static com.ojas.obs.regforgot.constants.UserConstants.EXCEPTION;
import static com.ojas.obs.regforgot.constants.UserConstants.MAILEXCEPTION;
import static com.ojas.obs.regforgot.constants.UserConstants.SQLEXCEPTION;
import static com.ojas.obs.regforgot.constants.UserConstants.EMPTYDATA;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.regforgot.error.ErrorResponse;
import com.ojas.obs.regforgot.facade.ForgotPasswordFacade;
import com.ojas.obs.regforgot.model.ForgotPassword;
import com.ojas.obs.regforgot.request.ForgotPasswordRequest;

@RestController
//@RequestMapping(FORGOT)
public class ForgotPasswordController {
	@Autowired
	private ForgotPasswordFacade forgotPasswordFacade;

	Logger logger = Logger.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> setEmployeeInfo(@RequestBody ForgotPasswordRequest forgotPasswordRequest,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		logger.debug("incomming request : " + forgotPasswordRequest);
		try {
			if (null == forgotPasswordRequest) {
				ErrorResponse error = new ErrorResponse();
				logger.debug("Data is not valid");
				error.setStatusCode("422");
				error.setMessage("Request is not valid");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			ForgotPassword forgotPassword = forgotPasswordRequest.getForgotPassword();
			if (null == forgotPasswordRequest.getTransactionType()
					|| forgotPasswordRequest.getTransactionType().isEmpty() || null == forgotPassword) {
				ErrorResponse error = new ErrorResponse();
				logger.debug("Transaction type must not be null");
				error.setStatusCode("422");
				error.setMessage("Transaction type must not be null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (forgotPasswordRequest.getTransactionType().equalsIgnoreCase(SENDMAIL)
					&& (null == forgotPassword.getEmployeeId() || forgotPassword.getEmployeeId().isEmpty())) {
				ErrorResponse error = new ErrorResponse();
				logger.debug("Data is not valid");
				error.setStatusCode("422");
				error.setMessage("Id must not be null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (forgotPasswordRequest.getTransactionType().equalsIgnoreCase(UPDATE)
					&& ((null == forgotPassword.getOtp())
					|| (null == forgotPassword.getNewPassword() || forgotPassword.getNewPassword().isEmpty())
					|| (null == forgotPassword.getEmployeeId() || forgotPassword.getEmployeeId().isEmpty()))) {
				ErrorResponse error = new ErrorResponse();
				logger.debug("data must not be null for update");
				error.setStatusCode("422");
				error.setMessage("Data must not be null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return forgotPasswordFacade.setForgotPassword(forgotPasswordRequest);
		} catch (EmptyResultDataAccessException exception) {
			logger.error("MailException caught");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatusCode("409");
			errorResponse.setMessage(EMPTYDATA);
			errorResponse.setStatusMessage(exception.getMessage());
			logger.error(exception.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		}
		catch (MailException exception) {
			logger.error("MailException caught");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatusCode("409");
			errorResponse.setMessage(MAILEXCEPTION);
			errorResponse.setStatusMessage(exception.getMessage());
			logger.error(exception.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		} catch (SQLException exception) {
			logger.error("SQLException caught");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatusCode("409");
			errorResponse.setMessage(SQLEXCEPTION);
			errorResponse.setStatusMessage(exception.getMessage());
			logger.error(exception.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		} catch (Exception exception) {
			logger.error("Exception caught");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatusCode("409");
			errorResponse.setMessage(EXCEPTION);
			errorResponse.setStatusMessage(exception.getMessage());
			logger.error(exception.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		}

	}
}
