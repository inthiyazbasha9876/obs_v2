package com.ojas.obs.passport.facadeImpl;

//import static com.ojas.obs.passport.utility.Constants.DELETE;
import static com.ojas.obs.passport.utility.Constants.GETALL;

import static com.ojas.obs.passport.utility.Constants.SAVE;
import static com.ojas.obs.passport.utility.Constants.UPDATE;
import static com.ojas.obs.passport.utility.Constants.GETBYID;
import static com.ojas.obs.passport.utility.Constants.DELETE;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.passport.Request.PassportRequest;
import com.ojas.obs.passport.Response.PassportResponse;
import com.ojas.obs.passport.daoImpl.PassportDaoImpl;
import com.ojas.obs.passport.facade.PassportFacade;
import com.ojas.obs.passport.model.Passport;

@Service
public class PassportFacadeImpl implements PassportFacade {

	@Autowired
	PassportDaoImpl passportDaoImpl;

	PassportResponse passportResponse = null;

	Logger logger = Logger.getLogger(this.getClass());

	// SAVE
	@Override
	public ResponseEntity<Object>  setPassport(PassportRequest passportRequestObject) throws SQLException {
		logger.info("@@@@received at service setPassport" );
		passportResponse = new PassportResponse();
		if (passportRequestObject.getTransaactionType().equalsIgnoreCase(SAVE)) {
			boolean savePassport = passportDaoImpl.savePassport(passportRequestObject);
			System.out.println("PassportFacadeImpl.setPassport()");
			System.out.println("save::"+savePassport);
			logger.debug("@@@@received at service by calling the savePassport is" + savePassport);
			if (savePassport) {
				System.out.println("PassportFacadeImpl.setPassport()$$$$SSSSS");
				logger.debug("@@@@inside  save condition.****** : ");
				passportResponse.setStatusCode("200");
				passportResponse.setMessage("PassportCenter has been saved successfully");
				
		return new ResponseEntity<Object>(passportResponse, HttpStatus.OK);
	}else {
		logger.debug("@@@@inside  save condition.****** : ");
		passportResponse.setStatusCode("422");
		passportResponse.setMessage("failed to save");
		return new ResponseEntity<Object>(passportResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}
		}
		  //UPDATE 
		if (passportRequestObject.getTransaactionType().equalsIgnoreCase(UPDATE)) {
			for(Passport passport : passportRequestObject.getPassportList()) {
			if (null != passport.getId()) {
				logger.debug("@@@@calling the updatePassport ");
				boolean updatePassport = passportDaoImpl.updatePassport(passportRequestObject);
				
				if (updatePassport) {
					logger.debug("@@@@inside  update condition.****** : ");
					passportResponse.setStatusCode("200");
					logger.debug("@@@@PassportCenter has updated successfully");
					passportResponse.setMessage("PassportCenter has been updated successfully");
					return new ResponseEntity<Object>(passportResponse, HttpStatus.OK);
				}else {
					logger.error("@@@@inside  update else condition.****** : ");
					passportResponse.setStatusCode("422");
					logger.error("@@@@failed to update****** : ");
					passportResponse.setMessage("failed to update");
					return new ResponseEntity<Object>(passportResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			} else {
				logger.error("@@@@inside  update condition.please provide the id****** : ");
				passportResponse.setMessage("please provide the id");
				return new ResponseEntity<Object>(passportResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
		}
			}
		
		  //DELETE
		if (passportRequestObject.getTransaactionType().equalsIgnoreCase(DELETE)) {
			for(Passport passport : passportRequestObject.getPassportList() ) {
			if (null != passport.getId()) {
				boolean deletePassport = passportDaoImpl.deletePassport(passportRequestObject);
				//int count1 = passportDaoImpl.getcountPassport(passportRequestObject);
				logger.debug("@@@@@@@@@@@@@received at service by calling the deletePassport is" + deletePassport);
				if (deletePassport) {
					logger.debug("inside  get_count condition.****** : ");
					passportResponse.setStatusCode("200");
					passportResponse.setMessage("PassportCenter has  deleted successfully");
					//passportResponse.setNoOfRecords(count1);
					return new ResponseEntity<Object>(passportResponse, HttpStatus.OK);
				}else {
					logger.debug("inside  get_count condition.****** : ");
					passportResponse.setStatusCode("200");
					passportResponse.setMessage("PassportCenter has not  deleted ");
					//passportResponse.setNoOfRecords(count1);
					return new ResponseEntity<Object>(passportResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
				
				} else {
				passportResponse.setStatusCode("422");
				passportResponse.setMessage("please provide the id");
				return new ResponseEntity<Object>(passportResponse, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
		}
		return new ResponseEntity<Object>(passportResponse, HttpStatus.OK);
	}
	// GETALL

	@Override
	public ResponseEntity<Object> getPassport(PassportRequest passportRequestObject) throws SQLException {
		logger.info("@@@@received at service by calling the getPassport");
		passportResponse = new PassportResponse();
		if (passportRequestObject.getTransaactionType().equalsIgnoreCase(GETALL)) {
			logger.debug("@@@@inside the getall condition in facade");
			List<Passport> allPassportDetails = passportDaoImpl.getAll(passportRequestObject);
			System.out.println("all method " + allPassportDetails);
			// List<Passport> perPage = passportDaoImpl.getCountPerPage(allPassportDetails,
			// passportRequestObject.getPageSize(),
			// passportRequestObject.getPageNo());
			logger.debug("@@@@received at service by calling the getAllmethod is" + allPassportDetails);

			if (allPassportDetails.isEmpty() || allPassportDetails == null) {
				logger.debug("@@@@@@ inside allPassportDetails is null or empty no records found condition ");
				passportResponse.setPassportList(allPassportDetails);
				passportResponse.setStatusCode("409");
				logger.error("@@@@no records found");
				passportResponse.setMessage("no records found");
				return new ResponseEntity<Object>(passportResponse, HttpStatus.CONFLICT);

			} else {
				logger.debug("@@@@@@ you got the list of PassportCenters successfully in else condition");
				passportResponse.setPassportList(allPassportDetails);
				passportResponse.setStatusCode("200");
				passportResponse.setMessage("You got the list of PassportCenters successfully");
				// passportResponse.setPassportList(perPage);

			}
			return new ResponseEntity<Object>(passportResponse, HttpStatus.OK);
		}
		if (passportRequestObject.getTransaactionType().equalsIgnoreCase(GETBYID)) {
			if (passportRequestObject.getPassportList().get(0).getId() == null) {
				logger.error("@@@@please provide id::");
				passportResponse.setMessage("please provide id::");
			} else {
				logger.debug("@@@@inside GETBYID conditon ");
				List<Passport> listOfPassport = passportDaoImpl.getById(passportRequestObject);
				passportResponse.setPassportList(listOfPassport);
				logger.debug("@@@@successfully retrieved your record details");
				passportResponse.setMessage("Record details has been retrieved successfully");
				return new ResponseEntity<Object>(passportResponse, HttpStatus.OK);
			}

		}

		return new ResponseEntity<Object>(passportResponse, HttpStatus.OK);
	}
}
