package com.ojas.obs.facadeimpl;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.dao.EmployeeContactDao;
import com.ojas.obs.facade.EmployeeContactFacade;
import com.ojas.obs.model.EmployeeContactInfo;
import com.ojas.obs.requests.EmployeeContactInfoRequest;
import com.ojas.obs.response.EmployeeContactInfoResponse;

/**
 * 
 * @author ksaiKrishna
 *
 */
@Service
public class EmployeeContactFacadeImpl implements EmployeeContactFacade {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private EmployeeContactDao employeeContactDao;

	
	@Override
	public ResponseEntity<Object> setEmployeeContactInfo(EmployeeContactInfoRequest employeeContactInfoRequest) throws SQLException{
		logger.debug("inside set method : " + employeeContactInfoRequest);
		EmployeeContactInfoResponse employeeContactInfoResponse= null;
		if(employeeContactInfoRequest.getTransactionType().equalsIgnoreCase("save")) {
			employeeContactInfoResponse = new EmployeeContactInfoResponse();
			boolean save = employeeContactDao.saveEmployeeContactInfo(employeeContactInfoRequest);
			logger.debug("inside  save condition.****** : " + save);
			
			if(save) {
				employeeContactInfoResponse.setMessage("Employee Certification detail saved successfuly");
				employeeContactInfoResponse.setStatusCode("200");
				return new ResponseEntity<>(employeeContactInfoResponse,HttpStatus.OK);
			}else {
				employeeContactInfoResponse.setMessage("Error");
				employeeContactInfoResponse.setStatusCode("409");
				return new ResponseEntity<>(employeeContactInfoResponse,HttpStatus.CONFLICT);
			}
		}
		
		if(employeeContactInfoRequest.getTransactionType().equalsIgnoreCase("update")) {
			employeeContactInfoResponse = new EmployeeContactInfoResponse();
			boolean update = employeeContactDao.updateEmployeeContactInfo(employeeContactInfoRequest);
			logger.debug("inside  update condition.****: " + update);
				if(update) {
					employeeContactInfoResponse.setMessage("Employee Certification detail updated successfuly");
					employeeContactInfoResponse.setStatusCode("200");
				return new ResponseEntity<>(employeeContactInfoResponse,HttpStatus.OK);
			    }else {
			    	employeeContactInfoResponse.setMessage("Failed");
			    	employeeContactInfoResponse.setStatusCode("409");
				return new ResponseEntity<>(employeeContactInfoResponse,HttpStatus.CONFLICT);
			}
			
		}
		
		if(employeeContactInfoRequest.getTransactionType().equalsIgnoreCase("delete")) {
			employeeContactInfoResponse = new EmployeeContactInfoResponse();
			boolean delete = employeeContactDao.deleteEmployeeContactInfo(employeeContactInfoRequest);
			logger.debug("inside  update condition.***: " + delete);
			if(delete) {
				employeeContactInfoResponse.setMessage("Employee Certification detail deleted successfuly");
				employeeContactInfoResponse.setStatusCode("200");
			    return new ResponseEntity<>(employeeContactInfoResponse,HttpStatus.OK);
		    }else {
		    	employeeContactInfoResponse.setMessage("Failed");
		    	employeeContactInfoResponse.setStatusCode("409");
			return new ResponseEntity<>(employeeContactInfoResponse,HttpStatus.CONFLICT);
		    }
		}
		boolean b = (employeeContactInfoRequest.getTransactionType().equalsIgnoreCase("save")
				|| employeeContactInfoRequest.getTransactionType().equalsIgnoreCase("update") || employeeContactInfoRequest.getTransactionType().equalsIgnoreCase("getbyid"));
				if (!(employeeContactInfoRequest.getTransactionType().equalsIgnoreCase("delete") || b)) {
					logger.debug("inside  transactionCheck condition.****** : ");
					employeeContactInfoResponse = new EmployeeContactInfoResponse();
					employeeContactInfoResponse.setStatusCode("422");
					employeeContactInfoResponse.setMessage("transaction type is not correct");
					return new ResponseEntity<>(employeeContactInfoResponse, HttpStatus.CONFLICT);
				}
				return new ResponseEntity<>(employeeContactInfoResponse, HttpStatus.CONFLICT);
				
	}
	
	

	@Override
	public ResponseEntity<Object> getEmployeeContactInfo(EmployeeContactInfoRequest employeeContactInfoRequest) throws SQLException{
		EmployeeContactInfoResponse employeeContactInfoResponse= null;
		logger.debug("inside  Update Condition.****** : " + employeeContactInfoRequest);
		if(employeeContactInfoRequest.getTransactionType().equalsIgnoreCase("getall")) {
			logger.debug("inside  get condition.****** : ");
			employeeContactInfoResponse = new EmployeeContactInfoResponse();
			List<EmployeeContactInfo> allContactDetails=employeeContactDao.getAllContacctDetails();
			
			
			if(allContactDetails==null || allContactDetails.isEmpty()) {
				employeeContactInfoResponse.setEmpContacts(new ArrayList<>());
				employeeContactInfoResponse.setMessage("No records found");
				employeeContactInfoResponse.setStatusCode("409");
				return new ResponseEntity<>(employeeContactInfoResponse,HttpStatus.CONFLICT);
			}
				employeeContactInfoResponse.setMessage("Success");
				employeeContactInfoResponse.setStatusCode("200");
				employeeContactInfoResponse.setEmpContacts(allContactDetails);
				return new ResponseEntity<>(employeeContactInfoResponse,HttpStatus.OK);	
			
		}
		if(employeeContactInfoRequest.getTransactionType().equalsIgnoreCase("get_count")) {
			employeeContactInfoResponse = new EmployeeContactInfoResponse();
			int count=employeeContactDao.getAllContactDetailsCount();
			logger.debug("inside  get_count condition.****** : ");
			if(count==0) {
				employeeContactInfoResponse.setMessage("failed");
				employeeContactInfoResponse.setStatusCode("409");
				return new ResponseEntity<>(employeeContactInfoResponse,HttpStatus.CONFLICT);
			}
				employeeContactInfoResponse.setMessage("succeSS");
				employeeContactInfoResponse.setStatusCode("200");
				return new ResponseEntity<>(employeeContactInfoResponse,HttpStatus.OK);
			
		}
		if(employeeContactInfoRequest.getTransactionType().equalsIgnoreCase("getbyid")) {
			employeeContactInfoResponse = new EmployeeContactInfoResponse();
			List<EmployeeContactInfo> list=null;
			String empId=employeeContactInfoRequest.getEmpInfo().get(0).getEmpId();
			Integer id=employeeContactInfoRequest.getEmpInfo().get(0).getId();
			if(id!=null)
				list=employeeContactDao.showEmployeeContactInfoById(employeeContactInfoRequest);
			else if(empId!=null)
				list=employeeContactDao.showEmployeeContactInfoByEmpId(employeeContactInfoRequest);
			
			logger.debug("inside  get_count condition.****** : ");
			if(list==null) {
				employeeContactInfoResponse.setMessage("No record Present");
				employeeContactInfoResponse.setStatusCode("409");
				return new ResponseEntity<>(employeeContactInfoResponse,HttpStatus.CONFLICT);
			}else {
				employeeContactInfoResponse.setMessage("success");
				employeeContactInfoResponse.setStatusCode("200");
				employeeContactInfoResponse.setEmpContacts(list);
				return new ResponseEntity<>(employeeContactInfoResponse,HttpStatus.OK);
			}
		}
		return null;
	}
	
}
