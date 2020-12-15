package com.ojas.obs.projectDetails.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.ojas.obs.projectDetails.model.ErrorResponse;


@ControllerAdvice
public class ProjectDetailsExceptionHandler {
	@ExceptionHandler(ProjectDetailsException.class)
	public ResponseEntity<Object> handleProjectDetailsNotFound(
			ProjectDetailsException projectDetailsException) {

		return new ResponseEntity<>(new ErrorResponse("422", projectDetailsException.getMessage()),HttpStatus.UNPROCESSABLE_ENTITY);
	}
	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<Object> handleInvalidFormat(
			InvalidFormatException invalidFormatException) {

		return new ResponseEntity<>(new ErrorResponse("422", "InvalidFormatException"),HttpStatus.UNPROCESSABLE_ENTITY);
	}
	@ExceptionHandler(TransactionException.class)
	public ResponseEntity<Object> handleInvalidFormat(
			TransactionException transactionException) {

		return new ResponseEntity<>(new ErrorResponse("422", "TransactionException"),HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
