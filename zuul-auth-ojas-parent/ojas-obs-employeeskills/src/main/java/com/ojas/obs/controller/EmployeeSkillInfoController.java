package com.ojas.obs.controller;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ojas.obs.facade.EmployeeSkillFacade;
import com.ojas.obs.model.EmployeeSkillInfo;
import com.ojas.obs.model.EmployeeSkillInfoRequest;
import com.ojas.obs.utility.ErrorResponse;

@RestController
//@RequestMapping("/obs/employeeskillsdetails")
public class EmployeeSkillInfoController {

	@Autowired
	private EmployeeSkillFacade employeeSkillService;

	Logger logger = Logger.getLogger(this.getClass());

	@PostMapping("/set")
	public ResponseEntity<Object> setEmployeeSkillInfo(@RequestBody EmployeeSkillInfoRequest employeeSkillInfoRequest,
			HttpServletRequest request, HttpServletResponse response) {

		ResponseEntity<Object> responseEntity = null;
		ErrorResponse error = new ErrorResponse();
		logger.info("Enter the controller set method...");
		try {
		if (employeeSkillInfoRequest == null) {
			logger.debug("checking object is null or not...");
			error.setStatusMessage("Requestobj is not valid");
			error.setStatusCode("422");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	
		List<EmployeeSkillInfo> listEmployeeSkillInfo = employeeSkillInfoRequest.getSkillInfoModel();

		if (listEmployeeSkillInfo == null || listEmployeeSkillInfo.isEmpty()) {
			error = new ErrorResponse();
			error.setMessage("data must not be null");
			error.setStatusCode("422");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		for (EmployeeSkillInfo skillDetails : listEmployeeSkillInfo) {
			if (employeeSkillInfoRequest.getTransactionType().equalsIgnoreCase("UPDATE")
					&& (null == skillDetails.getUpdate_by()) && (skillDetails.getEmployee_id() == null)) {
				error = new ErrorResponse();
				error.setMessage("Data Must not be null");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}
			if (employeeSkillInfoRequest.getTransactionType().equalsIgnoreCase("SAVE")
					&& null == skillDetails.getCreated_by()) {
				error = new ErrorResponse();
				error.setMessage("Data must not be Null");
				error.setStatusCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}

		
			responseEntity = employeeSkillService.setEmployeeSkillInfo(employeeSkillInfoRequest);
		} catch (DuplicateKeyException exception) {
			error.setStatusMessage(exception.getMessage());
			error.setMessage("duplicates are not allowed");
			error.setStatusCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (SQLException se) {
			logger.error("duplicates are not alowed..");
			error.setMessage("duplicates are not alowed");
			error.setStatusCode("422");
			error.setStatusMessage(se.getMessage());
			responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			logger.error("duplicates are not alowed..");
			error.setStatusCode("409");
			error.setStatusMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return responseEntity;

	}

	@PostMapping("/get")
	public ResponseEntity<Object> getEmployeeSkillInfo(@RequestBody EmployeeSkillInfoRequest employeeSkillInfoRequest) throws SQLException {
				
		logger.debug("@employeeSkillInfoRequest::" + employeeSkillInfoRequest);
		
		if (null == employeeSkillInfoRequest) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage("kyeRequestObj is null");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if (null == employeeSkillInfoRequest.getTransactionType() || employeeSkillInfoRequest.getTransactionType().isEmpty()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage("transaction type is null");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (null == employeeSkillInfoRequest.getTransactionType()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage("transaction type is null");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		return employeeSkillService.getEmployeeSkillInfo(employeeSkillInfoRequest);

	
			}
}
