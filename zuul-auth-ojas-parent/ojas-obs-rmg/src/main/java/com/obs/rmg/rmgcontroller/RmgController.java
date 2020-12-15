package com.obs.rmg.rmgcontroller;

import static com.obs.rmg.rmgconstants.RmgUrlConstants.GET;
import static com.obs.rmg.rmgconstants.RmgUrlConstants.SET;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.DELETE;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.FAILED;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.FIELDNULL;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GENERIC;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETBYID;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETDEPLOYEDRESOURCESBYSKILLS;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETEMPIDBYSKILLS;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETPROJECTSBYEMPID;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETRESOURCESBYPROJECT;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.RELEASE;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.SAVE;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.SPECIFIC;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.UPDATE;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.UPDATEBOOKING;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.SAVETASKS;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.UPDATETASKS;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETRESOURCEPROJECTTASKS;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETALLTASKS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.obs.rmg.rmgfacade.RmgFacade;
import com.obs.rmg.rmgmodel.RmgGeneric;
import com.obs.rmg.rmgmodel.RmgSpecific;
import com.obs.rmg.rmgrequest.RmgRequest;
import com.obs.rmg.rmgresponse.ErrorResponse;

@RestController
//@RequestMapping(RMG)
public class RmgController {

	@Autowired
	private RmgFacade rmgFacade;

