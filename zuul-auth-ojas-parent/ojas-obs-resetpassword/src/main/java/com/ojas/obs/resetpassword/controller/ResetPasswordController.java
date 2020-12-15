package com.ojas.obs.resetpassword.controller;

//import static com.ojas.obs.resetpassword.constants.UserConstants.RESETPASSWORD;
import static com.ojas.obs.resetpassword.constants.UserConstants.SET;
import static com.ojas.obs.resetpassword.constants.UserConstants.UPDATE;
import static com.ojas.obs.resetpassword.constants.UserConstants.SAVE;

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

import com.ojas.obs.resetpassword.error.ErrorResponse;
import com.ojas.obs.resetpassword.facade.ResetPasswordFacade;
import com.ojas.obs.resetpassword.model.ResetPassword;
import com.ojas.obs.resetpassword.request.ResetPasswordRequest;

@RestController
//@RequestMapping(RESETPASSWORD)
public class ResetPasswordController {
	@Autowired
	private ResetPasswordFacade resetPasswordFacade;
	Logger logger = Logger.getLogger(this.getClass());
	ResetPasswordRequest resetPasswordRequest = null;

	@PostMapping(SET)
	public ResponseEntity<Object> setPassword(@RequestBody ResetPasswordRequest resetPasswordRequest,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		logger.debug("incoming requests " + resetPasswordRequest);
		try {
			ResetPassword resetPassword = resetPasswordRequest.getPwd();
			if ((resetPasswordRequest.getTransactionType() == null
					|| resetPasswordRequest.getTransactionType().isEmpty() || resetPassword == null)) {
				logger.error("Request is not valid");
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setStatusCode("422");
				errorResponse.setMessage("Data must not be null");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (resetPasswordRequest.getTransactionType().equalsIgnoreCase(UPDATE)
					&& ((resetPassword.getCurruntPassword() == null || resetPassword.getCurruntPassword().isEmpty())
							|| (resetPassword.getEmployeeId() == null || resetPassword.getEmployeeId().isEmpty())
							|| (resetPassword.getUpdatedBy() == null || resetPassword.getUpdatedBy().isEmpty())
							|| (resetPassword.getNewPassword() == null || resetPassword.getNewPassword().isEmpty()))) {
				logger.error("Request is not valid, No id provided");
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setStatusCode("422");
				errorResponse.setMessage("Password must not be null");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			return resetPasswordFacade.setPassword(resetPasswordRequest);

		} catch (DuplicateKeyException exception) {
			logger.error("***** inside catch block *****");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatusCode("409");
			errorResponse.setMessage(exception.getCause().getLocalizedMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("***** inside catch block *****");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatusCode("409");
			errorResponse.setMessage(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/save")
	public ResponseEntity<Object> savePassword(@RequestBody ResetPasswordRequest resetPasswordRequest,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		try {
			logger.debug("Incoming request : " + resetPasswordRequest);
			ResetPassword resetPassword = resetPasswordRequest.getPwd();
			if ((resetPasswordRequest.getTransactionType() == null
					|| resetPasswordRequest.getTransactionType().isEmpty() || resetPassword == null)) {
				logger.error("Request is not valid");
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setStatusCode("422");
				errorResponse.setMessage("Data must not be null");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (resetPasswordRequest.getTransactionType().equalsIgnoreCase(SAVE)
					&& ((resetPassword.getEmployeeId() == null || resetPassword.getId() == null
							|| resetPassword.getEmployeeId().isEmpty())
							|| (resetPassword.getNewPassword() == null || resetPassword.getNewPassword().isEmpty())))

			{
				logger.error("Request is not valid, No id provided");
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setStatusCode("422");
				errorResponse.setMessage("Password must not be null");
				return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return resetPasswordFacade.savePassword(resetPasswordRequest);

		} catch (DuplicateKeyException exception) {
			logger.error("***** inside catch block *****");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatusCode("409");
			errorResponse.setMessage(exception.getCause().getLocalizedMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		} catch (Exception exception) {
			logger.error("***** inside catch block *****");
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setStatusCode("409");
			errorResponse.setMessage(exception.getMessage());
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

		}
	}
}
