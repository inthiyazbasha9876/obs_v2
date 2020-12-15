package com.ojas.obs.facade;

import static com.ojas.obs.constants.Constants.FAILED;
import static com.ojas.obs.constants.Constants.GETALL;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.UPDATE;
import static com.ojas.obs.constants.Constants.DELETE;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.dao.StatesDao;
import com.ojas.obs.model.States;
import com.ojas.obs.request.StatesRequest;
import com.ojas.obs.response.StatesResponse;

@Service
public class StatesFacadeImpl implements StatesFacade {

	@Autowired
	private StatesDao statesDao;
	Logger logger = Logger.getLogger(this.getClass());

    @Override
	public ResponseEntity<Object> setStates(StatesRequest statesRequestObj) throws SQLException {
        logger.info("@@@@Inside setStates facade : " + statesRequestObj);
		StatesResponse statesResponse = null;
			if (statesRequestObj.getTransactionType().equalsIgnoreCase(SAVE)) {
				statesResponse = new StatesResponse();
				boolean savestate = statesDao.saveStates(statesRequestObj);
				if (savestate) {
					logger.debug("@@@@Inside save transaction facade : " + savestate);
					statesResponse.setMessage("Successfully record added");
					return new ResponseEntity<>(statesResponse, HttpStatus.OK);
				} else {
					logger.debug("@@@@Inside save transaction failed facade : " + savestate);
					statesResponse.setMessage(FAILED);
					return new ResponseEntity<>(statesResponse, HttpStatus.CONFLICT);
				}
			}
			if (statesRequestObj.getTransactionType().equalsIgnoreCase(UPDATE)) {
				logger.debug("@@@@Inside update transaction condition.");
				statesResponse = new StatesResponse();
				boolean updateState = statesDao.updateStates(statesRequestObj);
				
				if (updateState) {
					logger.debug("@@@@Inside update transaction condition::Successfully record updated");
					statesResponse.setMessage("Successfully record updated");
					
					return new ResponseEntity<>(statesResponse, HttpStatus.OK);
				} else {
					logger.debug("@@@@Inside update transaction condition:: record failed to update");
					statesResponse.setMessage(FAILED);
					return new ResponseEntity<>(statesResponse, HttpStatus.CONFLICT);
				}
			}
			if (statesRequestObj.getTransactionType().equalsIgnoreCase(DELETE)) {
				logger.debug("@@@@Inside update transaction condition.");
				statesResponse = new StatesResponse();
				boolean deleteState = statesDao.deleteStates(statesRequestObj);
				
				if (deleteState) {
					logger.debug("@@@@Inside update transaction condition::Successfully record updated");
					statesResponse.setMessage("Successfully record delete");
					
					return new ResponseEntity<>(statesResponse, HttpStatus.OK);
				} else {
					logger.debug("@@@@Inside update transaction condition:: record failed to delete");
					statesResponse.setMessage(FAILED);
					return new ResponseEntity<>(statesResponse, HttpStatus.CONFLICT);
				}
			}
			
		boolean b = (statesRequestObj.getTransactionType().equalsIgnoreCase(SAVE)
					|| statesRequestObj.getTransactionType().equalsIgnoreCase(UPDATE));
					if (! b) {
						logger.debug("@@@@Inside setStates: transaction Type not correct");
						statesResponse = new StatesResponse();
						statesResponse.setStatusCode("422");
						statesResponse.setMessage("transaction type is not correct");
					}
					return new ResponseEntity<>(statesResponse, HttpStatus.CONFLICT);
		 
	}
    
	@Override
	public ResponseEntity<Object> getStates(StatesRequest statesRequestObj) throws SQLException {

		StatesResponse statesResponse = new StatesResponse();
		logger.info("@@@@Inside getStates in facade");
		List<States> stateList=null;
		if(statesRequestObj.getTransactionType().equalsIgnoreCase(GETALL)) {
		 stateList = statesDao.getAll(statesRequestObj);
		 statesResponse.setStatesList(stateList);
		 statesResponse.setMessage("Record found");
		 statesResponse.setStatusCode("200");
		}else if(statesRequestObj.getTransactionType().equalsIgnoreCase(GETBYID)){
			stateList = statesDao.getStateById(statesRequestObj);
			 statesResponse.setStatesList(stateList);
			 statesResponse.setMessage("Record found");
			 statesResponse.setStatusCode("200");
		}
		if ( stateList == null) { 
			logger.debug("@@@@Inside getStates in facade::StateList is null");
			statesResponse.setStatesList(new ArrayList<>());
			statesResponse.setMessage("No records found");
		
		
		}
		return new ResponseEntity<>(statesResponse, HttpStatus.OK);
	}

}
