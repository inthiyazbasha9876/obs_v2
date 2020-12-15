package com.ojas.obs.resourcetype.controller;

import static com.ojas.obs.resourcetype.constant.ResourceTypesConstants.GET;
import static com.ojas.obs.resourcetype.constant.ResourceTypesConstants.SET;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.resourcetype.exception.DataNotInsertedException;
import com.ojas.obs.resourcetype.exception.EmploymentDetailsException;
import com.ojas.obs.resourcetype.facade.ResourceTypeFacade;
import com.ojas.obs.resourcetype.model.ErrorResponse;
import com.ojas.obs.resourcetype.model.ResourceTypeRequest;
import com.ojas.obs.resourcetype.model.ResourceTypeResponse;

/**
 * resource class inserts,updates,deletes and retrieves data into/from
 * employment_details table
 * 
 * @author akrishna
 *
 */
@RestController
//@RequestMapping(EMPLOYMENT_DETAILS)
public class ResourceTypeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceTypeController.class);

	@Autowired
	private ResourceTypeFacade resourceTypeFacade;

	/**
	 * resource inserts,updates,deletes data into employment_details table
	 * 
	 * @param employmentDetailsData
	 * @return
	 * @throws EmploymentDetailsException
	 * @throws DataNotInsertedException
	 */
	@PostMapping(SET)
	public ResponseEntity<Object> setEmploymentDetails(@RequestBody ResourceTypeRequest resourceTypeRequest)
			throws SQLException {

		LOGGER.debug("The requested object is ");
		ResourceTypeResponse employementDetailsResponse = null;
		ErrorResponse error = new ErrorResponse();
		ResponseEntity<Object> responseEntity = null;
		try {
			if (null == resourceTypeRequest) {
				LOGGER.error("the requested object is null");
			}
			employementDetailsResponse = resourceTypeFacade.saveResourceTypes(resourceTypeRequest);
			LOGGER.debug("response object is ");
			return new ResponseEntity<>(employementDetailsResponse, HttpStatus.OK);

		} catch (DuplicateKeyException dke) {
			error.setMessage("duplicates are not allowed");
			error.setStatusCode("409");
			error.setStatusMessage(dke.getCause().getLocalizedMessage());
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (SQLException ede) {
			error.setStatusMessage(ede.getMessage());
			error.setStatusCode("409");
			error.setStatusMessage(ede.getMessage());
			error.setMessage("SQLException occured");
			ede.printStackTrace();
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception sq) {
			error.setStatusMessage(sq.getMessage());
			error.setStatusCode("409");
			error.setMessage("Exception occured");
			responseEntity = new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

		return responseEntity;

	}

	/**
	 * resource retrieves data from employment_details table
	 * 
	 * @param employmentDetailsData
	 * @return
	 * @throws EmploymentDetailsException
	 */
	@PostMapping(GET)
	public ResponseEntity<Object> getResourceTypes(@RequestBody ResourceTypeRequest resourceTypeRequest) {

		LOGGER.debug("requested object is ");
		ResourceTypeResponse resourceTypeResponse = null;
		ErrorResponse error = new ErrorResponse();
		if (null != resourceTypeRequest) {
			LOGGER.error("the requested object is null");
			try {
				LOGGER.debug("response object is ");
				resourceTypeResponse = resourceTypeFacade.viewResourceTypes(resourceTypeRequest);
			} catch (SQLException e) {
				error.setStatusMessage(e.getCause().getMessage());
				error.setMessage("EmploymentDetailsException");
				error.setStatusCode("409");
				error.setMessage(e.getCause().getLocalizedMessage());
				return new ResponseEntity<>(error, HttpStatus.CONFLICT);
			} catch (Exception e) {
				error.setStatusMessage(e.getCause().getMessage());
				error.setMessage("Employment Details Exception");
				error.setStatusCode("409");
				error.setMessage(e.getCause().getLocalizedMessage());
				return new ResponseEntity<>(error, HttpStatus.CONFLICT);
			}
		}

		return new ResponseEntity<>(resourceTypeResponse, HttpStatus.OK);

	}

}
