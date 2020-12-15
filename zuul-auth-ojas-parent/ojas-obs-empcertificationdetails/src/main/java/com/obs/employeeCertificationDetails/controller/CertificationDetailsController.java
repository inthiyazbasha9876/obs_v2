package com.obs.employeeCertificationDetails.controller;


import static com.obs.employeeCertificationDetails.constants.Constants.DELETE;
import static com.obs.employeeCertificationDetails.constants.Constants.GETCERTIFICATION;
import static com.obs.employeeCertificationDetails.constants.Constants.ID_NULL;
import static com.obs.employeeCertificationDetails.constants.Constants.MODEL_NULL;
import static com.obs.employeeCertificationDetails.constants.Constants.REQUEST_DATA_MISSING;
import static com.obs.employeeCertificationDetails.constants.Constants.REQUEST_NULL;
import static com.obs.employeeCertificationDetails.constants.Constants.SAVE;
import static com.obs.employeeCertificationDetails.constants.Constants.SETCERTIFICATION;
import static com.obs.employeeCertificationDetails.constants.Constants.UPDATE;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.obs.employeeCertificationDetails.error.ErrorResponse;
import com.obs.employeeCertificationDetails.facade.CertificationDetailsFacade;
import com.obs.employeeCertificationDetails.model.CertificationDetails;
import com.obs.employeeCertificationDetails.request.CertificationDetailsRequest;

@RestController
public class CertificationDetailsController {
	@Autowired
	private CertificationDetailsFacade facadeImpl;
	Logger logger = Logger.getLogger(this.getClass());

	@PostMapping(SETCERTIFICATION)
	public ResponseEntity<Object> setCertificationDetails(@RequestBody CertificationDetailsRequest certificationDetailsRequestObject) {
		ErrorResponse error = null;
		logger.info("@@@@ Inside setCertificationDetails controller::"+certificationDetailsRequestObject);
		try {

            if (null == certificationDetailsRequestObject) { 
            	logger.debug("@@@@ Request Object is null");
				error = new ErrorResponse();
				error.setMessage(REQUEST_NULL);
				error.setCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
            List<CertificationDetails> certificationDetailsList = certificationDetailsRequestObject.getCertificationDetailsModel();
            if (certificationDetailsList == null) {
				logger.debug("@@@@ certificationDetailsList is null");
				error = new ErrorResponse();
				error.setMessage(MODEL_NULL);
				error.setCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
            for(CertificationDetails certificationDetails:certificationDetailsList) {
            	if((certificationDetailsRequestObject.getTransactionType().equalsIgnoreCase(SAVE)
    				&& null == certificationDetails.getCertificationName()
    						|| null == certificationDetails.getCreatedBy()
    						|| null == certificationDetails.getIssuedBy())){
    					logger.debug("@@@@data is  invalid");
    					error = new ErrorResponse();
    					error.setMessage(REQUEST_DATA_MISSING);
    					error.setCode("422");
    					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    				
    		    }
    			if((certificationDetailsRequestObject.getTransactionType().equalsIgnoreCase(UPDATE) 
    					|| certificationDetailsRequestObject.getTransactionType().equalsIgnoreCase(DELETE))
    					&&(certificationDetails.getId()==null)
    					&&(certificationDetails.getUpdatedBy()==null)) {
    				logger.debug("@@@@@@@@@data is  invalid");
    				error = new ErrorResponse();
    				error.setMessage(ID_NULL);
    				error.setCode("422");
    				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    			}
            }
			return facadeImpl.setCertificationDetails(certificationDetailsRequestObject);
        } catch (SQLException exception) {
			error = new ErrorResponse();
			logger.info("Exception",exception);
			error.setMessage("Exception Cought");
			error.setStatusMessage(exception.getMessage());
			error.setCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);

	}catch (Exception exception) {
		logger.debug("inside CostCenterController-SQLException catch block.****");
		 error = new ErrorResponse();
		logger.debug("Exception is  invalid");
		error.setMessage("Exception");
		error.setStatusMessage(exception.getMessage());
		error.setCode("409");
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
		
	}

	// get and get All Function call
	@PostMapping(GETCERTIFICATION)
	public ResponseEntity<Object> getCertificationDetails(@RequestBody CertificationDetailsRequest certificationDetailsRequestObject) {
		ErrorResponse error = null;
		try {
            if (null == certificationDetailsRequestObject) {
				logger.debug("request is not valid");
                error = new ErrorResponse();
				error.setMessage(REQUEST_NULL);
				error.setCode("422");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return facadeImpl.getCertificationDetails(certificationDetailsRequestObject);
		} catch (SQLException exception) {
			error = new ErrorResponse();
			error.setMessage("Exception cought");
			error.setStatusMessage(exception.getMessage());
			error.setCode("409");
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);

	}catch (Exception exception) {
		logger.debug("inside CostCenterController-SQLException catch block.****");
		 error = new ErrorResponse();
		logger.debug("Exception is  invalid");
		error.setMessage("exception");
		error.setStatusMessage(exception.getMessage());
		error.setCode("409");
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	}
}
