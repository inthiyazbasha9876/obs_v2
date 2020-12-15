package com.ojas.obs.facadeimpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.constants.UserConstants;
import com.ojas.obs.dao.EmployeeEducationDao;
import com.ojas.obs.facade.EmployeeEducationFacade;
import com.ojas.obs.model.EmployeeEducation;
import com.ojas.obs.modelrequest.EmployeeEducationRequest;
import com.ojas.obs.modelresponse.EmployeeEducationResponse;

/**
 * 
 * @author mpraneethguptha
 *
 */
@Service
public class EmployeeEducationFacadeImpl implements EmployeeEducationFacade {

	@Autowired
	EmployeeEducationDao employeeEducationDao;

	Logger logger = Logger.getLogger(this.getClass());
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ojas.obs.facade.EmployeeEducationFacade#setEmployeeEducationInfo(com.ojas
	 * .obs.modelrequest.EmployeeEducationRequest)
	 */

	@Override
	public ResponseEntity<Object> setEmployeeEducationInfo(EmployeeEducationRequest employeeEducationRequest)
			throws SQLException {
		EmployeeEducationResponse employeeEducationResponse = null;
		logger.info("The request details in service set method" + employeeEducationRequest);

		if (employeeEducationRequest.getTransactionType().equalsIgnoreCase(UserConstants.SAVE)) {
			employeeEducationResponse = new EmployeeEducationResponse();
			boolean employeeEducation = employeeEducationDao.saveEmployeeEducation(employeeEducationRequest);
			if (employeeEducation) {
				int allRecordsCount = employeeEducationDao.getAllRecordsCount();
				logger.debug("The record count being saved " + allRecordsCount);
				logger.debug("Details saved " + employeeEducation);
				employeeEducationResponse.setMessage("Employeee education record saved Successfully");
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.OK);
			} else {
				employeeEducationResponse.setMessage("FAILED to save");
				logger.error("request details not saved");
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.CONFLICT);

			}
		}
		if (employeeEducationRequest.getTransactionType().equalsIgnoreCase(UserConstants.DELETE)) {
			employeeEducationResponse = new EmployeeEducationResponse();
			boolean isDeleted = false;
			List<EmployeeEducation> employeeEducations = employeeEducationRequest.getListEmployeeEducations();
			for (EmployeeEducation employeeEducation : employeeEducations) {
				int id = employeeEducation.getId();
				isDeleted = employeeEducationDao.deleteEmployeeEducation(id);
			}
			if (isDeleted) {
				int allRecordsCount = employeeEducationDao.getAllRecordsCount();
				logger.debug("The employee education record deleted " + allRecordsCount);
				employeeEducationResponse.setMessage("Employeee education record deleted Successfully");
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.OK);
			} else {
				employeeEducationResponse.setMessage("FAILED");
				logger.error("Requested object is not deleted");
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.CONFLICT);
			}

		}
		if (employeeEducationRequest.getTransactionType().equalsIgnoreCase(UserConstants.UPDATE)) {
			employeeEducationResponse = new EmployeeEducationResponse();
			boolean employeeEducation = employeeEducationDao.updateEmployeeEducation(employeeEducationRequest);
			if (employeeEducation) {
				logger.debug("employeeEducation details updated");
				employeeEducationResponse.setMessage("Employeee education record updated Successfully");
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.OK);
			} else {
				employeeEducationResponse.setMessage("FAILED");
				logger.error("employeeEducation details not updated");
				return new ResponseEntity<>(employeeEducationResponse, HttpStatus.CONFLICT);
			}
		}

		return new ResponseEntity<>(employeeEducationResponse, HttpStatus.CONFLICT);
	}

	@Override
	public ResponseEntity<Object> getEmployeeEducationInfo(EmployeeEducationRequest employeeEducationRequest)
			throws SQLException {
		EmployeeEducationResponse employeeEducationResponse = null;
		logger.info("The request details in service get method" + employeeEducationRequest);

		if (employeeEducationRequest.getTransactionType().equalsIgnoreCase(UserConstants.GETALL)) {
			if (null != employeeEducationRequest.getListEmployeeEducations().get(0).getId()) {
				List<EmployeeEducation> byId = employeeEducationDao.getAllEmployeeEducation(employeeEducationRequest);
				if (null != byId) {
					employeeEducationResponse = new EmployeeEducationResponse();
					employeeEducationResponse.setListCourse(byId);
					employeeEducationResponse.setMessage("Retrieved  education record successfully");
					logger.debug("Retrieved  education record Succesfully using " + byId);
					return new ResponseEntity<>(employeeEducationResponse, HttpStatus.OK);
				} else {
					employeeEducationResponse = new EmployeeEducationResponse();
					employeeEducationResponse.setMessage("Failed");
					logger.error("The request record using" + byId + " not retrieved");
					return new ResponseEntity<>(employeeEducationResponse, HttpStatus.CONFLICT);
				}
			} else {
				List<EmployeeEducation> emplList = employeeEducationDao
						.getAllEmployeeEducation(employeeEducationRequest);

				employeeEducationResponse = new EmployeeEducationResponse();

				if (null != emplList) {
					employeeEducationResponse.setListCourse(emplList);
					employeeEducationResponse.setMessage("Retrieved all records successfully");
					logger.debug("List of employee records retrieved " + emplList);
					return new ResponseEntity<>(employeeEducationResponse, HttpStatus.OK);
				} else {
					
					employeeEducationResponse.setMessage("Failed");
					logger.error("List of employee records not retrieved");
					return new ResponseEntity<>(employeeEducationResponse, HttpStatus.CONFLICT);
				}
			}
		}
		return new ResponseEntity<>(employeeEducationResponse, HttpStatus.CONFLICT);
	}

	/**
	 * 
	 * @param allEducationDetails
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
}
