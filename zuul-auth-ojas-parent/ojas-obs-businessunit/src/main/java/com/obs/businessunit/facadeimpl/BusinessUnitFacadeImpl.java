package com.obs.businessunit.facadeimpl;

import static com.obs.businessunit.constants.UserConstants.FAILED;
import static com.obs.businessunit.constants.UserConstants.GETALL;
import static com.obs.businessunit.constants.UserConstants.GETBYID;
import static com.obs.businessunit.constants.UserConstants.SAVE;
import static com.obs.businessunit.constants.UserConstants.SUCCESS;
import static com.obs.businessunit.constants.UserConstants.UPDATE;
import static com.obs.businessunit.constants.UserConstants.DELETE;
import static com.obs.businessunit.constants.UserConstants.GETALLBUID;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.obs.businessunit.dao.BusinessUnitDao;
import com.obs.businessunit.facade.BusinessUnitFacade;
import com.obs.businessunit.model.BusinessUnit;
import com.obs.businessunit.request.BusinessUnitRequest;
import com.obs.businessunit.response.BusinessUnitResponse;


@Service
public class BusinessUnitFacadeImpl implements BusinessUnitFacade{
	@Autowired
	BusinessUnitDao businessUnitDao;
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> setBusinessUnit(BusinessUnitRequest businessUnitRequest) throws SQLException{

		logger.debug("inside saveEmployeeEducation method : " + businessUnitRequest);
		BusinessUnitResponse businessUnitResponse = null;


			if (businessUnitRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
				businessUnitResponse = new BusinessUnitResponse();
				boolean businessUnit = businessUnitDao.saveBusinessUnit(businessUnitRequest);
				logger.debug("inside  save condition.****** : " + businessUnit);
				if (businessUnit) {
					businessUnitResponse.setMessage("Successfully record added"); 
					businessUnitResponse.setStatusCode("200");
					return new ResponseEntity<>(businessUnitResponse, HttpStatus.OK);
				} else {
					businessUnitResponse.setMessage(FAILED);
					return new ResponseEntity<>(businessUnitResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
			if (businessUnitRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
				businessUnitResponse = new BusinessUnitResponse();
				boolean updateEducation = businessUnitDao.updateBusinessUnit(businessUnitRequest);
				if (updateEducation) {
					businessUnitResponse.setMessage("Successfully record updated");
					businessUnitResponse.setStatusCode("200");
					return new ResponseEntity<>(businessUnitResponse, HttpStatus.OK);
				} else {
					businessUnitResponse.setMessage(FAILED);
					return new ResponseEntity<>(businessUnitResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
			
			if (businessUnitRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
				businessUnitResponse = new BusinessUnitResponse();
	        	boolean deleteBusinessUnit = businessUnitDao.deleteBusinessUnit(businessUnitRequest);
				if (deleteBusinessUnit) {
					businessUnitResponse.setMessage("Successfully record updated");
					businessUnitResponse.setStatusCode("200");
					return new ResponseEntity<>(businessUnitResponse, HttpStatus.OK);
				} else {
					businessUnitResponse.setMessage(FAILED);
					return new ResponseEntity<>(businessUnitResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
		
			return null;
	}
	@Override
	public ResponseEntity<Object> getBusinessUnit(BusinessUnitRequest businessUnitRequest) throws SQLException {
		
		BusinessUnitResponse businessUnitResponse = new BusinessUnitResponse();
		List<BusinessUnit> allBusinessUnitDetails=null;
		//List<String> list=new ArrayList<>();
		logger.debug("inside getAllEducationDetails in EmployeeEducationFacde.***");
		if(businessUnitRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
		 allBusinessUnitDetails = businessUnitDao.getAllBussinessDetails(businessUnitRequest);
		}
		if (businessUnitRequest.getTransactionType().equalsIgnoreCase(GETBYID)){
			allBusinessUnitDetails = businessUnitDao.getById(businessUnitRequest);
		}
		if (allBusinessUnitDetails == null) {
			//businessUnitResponse.setBusinessUnitList(new ArrayList<>());
			businessUnitResponse.setMessage("No records found");
			businessUnitResponse.setStatusCode("409");
		} else {
			businessUnitResponse.setBusinessUnitList(allBusinessUnitDetails);
			businessUnitResponse.setMessage(SUCCESS);
		}
		if (businessUnitRequest.getTransactionType().equalsIgnoreCase(GETALLBUID)){
			List<String> list = null;
			list = businessUnitDao.getBuHeads(businessUnitRequest);
			if (list == null || list.isEmpty()) {
				//businessUnitResponse.setBusinessUnitList(new ArrayList<>());
				businessUnitResponse.setMessage("No records found");
				businessUnitResponse.setStatusCode("409");
			} else {
					businessUnitResponse.setBusinessUnitList(allBusinessUnitDetails);
					businessUnitResponse.setBuHeads(list);
					businessUnitResponse.setMessage(SUCCESS);
				}
		}

		
		
		return new ResponseEntity<>(businessUnitResponse, HttpStatus.OK);
		
	}
}
