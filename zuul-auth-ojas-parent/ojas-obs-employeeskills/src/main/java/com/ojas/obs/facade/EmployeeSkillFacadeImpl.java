package com.ojas.obs.facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.dao.EmployeeSkillDao;
import com.ojas.obs.model.EmployeeSkillInfo;
import com.ojas.obs.model.EmployeeSkillInfoRequest;
import com.ojas.obs.model.EmployeeSkillInfoResponse;

@Service
public class EmployeeSkillFacadeImpl implements EmployeeSkillFacade {

	@Autowired
	private EmployeeSkillDao employeeSkillDao;

	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> setEmployeeSkillInfo(EmployeeSkillInfoRequest employeeSkillInfoRequest)
			throws SQLException {

		EmployeeSkillInfoResponse response = new EmployeeSkillInfoResponse();
		List<EmployeeSkillInfo> listEmployeeSkillInfo = employeeSkillInfoRequest.getSkillInfoModel();
		logger.info("Enter the set method facade ....");

		// save method

		if (employeeSkillInfoRequest.getTransactionType().equalsIgnoreCase("save")) {

			employeeSkillDao.saveEmployeeSkillInfo(employeeSkillInfoRequest);
			int count = employeeSkillDao.getAllCount();
			response.setGetSkillInfoList(new ArrayList<>());
			logger.debug("Successfully record addedd...");
			response.setStatusMessage("Successfully record added");
			response.setTotalCount(count);

			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		// update method

		if (employeeSkillInfoRequest.getTransactionType().equalsIgnoreCase("update"))
			for (EmployeeSkillInfo skillDetails : listEmployeeSkillInfo) {
				if (0 != skillDetails.getId()) {
					employeeSkillDao.updateEmployeeSkillInfo(employeeSkillInfoRequest);
					int count = employeeSkillDao.getAllCount();
					response.setGetSkillInfoList(new ArrayList<>());
					logger.debug("Successfully record updated....");
					response.setStatusMessage("Successfully record updated");
					response.setTotalCount(count);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
			}
		return null;
	}

	@Override
	public ResponseEntity<Object> getEmployeeSkillInfo(EmployeeSkillInfoRequest employeeSkillInfoRequest)
			throws SQLException {

		EmployeeSkillInfoResponse response = new EmployeeSkillInfoResponse();
		if (employeeSkillInfoRequest.getTransactionType().equalsIgnoreCase("getAll")) {

			List<EmployeeSkillInfo> employeeSkillInfoList = employeeSkillDao
					.showEmployeeSkillInfo(employeeSkillInfoRequest);

			if (employeeSkillInfoList == null || employeeSkillInfoList.isEmpty()) {
				response.setGetSkillInfoList(employeeSkillInfoList);
				response.setStatusMessage("No Records Found");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			} else {
				response.setStatusMessage("success");
				response.setGetSkillInfoList(employeeSkillInfoList);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}

		if (employeeSkillInfoRequest.getTransactionType().equalsIgnoreCase("getbyid")) {

			List<EmployeeSkillInfo> list = null;
			
			Integer id = employeeSkillInfoRequest.getSkillInfoModel().get(0).getId();
			if (id != 0)
				list = employeeSkillDao.getById(employeeSkillInfoRequest);

			if (list != null) {
				
				response.setStatusMessage("success");
				response.setGetSkillInfoList(list);
				return new ResponseEntity<>(response, HttpStatus.OK);

			} else {
				response.setStatusMessage("no records found");
			
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}

		if (employeeSkillInfoRequest.getTransactionType().equalsIgnoreCase("getByEmpId")) {

			List<EmployeeSkillInfo> list = null;
			String empId = employeeSkillInfoRequest.getSkillInfoModel().get(0).getEmployee_id();

			if (empId != null)
				list = employeeSkillDao.getByEmpId(employeeSkillInfoRequest);

			if (list != null) {
			
				response.setStatusMessage("success");
				response.setGetSkillInfoList(list);
				return new ResponseEntity<>(response, HttpStatus.OK);

			} else {
				response.setStatusMessage("no records found");
				
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}


