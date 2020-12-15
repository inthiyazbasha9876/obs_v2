package com.ojas.obs.facade;

import static com.ojas.obs.constants.UserConstants.DELETE;
import static com.ojas.obs.constants.UserConstants.FAILED;
import static com.ojas.obs.constants.UserConstants.NORECORDS;
import static com.ojas.obs.constants.UserConstants.SAVE;
import static com.ojas.obs.constants.UserConstants.SUCCESS;
import static com.ojas.obs.constants.UserConstants.UPDATE;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.dao.BankDetailsDAO;
import com.ojas.obs.model.BankDetails;
import com.ojas.obs.request.BankDetailsRequest;
import com.ojas.obs.response.BankDetailsResponse;

/**
 * 
 * @author akrishna
 *
 */

@Service
public class BankDetailsFacade {

	Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private BankDetailsDAO bankDetailsDAO;

	/**
	 * 
	 * @param bankDetailsRequest
	 * @return
	 * @throws SQLException 
	 */
	public ResponseEntity<Object> setBankDetails(BankDetailsRequest bankDetailsRequest) throws SQLException {

		logger.debug("incomming request in facade " + bankDetailsRequest);
		
			if (bankDetailsRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
				logger.debug("Transactiontype is " );
				BankDetailsResponse bankDetailsResponse = new BankDetailsResponse();
				boolean saveBank = bankDetailsDAO.saveEmployeeBankDetails(bankDetailsRequest);

				if (saveBank) {
					logger.debug("object stored in db " + saveBank);
					bankDetailsResponse.setMessage("BankDetails record is saved Successfully");
					bankDetailsResponse.setSatusCode("200");
					return new ResponseEntity<>(bankDetailsResponse, HttpStatus.OK);
				}
			}
			if (bankDetailsRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
				logger.debug("Transactiontype is  " + UPDATE);
				BankDetailsResponse bankDetailsResponse = new BankDetailsResponse();
				boolean updateBank = bankDetailsDAO.updateEmployeeBankDetails(bankDetailsRequest);
				if (updateBank) {
					logger.debug("object updated in db " + updateBank);
					bankDetailsResponse.setMessage("BankDetails record is updated Successfully");
					bankDetailsResponse.setSatusCode("200");
					return new ResponseEntity<>(bankDetailsResponse, HttpStatus.OK);
				}
			}
			if (bankDetailsRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
				logger.debug("Transactiontype is  " + DELETE);
				BankDetailsResponse bankDetailsResponse = new BankDetailsResponse();

				boolean deleteBank = bankDetailsDAO.deleteEmployeeBankDetails(bankDetailsRequest);

				if (deleteBank) {
					logger.debug("object deleted in db " + deleteBank);
					bankDetailsResponse.setMessage("BankDetails record is deleted Successfully");
					bankDetailsResponse.setSatusCode("200");
					return new ResponseEntity<>(bankDetailsResponse, HttpStatus.OK);
				}
			}
			logger.debug("object not deleted in db ");
			BankDetailsResponse bankDetailsResponse = new BankDetailsResponse();
			bankDetailsResponse.setMessage(FAILED);
			bankDetailsResponse.setSatusCode("409");
			return new ResponseEntity<>(bankDetailsResponse, HttpStatus.CONFLICT);

		 
	}

	/**
	 * 
	 * @param bankDetailsRequest
	 * @return
	 */
	public ResponseEntity<Object> getBankDetails(BankDetailsRequest bankDetailsRequest) throws SQLException {
		BankDetailsResponse bankDetailsResponse = null;
		logger.debug("get request inside facade " + bankDetailsRequest);
	
			List<BankDetails> allBankDetails = bankDetailsDAO.getAllBankDetails(bankDetailsRequest);
			logger.debug("response in facade " + allBankDetails);
			if (null == allBankDetails || allBankDetails.isEmpty()) {
				bankDetailsResponse = new BankDetailsResponse();
				bankDetailsResponse.setMessage(NORECORDS);
				bankDetailsResponse.setSatusCode("406");
				bankDetailsResponse.setListBankDetails(new ArrayList<>());
				return new ResponseEntity<>(bankDetailsResponse, HttpStatus.NOT_ACCEPTABLE);
			} else {
				bankDetailsResponse = new BankDetailsResponse();
				bankDetailsResponse.setMessage(SUCCESS);
				bankDetailsResponse.setSatusCode("200");
				bankDetailsResponse.setListBankDetails(allBankDetails);

			}

			return new ResponseEntity<>(bankDetailsResponse, HttpStatus.OK);
	}
}
