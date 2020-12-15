package com.ojas.obs.facadeImpl;

import static com.ojas.obs.constants.InsuranceConstants.DELETE;
import static com.ojas.obs.constants.InsuranceConstants.FAILED;
import static com.ojas.obs.constants.InsuranceConstants.SAVE;
import static com.ojas.obs.constants.URLconstants.GETAll;
import static com.ojas.obs.constants.InsuranceConstants.UPDATE;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.dao.InsuranceDao;
import com.ojas.obs.errorMessage.ErrorResponse;
import com.ojas.obs.facade.InsuranceFacade;
import com.ojas.obs.model.Insurance;
import com.ojas.obs.request.InsuranceRequest;
import com.ojas.obs.response.InsuranceResponse;

@Service
public class InsuranceFacadeImpl implements InsuranceFacade {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	InsuranceDao insuranceDao;

	@Override
	public ResponseEntity<Object> saveInsuranceDetails(InsuranceRequest insuranceRequest) throws SQLException {

		logger.debug("inside saveInsuranceDetails method : " + insuranceRequest);
		InsuranceResponse insuranceResponse = null;

		try {
			if (insuranceRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
				insuranceResponse = new InsuranceResponse();
				boolean saveInsurance = insuranceDao.saveInsuranceDetails(insuranceRequest);
				logger.debug("inside  save condition.****** : " + saveInsurance);
				if (saveInsurance) {

					insuranceResponse.setStatusMessage("Success fully record added");
					return new ResponseEntity<>(insuranceResponse, HttpStatus.OK);
				} else {
					insuranceResponse.setStatusMessage(FAILED);
					return new ResponseEntity<>(insuranceResponse, HttpStatus.CONFLICT);
				}
			}

			if (insuranceRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
				insuranceResponse = new InsuranceResponse();

				boolean updateEmployee = insuranceDao.updateInsuranceDetails(insuranceRequest);
				if (updateEmployee) {
					insuranceResponse.setStatusMessage("Success fully record updated");
					return new ResponseEntity<>(insuranceResponse, HttpStatus.OK);
				}
			}
			if (insuranceRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
				insuranceResponse = new InsuranceResponse();
				Integer planId = insuranceRequest.getInsurance().getId();
				boolean deleteEmployeeRecord = insuranceDao.deleteInsuranceDetails(insuranceRequest);

				logger.debug("inside  delete condition.****** and id is : " + planId);
				if (deleteEmployeeRecord) {
					insuranceResponse.setStatusMessage("Success fully record deleted");
					return new ResponseEntity<>(insuranceResponse, HttpStatus.OK);
				} else {
					insuranceResponse.setStatusMessage(FAILED);
					return new ResponseEntity<>(insuranceResponse, HttpStatus.CONFLICT);
				}
			}

		} catch (Exception e) {
			logger.debug("inside InsuranceFacadeImpl catch block.****");
			ErrorResponse error = new ErrorResponse();
			logger.debug("data is  invalid");
			error.setMessage(e.getMessage());
		}
		return null;
	}

	@Override
	public ResponseEntity<Object> getAllInsuranceDetails(InsuranceRequest insuranceRequest) throws SQLException {

		logger.debug("inside getAllInsuranceDetails in InsuranceFacadeImpl.***");
		InsuranceResponse insuranceResponse = new InsuranceResponse();
		if (insuranceRequest.getTransactionType().equalsIgnoreCase(GETAll)) {
			List<Insurance> allEmployeeDetails = insuranceDao.getAllInsuranceDetails(insuranceRequest);
			if (allEmployeeDetails == null) {

				insuranceResponse.setInsurance(new ArrayList<>());
				insuranceResponse.setStatusMessage("No records found");
				insuranceResponse.setTotalCount(0);
				return new ResponseEntity<>(insuranceResponse, HttpStatus.CONFLICT);
			} else {
				int count = insuranceDao.getAllInsuranceDetailsCount();
				insuranceResponse.setInsurance(allEmployeeDetails);
				insuranceResponse.setStatusMessage("Success");
				insuranceResponse.setTotalCount(count);
				return new ResponseEntity<Object>(insuranceResponse, HttpStatus.OK);
			}
		}

		return null;
	}

}
