package com.obs.employeeCertificationDetails.facadeImpl;


import static com.obs.employeeCertificationDetails.constants.Constants.DELETE;
import static com.obs.employeeCertificationDetails.constants.Constants.FAILED;
import static com.obs.employeeCertificationDetails.constants.Constants.GET;
import static com.obs.employeeCertificationDetails.constants.Constants.GETBYID;
import static com.obs.employeeCertificationDetails.constants.Constants.GET_COUNT;
import static com.obs.employeeCertificationDetails.constants.Constants.SAVE;
import static com.obs.employeeCertificationDetails.constants.Constants.SUCCESS;
import static com.obs.employeeCertificationDetails.constants.Constants.UPDATE;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.obs.employeeCertificationDetails.dao.EmployeeCertificationDAO;
import com.obs.employeeCertificationDetails.facade.CertificationDetailsFacade;
import com.obs.employeeCertificationDetails.model.CertificationDetails;
import com.obs.employeeCertificationDetails.request.CertificationDetailsRequest;
import com.obs.employeeCertificationDetails.response.CertificationDetailsResponse;


@Service
public class CertificationDetailsFacadeImpl implements CertificationDetailsFacade{
	@Autowired
	private EmployeeCertificationDAO employeeCertificationDAOImpl;
    Logger logger = Logger.getLogger(this.getClass());
    
	@Override
	public ResponseEntity<Object> setCertificationDetails(CertificationDetailsRequest certificationDetailsRequest) throws SQLException{
		logger.debug("inside set method : " + certificationDetailsRequest);
		CertificationDetailsResponse certificationDetailsResponse = null;
		if(certificationDetailsRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
			certificationDetailsResponse = new CertificationDetailsResponse();
			boolean saveCertificationDetails = employeeCertificationDAOImpl.saveCertificationDetails(certificationDetailsRequest);
			logger.debug("inside  save condition.****** : " + saveCertificationDetails);
			if(saveCertificationDetails) {
				certificationDetailsResponse.setMessage("Employee Certification detail saved successfuly");
				certificationDetailsResponse.setStatusCode("200");
				return new ResponseEntity<>(certificationDetailsResponse,HttpStatus.OK);
			}else {
				certificationDetailsResponse.setMessage("Error");
				certificationDetailsResponse.setStatusCode("409");
				return new ResponseEntity<>(certificationDetailsResponse,HttpStatus.CONFLICT);
			}
		}
		if(certificationDetailsRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
			certificationDetailsResponse = new CertificationDetailsResponse();
			boolean updateCertificationDetails = employeeCertificationDAOImpl.updateCertificationDetails(certificationDetailsRequest);
			logger.debug("Inside  update condition.****** : " + updateCertificationDetails);
				if(updateCertificationDetails) {
					certificationDetailsResponse.setMessage("Employee Certification detail updated successfuly");
					certificationDetailsResponse.setStatusCode("200");
				return new ResponseEntity<>(certificationDetailsResponse,HttpStatus.OK);
			    }else {
				certificationDetailsResponse.setMessage(FAILED);
				certificationDetailsResponse.setStatusCode("409");
				return new ResponseEntity<>(certificationDetailsResponse,HttpStatus.CONFLICT);
			}
			
		}
		if(certificationDetailsRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
			certificationDetailsResponse = new CertificationDetailsResponse();
			boolean deleteCertificationDetails = employeeCertificationDAOImpl.deleteCertificationDetails(certificationDetailsRequest);
			logger.debug("Inside  Update condition.****** : " + deleteCertificationDetails);
			if(deleteCertificationDetails) {
				certificationDetailsResponse.setMessage("Employee Certification detail deleted successfuly");
				certificationDetailsResponse.setStatusCode("200");
			    return new ResponseEntity<>(certificationDetailsResponse,HttpStatus.OK);
		    }else {
			certificationDetailsResponse.setMessage(FAILED);
			certificationDetailsResponse.setStatusCode("409");
			return new ResponseEntity<>(certificationDetailsResponse,HttpStatus.CONFLICT);
		    }
		}
		boolean b = (certificationDetailsRequest.getTransactionType().equalsIgnoreCase(SAVE)
				|| certificationDetailsRequest.getTransactionType().equalsIgnoreCase(UPDATE) || certificationDetailsRequest.getTransactionType().equalsIgnoreCase(GETBYID));
				if (!(certificationDetailsRequest.getTransactionType().equalsIgnoreCase(DELETE) || b)) {
					logger.debug("inside  transactionCheck condition.****** : ");
					certificationDetailsResponse = new CertificationDetailsResponse();
					certificationDetailsResponse.setStatusCode("422");
					certificationDetailsResponse.setMessage("transaction type is not correct");
					return new ResponseEntity<>(certificationDetailsResponse, HttpStatus.CONFLICT);
				}
				return new ResponseEntity<>(certificationDetailsResponse, HttpStatus.CONFLICT);
				
	}
	
	

