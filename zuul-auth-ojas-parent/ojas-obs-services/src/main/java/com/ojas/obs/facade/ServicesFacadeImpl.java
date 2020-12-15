package com.ojas.obs.facade;


import static com.ojas.obs.constants.Constants.GETALL;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.UPDATE;
import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.FAILED;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.dao.ServicesDao;
import com.ojas.obs.model.Services;
import com.ojas.obs.request.ServicesRequest;
import com.ojas.obs.response.ServicesResponse;

@Service
public class ServicesFacadeImpl implements ServicesFacade {

	@Autowired
	private ServicesDao servicesDao;
	Logger logger = Logger.getLogger(this.getClass());

    @Override
	public ResponseEntity<Object> setServices(ServicesRequest servicesRequestObj) throws SQLException {
        logger.info("@@@@Inside setServices facade : " + servicesRequestObj);
        
		ServicesResponse servicesResponse = null;
		
			if (servicesRequestObj.getTransactionType().equalsIgnoreCase(SAVE)) {
				servicesResponse = new ServicesResponse();
				boolean saveservice = servicesDao.saveServices(servicesRequestObj);
				if (saveservice) {
					logger.debug("@@@@Inside save transaction facade : " + saveservice);
					servicesResponse.setMessage("Successfully record added");
					return new ResponseEntity<>(servicesResponse, HttpStatus.OK);
				} else {
					logger.debug("@@@@Inside save transaction failed facade : " + saveservice);
					servicesResponse.setMessage(FAILED);
					return new ResponseEntity<>(servicesResponse, HttpStatus.CONFLICT);
				}
			}
			if (servicesRequestObj.getTransactionType().equalsIgnoreCase(UPDATE)) {
				logger.debug("@@@@Inside update transaction condition.");
				servicesResponse = new ServicesResponse();
				boolean updateService = servicesDao.updateServices(servicesRequestObj);
				
				if (updateService) {
					logger.debug("@@@@Inside update transaction condition::Successfully record updated");
					servicesResponse.setMessage("Successfully record updated");
					
					return new ResponseEntity<>(servicesResponse, HttpStatus.OK);
				} else {
					logger.debug("@@@@Inside update transaction condition:: record failed to update");
					servicesResponse.setMessage(FAILED);
					return new ResponseEntity<>(servicesResponse, HttpStatus.CONFLICT);
				}
			}
			
			if (servicesRequestObj.getTransactionType().equalsIgnoreCase(DELETE)) {
				logger.debug("@@@@Inside update transaction condition.");
				servicesResponse = new ServicesResponse();
				boolean deleteService = servicesDao.deleteServices(servicesRequestObj);
				
				if (deleteService) {
					logger.debug("@@@@Inside update transaction condition::Successfully record updated");
					servicesResponse.setMessage("Successfully record delete");
					
					return new ResponseEntity<>(servicesResponse, HttpStatus.OK);
				} else {
					logger.debug("@@@@Inside update transaction condition:: record failed to delete");
					servicesResponse.setMessage(FAILED);
					return new ResponseEntity<>(servicesResponse, HttpStatus.CONFLICT);
				}
			}
			
		boolean b = (servicesRequestObj.getTransactionType().equalsIgnoreCase(SAVE)
					|| servicesRequestObj.getTransactionType().equalsIgnoreCase(UPDATE));
					if (! b) {
						logger.debug("@@@@Inside setServices: transaction Type not correct");
						servicesResponse = new ServicesResponse();
						servicesResponse.setStatusCode("422");
						servicesResponse.setMessage("transaction type is not correct");
					}
					return new ResponseEntity<>(servicesResponse, HttpStatus.CONFLICT);
		 
	}
    
	@Override
	public ResponseEntity<Object> getServices(ServicesRequest servicesRequestObj) throws SQLException {

		ServicesResponse servicesResponse = new ServicesResponse();
		logger.info("@@@@Inside getServices in facade");
		List<Services> ServiceList=null;
		
		if(servicesRequestObj.getTransactionType().equalsIgnoreCase(GETALL)) {
			ServiceList = servicesDao.getAll(servicesRequestObj);
			servicesResponse.setServicesList(ServiceList);
			
			servicesResponse.setMessage("Record found");
			servicesResponse.setStatusCode("200");
			
		}else if(servicesRequestObj.getTransactionType().equalsIgnoreCase(GETBYID)){
			ServiceList = servicesDao.getServiceById(servicesRequestObj);
			servicesResponse.setServicesList(ServiceList);
			servicesResponse.setMessage("Record found");
			servicesResponse.setStatusCode("200");
		}
		if ( ServiceList == null) { 
			logger.debug("@@@@Inside getServices in facade::ServiceList is null");
			servicesResponse.setServicesList(new ArrayList<>());
			servicesResponse.setMessage("No records found");
		
		
		}
		return new ResponseEntity<Object>(servicesResponse, HttpStatus.OK);
	}

}
