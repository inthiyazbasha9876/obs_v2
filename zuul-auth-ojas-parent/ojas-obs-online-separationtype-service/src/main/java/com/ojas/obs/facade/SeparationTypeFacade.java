package com.ojas.obs.facade;

import java.sql.SQLException;
import java.util.List;

import static com.ojas.obs.constants.SeparationTypeConstants.SAVE;
import static com.ojas.obs.constants.SeparationTypeConstants.UPDATE;
import static com.ojas.obs.constants.SeparationTypeConstants.GETBYID;
import static com.ojas.obs.constants.SeparationTypeConstants.SUCCESS;
import static com.ojas.obs.constants.SeparationTypeConstants.GETALL;
import static com.ojas.obs.constants.SeparationTypeConstants.DELETE;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ojas.obs.constants.SeparationTypeConstants;
import com.ojas.obs.dao.SeparationTypeDao;
import com.ojas.obs.model.SeparationType;
import com.ojas.obs.request.SeparationTypeRequest;
import com.ojas.obs.response.SeparationTypeResponse;

/**
 * 
 * @author nsrikanth
 * 
 */
@Service
public class SeparationTypeFacade {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SeparationTypeDao separationTypeDao;

	/**
	 * 
	 * @param separationTypeRequest
	 * @return
	 * @throws SQLException
	 */

	public ResponseEntity<Object> setSeparationTypeDetails(SeparationTypeRequest separationTypeRequest)
			throws SQLException {
		logger.info("The request Inside SeparationTypeFacade setSeparationTypeDetails method " + separationTypeRequest);

		SeparationTypeResponse separationTypeResponse = new SeparationTypeResponse();
		boolean result = false;

		if (separationTypeRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
			result = separationTypeDao.saveSeparationType(separationTypeRequest);
			logger.debug("Inside SeparationTypeFacade save condition  " + result);
			if (result) {
				separationTypeResponse.setMessage(SeparationTypeConstants.SAVED);
				separationTypeResponse.setStatusCode("200");
				return new ResponseEntity<Object>(separationTypeResponse, HttpStatus.OK);
			} else {
				logger.error("Inside SeparationTypeFacade  save condition  " + result);
				separationTypeResponse.setMessage(SeparationTypeConstants.FAILED);
				return new ResponseEntity<Object>(separationTypeResponse, HttpStatus.CONFLICT);
			}

		}
		if (separationTypeRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
			result = separationTypeDao.updateSeparationType(separationTypeRequest);
			logger.debug("Inside SeparationTypeFacade  update condition " + result);
			if (result) {
				separationTypeResponse.setMessage(SeparationTypeConstants.UPDATED);
				separationTypeResponse.setStatusCode("200");
				return new ResponseEntity<Object>(separationTypeResponse, HttpStatus.OK);
			} else {
				logger.error("Inside SeparationTypeFacade  update condition " + result);
				separationTypeResponse.setMessage(SeparationTypeConstants.FAILED);
				return new ResponseEntity<Object>(separationTypeResponse, HttpStatus.CONFLICT);
			}
		}

		if (separationTypeRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
			List<SeparationType> separationType = separationTypeRequest.getSeparationType();
			for (SeparationType sepa : separationType)
				result = separationTypeDao.deleteSeparationType(sepa.getSeparationTypeId());
			if (result) {
				separationTypeResponse.setMessage(SeparationTypeConstants.DELETED);
				return new ResponseEntity<Object>(separationTypeResponse, HttpStatus.OK);
			} else {
				separationTypeResponse.setMessage(SeparationTypeConstants.FAILED);
				return new ResponseEntity<Object>(separationTypeResponse, HttpStatus.CONFLICT);
			}
		}

		return null;

	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ResponseEntity<Object> getSeparationType(SeparationTypeRequest separationTypeRequest) throws SQLException {
		SeparationTypeResponse separationTypeResponse = new SeparationTypeResponse();

		logger.info("The request Inside SeparationTypeFacade getSeparationType method " + separationTypeRequest);

		if (separationTypeRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			logger.debug("Inside SeparationTypeFacade getAll condition " + separationTypeRequest);
			List<SeparationType> separationTypeList = separationTypeDao.getAllSeparationType();

			if (null != separationTypeList && separationTypeList.size() != 0) {
				logger.debug("Inside SeparationTypeFacade  listsize is " + separationTypeRequest);
				separationTypeResponse.setSeparationTypeList(separationTypeList);
				separationTypeResponse.setMessage(SeparationTypeConstants.SUCCESS);
				separationTypeResponse.setStatusCode("200");
				return new ResponseEntity<Object>(separationTypeResponse, HttpStatus.OK);
			} else {
				logger.error("Inside SeparationTypeFacade in list is no_records " + separationTypeRequest);
				separationTypeResponse.setMessage(SeparationTypeConstants.NO_RECORDS);
				return new ResponseEntity<Object>(separationTypeResponse, HttpStatus.CONFLICT);
			}

		}
		if (separationTypeRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {

			separationTypeResponse = new SeparationTypeResponse();
			List<SeparationType> list = separationTypeDao.getById(separationTypeRequest);
			logger.debug("inside  SeparationTypeFacade getbyid condition.****** : " + separationTypeRequest);
			if (list.size() == 0) {
				logger.error("Inside SeparationTypeFacade  listsize is zero " + separationTypeRequest);
				separationTypeResponse.setMessage("No record Present");
				// separationTypeResponse.setTotalCount(0);
				return new ResponseEntity<>(separationTypeResponse, HttpStatus.CONFLICT);
			} else {
				logger.debug("Inside SeparationTypeFacade  list is " + separationTypeRequest);
				separationTypeResponse.setMessage(SUCCESS);
				separationTypeResponse.setSeparationTypeList(list);
				return new ResponseEntity<>(separationTypeResponse, HttpStatus.OK);
			}

		}
		return new ResponseEntity<Object>(separationTypeResponse, HttpStatus.OK);

	}
}