	@Override
	public ResponseEntity<Object> getCertificationDetails(CertificationDetailsRequest certificationDetailsRequest) throws SQLException{
		CertificationDetailsResponse certificationDetailsResponse = null;
		logger.debug("inside  update condition.****** : " + certificationDetailsRequest);
		if(certificationDetailsRequest.getTransactionType().equalsIgnoreCase(GET)) {
			logger.debug("inside  get condition.****** : ");
			certificationDetailsResponse = new CertificationDetailsResponse();
			List<CertificationDetails> allCertificationDetails=employeeCertificationDAOImpl.getAllCertificationDetails();
			
			
			if(allCertificationDetails==null || allCertificationDetails.isEmpty()) {
				certificationDetailsResponse.setCertificationDetailsList(new ArrayList<>());
				certificationDetailsResponse.setMessage("No records found");
				certificationDetailsResponse.setStatusCode("409");
				return new ResponseEntity<>(certificationDetailsResponse,HttpStatus.CONFLICT);
			}
				certificationDetailsResponse.setMessage(SUCCESS);
				certificationDetailsResponse.setStatusCode("200");
				certificationDetailsResponse.setCertificationDetailsList(allCertificationDetails);
				return new ResponseEntity<>(certificationDetailsResponse,HttpStatus.OK);	
			
		}
		if(certificationDetailsRequest.getTransactionType().equalsIgnoreCase(GET_COUNT)) {
			certificationDetailsResponse = new CertificationDetailsResponse();
			int count=employeeCertificationDAOImpl.getAllCertificationDetailsCount();
			logger.debug("inside  get_count condition.****** : ");
			if(count==0) {
				certificationDetailsResponse.setMessage(FAILED);
				certificationDetailsResponse.setStatusCode("409");
				return new ResponseEntity<>(certificationDetailsResponse,HttpStatus.CONFLICT);
			}
				certificationDetailsResponse.setMessage(SUCCESS);
				certificationDetailsResponse.setStatusCode("200");
				return new ResponseEntity<>(certificationDetailsResponse,HttpStatus.OK);
			
		}
		if(certificationDetailsRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
			certificationDetailsResponse = new CertificationDetailsResponse();
			List<CertificationDetails> list=null;
			String empId=certificationDetailsRequest.getCertificationDetailsModel().get(0).getEmployeeId();
			Integer id=certificationDetailsRequest.getCertificationDetailsModel().get(0).getId();
			if(id!=null)
				list=employeeCertificationDAOImpl.getDetailById(certificationDetailsRequest);
			else if(empId!=null)
				list=employeeCertificationDAOImpl.getDetailByEmpId(certificationDetailsRequest);
			
			logger.debug("inside  get_count condition.****** : ");
			if(list == null) {
				certificationDetailsResponse.setMessage("No record Present");
				certificationDetailsResponse.setStatusCode("409");
				return new ResponseEntity<>(certificationDetailsResponse,HttpStatus.CONFLICT);
			}
				certificationDetailsResponse.setMessage(SUCCESS);
				certificationDetailsResponse.setStatusCode("200");
				certificationDetailsResponse.setCertificationDetailsList(list);
				return new ResponseEntity<>(certificationDetailsResponse,HttpStatus.OK);
			
		}
		return null;
	}
    

	

}
