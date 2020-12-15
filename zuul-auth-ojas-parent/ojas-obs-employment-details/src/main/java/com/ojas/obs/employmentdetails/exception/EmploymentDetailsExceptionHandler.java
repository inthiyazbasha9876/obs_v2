package com.ojas.obs.employmentdetails.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ojas.obs.employmentdetails.model.ErrorResponse;

@ControllerAdvice
public class EmploymentDetailsExceptionHandler { 

	@ExceptionHandler(EmploymentDetailsException.class)
	public ResponseEntity<Object> handleEmploymentDetailsNotFound(
			EmploymentDetailsException employmentDetailsException) {

		return new ResponseEntity<Object>(new ErrorResponse("422", employmentDetailsException.getMessage()),
				HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(DataNotInsertedException.class)
	public ResponseEntity<Object> handleEmploymentDetailsNotInserted(
			DataNotInsertedException employmentDetailsException) {

		return new ResponseEntity<Object>(new ErrorResponse("422", employmentDetailsException.getMessage()),
				HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
