package com.ojas.obs.facadeimpl;

import static com.ojas.obs.constants.UserConstants.FAILED;
import static com.ojas.obs.constants.UserConstants.GETALL;
import static com.ojas.obs.constants.UserConstants.GETBYID;
import static com.ojas.obs.constants.UserConstants.SAVE;
import static com.ojas.obs.constants.UserConstants.SUCCESS;
import static com.ojas.obs.constants.UserConstants.UPDATE;
import static com.ojas.obs.constants.UserConstants.DELETE;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.dao.EmployeeStatusDao;
import com.ojas.obs.facade.EmployeeStatusFacade;
import com.ojas.obs.model.EmployeeStatus;
import com.ojas.obs.request.EmployeeStatusRequest;
import com.ojas.obs.response.EmployeeStatusResponse;

/**
 * 
 * @author Manohar
 *
 */
@Service
public class EmployeeStatusFacadeImpl implements EmployeeStatusFacade {
	@Autowired
	private EmployeeStatusDao employeeStatusDao;
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> setEmployeeStatus(EmployeeStatusRequest employeeStatusRequest)
			throws SQLException, DuplicateKeyException {
		logger.debug("inside setEmployeeStatus method in empstatusfacade: " + employeeStatusRequest);
		EmployeeStatusResponse employeeStatusResponse = null;
		if (employeeStatusRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
			employeeStatusResponse = new EmployeeStatusResponse();
			boolean save = employeeStatusDao.saveEmployeeStatus(employeeStatusRequest);
			logger.debug("Inside  save condition in facade : " + save);
			if (!save) {
				logger.error("Failed to save the record(s)");
				employeeStatusResponse.setMessage(FAILED);
				employeeStatusResponse.setStatusCode("409");
				return new ResponseEntity<>(employeeStatusResponse, HttpStatus.CONFLICT);
			}
			logger.info("Successfully saved the record(s)");
			employeeStatusResponse.setMessage("Record successfully saved");
			employeeStatusResponse.setStatusCode("200");
			return new ResponseEntity<>(employeeStatusResponse, HttpStatus.OK);
		}

		if (employeeStatusRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
			employeeStatusResponse = new EmployeeStatusResponse();
			boolean update = employeeStatusDao.updateEmployeeStatus(employeeStatusRequest);
			logger.debug("Inside  update condition : " + update);
			if (!update) {
				logger.error("Failed to update the record(s)");
				employeeStatusResponse.setStatusCode("409");
				employeeStatusResponse.setMessage(FAILED);
				logger.info("Update response : " + employeeStatusResponse);
				return new ResponseEntity<>(employeeStatusResponse, HttpStatus.CONFLICT);
			}
			employeeStatusResponse.setMessage("Record Successfully updated");
			employeeStatusResponse.setStatusCode("200");
			return new ResponseEntity<>(employeeStatusResponse, HttpStatus.OK);
		}

		if (employeeStatusRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
			employeeStatusResponse = new EmployeeStatusResponse();
			boolean delete = employeeStatusDao.deleteEmployeeStatus(employeeStatusRequest);
			logger.debug("*****inside  delete condition.***** : " + delete);
			if (!delete) {
				employeeStatusResponse.setMessage("Record already deleted");
				return new ResponseEntity<>(employeeStatusResponse, HttpStatus.CONFLICT);
			}
			employeeStatusResponse.setMessage("Record successfully deleted");
			return new ResponseEntity<>(employeeStatusResponse, HttpStatus.OK);
		}

		employeeStatusResponse = new EmployeeStatusResponse();
		return new ResponseEntity<>(employeeStatusResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	public ResponseEntity<Object> getEmployeeStatus(EmployeeStatusRequest employeeStatusRequest) throws SQLException {
		logger.debug("Inside  getEmployeeStatus method in EmployeeStatusFacade");
		EmployeeStatusResponse statusResponse = new EmployeeStatusResponse();
		List<EmployeeStatus> allEmpStatus = null;
		if (employeeStatusRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			allEmpStatus = employeeStatusDao.getAllStatus();
		}
		if (employeeStatusRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
			List<EmployeeStatus> empList = employeeStatusRequest.getEmployeeStatus();
			allEmpStatus = employeeStatusDao.getById(empList.get(0).getId());
		}
		if (null == allEmpStatus || allEmpStatus.isEmpty()) {
			logger.error("Failed to fetch the record(s)");
			statusResponse.setMessage("Failed to fetch the record(s)");
			statusResponse.setEmployeeStatusList(allEmpStatus);
			return new ResponseEntity<>(statusResponse, HttpStatus.CONFLICT);
		} else {
			logger.info("Records fetched successfully");
			statusResponse.setMessage(SUCCESS);
			statusResponse.setStatusCode("200");
			statusResponse.setEmployeeStatusList(allEmpStatus);
			return new ResponseEntity<>(statusResponse, HttpStatus.OK);
		}

	}
}