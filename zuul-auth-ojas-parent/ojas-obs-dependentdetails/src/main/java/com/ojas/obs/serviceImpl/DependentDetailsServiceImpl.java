package com.ojas.obs.serviceImpl;

import static com.ojas.obs.constants.DependentDetailsContants.DELETE;
import static com.ojas.obs.constants.DependentDetailsContants.GETALL;
import static com.ojas.obs.constants.DependentDetailsContants.GETBYID;
import static com.ojas.obs.constants.DependentDetailsContants.SAVE;
import static com.ojas.obs.constants.DependentDetailsContants.UPDATE;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ojas.obs.dao.DependentDetailsDao;
import com.ojas.obs.model.DependentDetails;
import com.ojas.obs.request.DependentDetailsRequest;
import com.ojas.obs.response.DependentDetailsResponse;
import com.ojas.obs.service.DependentDetailsService;

/**
 * 
 * @author srinukummari
 *
 */
@Component
public class DependentDetailsServiceImpl implements DependentDetailsService {
	Logger logger = Logger.getLogger(this.getClass());
	DependentDetailsResponse dependentDetailsResponse = null; 
	@Autowired
	DependentDetailsDao dependentDetailsDaoImpl;
	
	/*
	 * (non-Javadoc)
	 * @see com.ojas.obs.service.DependentDetailsService#setDependentDetails(com.ojas.obs.request.DependentDetailsRequest)
	 */
	@Override
	public DependentDetailsResponse setDependentDetails(
			DependentDetailsRequest dependentDetailsRequestObject)throws SQLException {
		List<DependentDetails> dependentDetails = dependentDetailsRequestObject.getDependentDetails();
		logger.debug("The request details in Service set method: " + dependentDetailsRequestObject);
		dependentDetailsResponse = new DependentDetailsResponse();
		if (dependentDetailsRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) {
			boolean b=false;
			for(DependentDetails details : dependentDetails) {
			if (((details.getDependent_Name() == null) 
					|| (details.getDependent_Name().equalsIgnoreCase("")))
					|| ((details.getRelation() == null)
					|| (details.getRelation().equalsIgnoreCase("")))
					|| ((details.getDate_Of_Birth() == null))
					|| ((details.getEmployee_Id() == null||details.getEmployee_Id().isEmpty())
					|| ((details.getCreated_By() == null||details.getCreated_By().isEmpty())))) {
				logger.debug("data is  invalid "+dependentDetailsRequestObject );
				dependentDetailsResponse.setStatusCode("422");
				dependentDetailsResponse.setMessage("Improper data");
				b=true; 
				break;
			}
			}
			if(b) {}
			else {
				int saveDependentDetails = dependentDetailsDaoImpl.saveDependentDetails(dependentDetailsRequestObject);
				
				if (saveDependentDetails != 0) {
					dependentDetailsResponse.setStatusCode("200");
					logger.debug("inside  save method Success fully record added.****** : " + saveDependentDetails);
					dependentDetailsResponse.setMessage("DependentDetails have saved successfully");
				}
			}
			return dependentDetailsResponse;
		}
		
		
	
		else if (dependentDetailsRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
			boolean b = false;
			for(DependentDetails details : dependentDetails) {
					if (0 != details.getId()) {
						if (((details.getDependent_Name() == null) || (details.getDependent_Name().equalsIgnoreCase("")))
								|| ((details.getRelation() == null)|| (details.getRelation().equalsIgnoreCase("")))
								|| ((details.getDate_Of_Birth() == null))|| ((details.getEmployee_Id() == null||details.getEmployee_Id().isEmpty())
								|| ((details.getUpdated_By() == null ||details.getUpdated_By().isEmpty())))) {
							logger.debug("data is  invalid "+dependentDetailsRequestObject );
								dependentDetailsResponse.setStatusCode("422");
								dependentDetailsResponse.setMessage("Improper data");
								b=true;
								
						}
					}
			}
			if(b) {}
			else {
				int updateDependentDetails = dependentDetailsDaoImpl.updateDependentDetails(dependentDetailsRequestObject);
				dependentDetailsResponse.setStatusCode("409");
				dependentDetailsResponse.setMessage("sorry DependentDetails are not updated");
				
						if (updateDependentDetails != 0) {
							dependentDetailsResponse.setStatusCode("200");
							logger.debug("inside  setMethod Success fully record Updated.****** : " + updateDependentDetails);
							dependentDetailsResponse.setMessage("DependentDetails are updated successfully");
							
						}
			}
				
			return dependentDetailsResponse;
		}
		
		else if (dependentDetailsRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {
			boolean b = false;
			for(DependentDetails details : dependentDetails) {
				if (0 == details.getId()) {
					dependentDetailsResponse.setStatusCode("422");
					logger.debug("Please provid the id :" +details.getId());
					dependentDetailsResponse.setMessage("please provide the id");
					b= true;

				}
			}
			if(b) {}
			else {
					int deleteDependentDetails = dependentDetailsDaoImpl.deleteDependentDetails(dependentDetailsRequestObject);
					dependentDetailsResponse.setStatusCode("409");
					dependentDetailsResponse.setMessage("sorry DependentDetails are not updated");
					
						if (deleteDependentDetails != 0) {
							dependentDetailsResponse.setStatusCode("200");
							logger.debug("inside  setMethod Success fully record Deleted.****** : " + deleteDependentDetails);
							dependentDetailsResponse.setMessage("DependentDetails are deleted successfully");
						}
				}
			}	
			else {
				dependentDetailsResponse.setMessage("please provide the id");
				logger.debug("Please provid the id ");
				dependentDetailsResponse.setStatusCode("409");
			}	
			return dependentDetailsResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ojas.obs.service.DependentDetailsService#getDependentDetails(com.ojas.obs.request.DependentDetailsRequest)
	 */
	@Override
	public DependentDetailsResponse getDependentDetails(DependentDetailsRequest dependentDetailsRequestObject)
			throws SQLException {
		dependentDetailsResponse = new DependentDetailsResponse();
		logger.debug("The request details in Service get method: " + dependentDetailsRequestObject);
		if (dependentDetailsRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
			List<DependentDetails> allDependentDetails = dependentDetailsDaoImpl.getAll(dependentDetailsRequestObject);
			
			if (!allDependentDetails.isEmpty()) {
				dependentDetailsResponse.setStatusCode("200");
				logger.debug("you got the list of DependentDetails successfully" + allDependentDetails);
				dependentDetailsResponse.setMessage("you got the list of DependentDetails successfully");
				dependentDetailsResponse.setGetDependentDetailsList(allDependentDetails);;
			} else {
				dependentDetailsResponse.setGetDependentDetailsList(allDependentDetails);
				dependentDetailsResponse.setStatusCode("200");
				logger.debug("no records found: " + allDependentDetails);
				dependentDetailsResponse.setMessage("no records found");
			}
		}
		
		else if (dependentDetailsRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)) {
			List<DependentDetails> all = null;
			
			String EmpId=dependentDetailsRequestObject.getDependentDetails().get(0).getEmployee_Id();
			if(EmpId == null) {
				all = dependentDetailsDaoImpl.getById(dependentDetailsRequestObject.getDependentDetails().get(0).getId());
				logger.debug("you got the list of DependentDetails successfully Using id: " + all);
			}
			else {
				all = dependentDetailsDaoImpl.getByEmpId(dependentDetailsRequestObject.getDependentDetails().get(0).getEmployee_Id());
				logger.debug("you got the list of DependentDetails successfully Using employee_id:  " + all);
			
			}
			
			
			if (!all.isEmpty()) {
				dependentDetailsResponse.setStatusCode("200");
				logger.debug("you got the list of DependentDetails successfully" + all);
				dependentDetailsResponse.setMessage("you got the list of DependentDetails successfully");
				dependentDetailsResponse.setGetDependentDetailsList(all);;
			} else {
				dependentDetailsResponse.setGetDependentDetailsList(all);
				dependentDetailsResponse.setStatusCode("200");
				logger.debug("No records Found " + all);
				dependentDetailsResponse.setMessage("no records found");
			}
		}
		
		else {
			dependentDetailsResponse.setStatusCode("422");
			logger.debug("Please provide valid transactionType: "+dependentDetailsRequestObject.getTransactionType() );
			dependentDetailsResponse.setMessage("please provide valid transaction type");
		}
		
		return dependentDetailsResponse;
	}


	
}
