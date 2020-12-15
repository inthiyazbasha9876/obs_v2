package com.ojas.obs.facade;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.FAILED;
import static com.ojas.obs.constants.Constants.GETALL;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.SUCCESS;
import static com.ojas.obs.constants.Constants.UPDATE;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.dao.EmployeeBUDao;
import com.ojas.obs.model.EmployeeBUDetails;
import com.ojas.obs.request.EmployeeBUDetailsRequest;
import com.ojas.obs.response.EmployeeBUDeatailsResponse;

/**
 * 
 * @author uyashwanth
 *
 */
@Service
public class EmpoyeeBUFacade {
	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private EmployeeBUDao employeebuDao;

	/**
	 * 
	 * @param employeeBURequest
	 * @return
	 * @throws SQLException
	 */
	public ResponseEntity<Object> setEmployeeBU(EmployeeBUDetailsRequest employeeBURequest) throws SQLException {
		logger.debug("inside setEmployeeBU method : " + employeeBURequest);
		EmployeeBUDeatailsResponse employeeburesponse = null;

		if (employeeBURequest.getTransactionType().equalsIgnoreCase(SAVE)) {
			employeeburesponse = new EmployeeBUDeatailsResponse();
			boolean saveEmployeebu = employeebuDao.saveEmployeebu(employeeBURequest);
			logger.debug("*****inside  save condition.***** : " + saveEmployeebu);
			if (saveEmployeebu) {
				employeeburesponse.setMessage("Successfully BusinessUnit record saved");
				employeeburesponse.setStatusCode("200");
				return new ResponseEntity<>(employeeburesponse, HttpStatus.OK);
			} else {
				logger.error("**Failed to save the record(s)***");
				employeeburesponse.setMessage(FAILED);
				employeeburesponse.setStatusCode("409");
				return new ResponseEntity<>(employeeburesponse, HttpStatus.CONFLICT);
			}
		}
		if (employeeBURequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
			employeeburesponse = new EmployeeBUDeatailsResponse();
			boolean updateemployeebu = employeebuDao.updateEmployeebu(employeeBURequest);
			logger.debug("*****inside  update condition.***** : " + updateemployeebu);
			if (updateemployeebu) {
				employeeburesponse.setMessage("Successfully BusinessUnit record updated");
				employeeburesponse.setStatusCode("200");
				return new ResponseEntity<>(employeeburesponse, HttpStatus.OK);
			} else {
				logger.error("**Failed to update the record(s)***");
				employeeburesponse.setMessage(FAILED);
				employeeburesponse.setStatusCode("409");
				return new ResponseEntity<>(employeeburesponse, HttpStatus.CONFLICT);
			}
		}
		if (employeeBURequest.getTransactionType().equalsIgnoreCase(DELETE)) {
			employeeburesponse = new EmployeeBUDeatailsResponse();
			boolean deleteId = false;
			logger.debug("*****inside  delete condition.***** : " + deleteId);
			List<EmployeeBUDetails> budetails = employeeBURequest.getEmployeeBUDeatils();
			for (EmployeeBUDetails employeebudetails : budetails) {
				Integer id = employeebudetails.getId();
				deleteId = employeebuDao.deleteEmployeeRecord(id);
			}
			if (deleteId) {
				employeeburesponse.setMessage("Successfully BusinessUnit record deleted");
				employeeburesponse.setStatusCode("200");
				return new ResponseEntity<>(employeeburesponse, HttpStatus.OK);
			} else {
				logger.error("**Failed to delete the record(s)***");
				employeeburesponse.setMessage(FAILED);
				employeeburesponse.setStatusCode("409");
				return new ResponseEntity<>(employeeburesponse, HttpStatus.CONFLICT);
			}
		}

		return null;

	}

	/**
	 * 
	 * @param employeeRequest
	 * @return
	 * @throws SQLException
	 */
	public ResponseEntity<Object> getEmployeeBUDetails(EmployeeBUDetailsRequest employeeRequest) throws SQLException {
		EmployeeBUDeatailsResponse employeeburesponse = null;
		logger.debug("inside  getall condition.****** : " + employeeRequest);
		if (employeeRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			logger.debug("inside  get condition.****** : ");
			employeeburesponse = new EmployeeBUDeatailsResponse();
			List<EmployeeBUDetails> allCertificationDetails = employeebuDao.getAllEmployeebu(employeeRequest);

			if (allCertificationDetails == null || allCertificationDetails.isEmpty()) {
				employeeburesponse.setListCourse(new ArrayList<>());
				employeeburesponse.setMessage("No records found");
				employeeburesponse.setStatusCode("409");
				return new ResponseEntity<>(employeeburesponse, HttpStatus.CONFLICT);
			} else {
				employeeburesponse.setMessage(SUCCESS);
				employeeburesponse.setStatusCode("200");
				employeeburesponse.setListCourse(allCertificationDetails);
				return new ResponseEntity<>(employeeburesponse, HttpStatus.OK);
			}
		}

		if (employeeRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
			employeeburesponse = new EmployeeBUDeatailsResponse();
			List<EmployeeBUDetails> list = null;
			logger.debug("inside  get_count condition.****** : ");
			String EmpId = employeeRequest.getEmployeeBUDeatils().get(0).getEmployeeId();
			if (EmpId == null) {
				list = employeebuDao.getById(employeeRequest);
			} else {
				list = employeebuDao.getByEmpId(employeeRequest);
			}
			if (list.size() == 0) {
				employeeburesponse.setMessage("No record Present");
				employeeburesponse.setStatusCode("409");
				return new ResponseEntity<>(employeeburesponse, HttpStatus.CONFLICT);
			} else {
				employeeburesponse.setMessage(SUCCESS);
				employeeburesponse.setStatusCode("200");
				employeeburesponse.setListCourse(list);
				return new ResponseEntity<>(employeeburesponse, HttpStatus.OK);
			}
		}
		return null;

	}

}