	@PostMapping(SET)
	public ResponseEntity<Object> setCustomerContactInfo(@RequestBody RmgRequest rmgRequest,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		
	    
		try {
			if (rmgRequest == null || rmgRequest.getRmgInfo() == null || rmgRequest.getTransactiontype() == null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("reqest object is null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
				if ((rmgRequest.getTransactiontype().equalsIgnoreCase(SAVE) && rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(SPECIFIC)))
				{
					for(RmgSpecific rmgspecific:rmgRequest.getRmgSpecificList())
				
					if(rmgRequest.getRmgInfo()==null || rmgspecific.getStartDate()==null || rmgspecific.getEndDate()==null
					   || rmgspecific.getEmployeeId()==null || rmgspecific.getBillRate()== 0.0)
					{
						ErrorResponse error = new ErrorResponse();
						error.setStatusCode("422");
						error.setStatusMessage(FIELDNULL);
						
					}

				}
				
				if (rmgRequest.getTransactiontype().equalsIgnoreCase(SAVETASKS) && (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(SPECIFIC)||rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(GENERIC)))
				{
					
					if(rmgRequest.getRmgInfo().getBookingId()==0)
					{
						ErrorResponse error = new ErrorResponse();
						error.setStatusCode("422");
						error.setStatusMessage(FIELDNULL);
						
					}

				}
				else if((rmgRequest.getTransactiontype().equalsIgnoreCase(SAVE) && rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(GENERIC)))
				{
					for( RmgGeneric rmggeneric: rmgRequest.getRmgGenericList())	
					{ 
						if(rmgRequest.getRmgInfo()==null || rmggeneric.getResourceSkills()==null || rmggeneric.getResourceExperience().isNaN(0) 
								|| rmggeneric.getStartDate()==null || rmggeneric.getEndDate()==null || rmggeneric.getBillRate()==0.0 ||rmggeneric.getResourceCount()==0 ) {
						
						ErrorResponse error = new ErrorResponse();
						error.setStatusCode("422");
						error.setStatusMessage(FIELDNULL);
						}
						
					}
	
			}
				

	if (rmgRequest.getTransactiontype().equalsIgnoreCase(UPDATE) && rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(SPECIFIC) && rmgRequest.getRmgInfo().getBookingId()==0) {

		if((rmgRequest.getRmgInfo().getStatus()==null
		    ||rmgRequest.getRmgInfo().getStatus().isEmpty()))
	    {
						ErrorResponse error = new ErrorResponse();
						error.setStatusCode("422");
						error.setStatusMessage(FIELDNULL);
						
		}
		else if((rmgRequest.getTransactiontype().equalsIgnoreCase(UPDATE) &&rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(GENERIC) && rmgRequest.getRmgInfo().getBookingId()==0))
	   {
			      if((rmgRequest.getRmgInfo().getStatus()==null
				        ||rmgRequest.getRmgInfo().getStatus().isEmpty()))
						{
							ErrorResponse error = new ErrorResponse();
							error.setStatusCode("422");
							error.setStatusMessage(FIELDNULL);
							
						}
					}

	}
	
	if (rmgRequest.getTransactiontype().equalsIgnoreCase(UPDATEBOOKING) && (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(SPECIFIC) || rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(GENERIC)) && rmgRequest.getRmgInfo().getBookingId()==0) {

		if((rmgRequest.getRmgInfo().getResourceType()==null
		    ||rmgRequest.getRmgInfo().getResourceType().isEmpty()))
	    {
						ErrorResponse error = new ErrorResponse();
						error.setStatusCode("422");
						error.setStatusMessage(FIELDNULL);
						
		}

	}

	if (rmgRequest.getTransactiontype().equalsIgnoreCase(UPDATETASKS) && (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(SPECIFIC)||rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(GENERIC)) && rmgRequest.getRmgInfo().getBookingId()==0) {

		if((rmgRequest.getRmgInfo().getStatus()==null
		    ||rmgRequest.getRmgInfo().getStatus().isEmpty()))
	    {
						ErrorResponse error = new ErrorResponse();
						error.setStatusCode("422");
						error.setStatusMessage(FIELDNULL);
						
		}

	}
	if(rmgRequest.getTransactiontype().equalsIgnoreCase(DELETE) && rmgRequest.getRmgInfo().getBookingId()==0 &&rmgRequest.getRmgInfo().getFlag() == null) {
		
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setStatusMessage(FIELDNULL);
			
		
	}
	
	if(rmgRequest.getTransactiontype().equalsIgnoreCase(RELEASE) && rmgRequest.getRmgInfo().getBookingId()==0) {
		
		ErrorResponse error = new ErrorResponse();
		error.setStatusCode("422");
		error.setStatusMessage(FIELDNULL);

}
			return rmgFacade.setRmg(rmgRequest);
		} catch (DuplicateKeyException e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage("duplicate key Exception");

			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			e.printStackTrace();
			error.setStatusMessage(FAILED);
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

	}

	@PostMapping(GET)
	public ResponseEntity<Object> getCustomerContactInfoDetails(@RequestBody RmgRequest rmgRequest,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

		try {
			if ( rmgRequest.getTransactiontype() == null
					|| rmgRequest.getTransactiontype().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("transaction type is null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETBYID) && rmgRequest.getRmgInfo().getBookingId()==0) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("Type must be getbyid");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (rmgRequest.getTransactiontype()==null && rmgRequest.getRmgInfo().getProjectId()==null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("project id id null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETEMPIDBYSKILLS)&& rmgRequest.getSkilllist()==null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("Type must be getAll");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETPROJECTSBYEMPID)&& rmgRequest.getProjectlist()==null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("Type must be getAll");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETRESOURCESBYPROJECT)&& rmgRequest.getEmployeeprojects().getProjectId()==null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("getresources data is empty");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETDEPLOYEDRESOURCESBYSKILLS) && rmgRequest.getEmployeeskills()==null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("getdeployedresources data is empty");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETRESOURCEPROJECTTASKS) && rmgRequest.getResourceprojecttasks()==null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("getdeployedresources data is empty");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			//getallworkedhours
			if (rmgRequest.getTransactiontype() == null || rmgRequest.getTransactiontype().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("getall allocation of hours data is empty");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return rmgFacade.getRmg(rmgRequest);
		} catch (Exception e) {
			ErrorResponse er = new ErrorResponse();
			er.setStatusCode("409");
			er.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(er, HttpStatus.CONFLICT);
		}
	}

}
