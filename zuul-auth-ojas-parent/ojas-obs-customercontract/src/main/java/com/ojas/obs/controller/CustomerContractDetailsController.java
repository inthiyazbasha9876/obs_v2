package com.ojas.obs.controller;

import static com.ojas.obs.constant.UserConstants.GET;
import static com.ojas.obs.constant.UserConstants.SAVE;
import static com.ojas.obs.constant.UserConstants.UPDATE;
import static com.ojas.obs.constant.UserConstants.SET;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.facadeimpl.CustomerContractDetailsFacadeImpl;
import com.ojas.obs.model.CustomerContractDetails;
import com.ojas.obs.request.CustomerContractDetailsRequest;
import com.ojas.obs.response.CustomerContractDetailsErrorResponse;

@RestController
public class CustomerContractDetailsController {

	@Autowired
	private CustomerContractDetailsFacadeImpl customerContractDetailsFacade;

	private final Logger logger = Logger.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> setCustomerContractDetails(
			@RequestBody CustomerContractDetailsRequest customerContractDetailsReq, HttpServletRequest req,
			HttpServletResponse res) {
		CustomerContractDetailsErrorResponse error = null;

		try {
			logger.debug("request data into controller" + customerContractDetailsReq);
			List<CustomerContractDetails> contractlist = customerContractDetailsReq.getCustomercontractdetailslist();

			for (CustomerContractDetails con : contractlist) {

				if (customerContractDetailsReq.getTransactiontype() == null
						|| customerContractDetailsReq.getTransactiontype().isEmpty()
						|| (contractlist == null || contractlist.isEmpty())
						) {
					error = new CustomerContractDetailsErrorResponse();
					error.setStatusCode("422");
					error.setMessage("Invalid Request Object");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}

				if (customerContractDetailsReq.getTransactiontype().equalsIgnoreCase(SAVE)
						|| customerContractDetailsReq.getTransactiontype().equalsIgnoreCase(UPDATE)) {
					
					if (con.getCustomerid() == null || con.getContractvalue() == null
							|| (con.getContractname() == null || con.getContractname().isEmpty())
							|| (con.getContractowner() == null || con.getContractowner().isEmpty())
							|| (con.getBuHead() == null || con.getBuHead().isEmpty())
							|| (con.getSbuHead() == null || con.getSbuHead().isEmpty())
							|| (con.getContractcompany() == null || con.getContractcompany().isEmpty())
							|| (con.getCreatedby() == null || con.getCreatedby().isEmpty())
							|| (con.getContractType() == null || con.getContractType().isEmpty())
							
							) {

						error = new CustomerContractDetailsErrorResponse();
						error.setStatusCode("422");
						error.setMessage("Invalid Request Object fields");
						return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					}
					
				}
			}

			return customerContractDetailsFacade.setCustomerContractDetails(customerContractDetailsReq);

		} catch (DuplicateKeyException e) {
			error = new CustomerContractDetailsErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage("duplicate key Exception");
			error.setStatusMessage(e.getCause().getLocalizedMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (SQLException e) {
			logger.debug("SQLException caught!");
			error = new CustomerContractDetailsErrorResponse();
			error.setStatusCode("409");
			error.setMessage("SQLException caught");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);

		} catch (Exception exception) {
			error = new CustomerContractDetailsErrorResponse();
			error.setStatusCode("409");
			error.setMessage("Exception Occured!");
			error.setStatusMessage(exception.getMessage());
			exception.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}

	@PostMapping(GET)
	public ResponseEntity<Object> getCustomerContractDetails(
			@RequestBody CustomerContractDetailsRequest customerContractDetailsReq, HttpServletRequest req,
			HttpServletResponse res) {

		CustomerContractDetailsErrorResponse error = null;
		try {
			logger.debug("received data into controller" + customerContractDetailsReq);
			if (null == customerContractDetailsReq) {
				error = new CustomerContractDetailsErrorResponse();
				logger.debug("data is not valid");
				error.setStatusCode("422");
				error.setMessage("Request is not valid");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}
			if (null == customerContractDetailsReq.getTransactiontype()
					|| customerContractDetailsReq.getTransactiontype().isEmpty()) {
				error = new CustomerContractDetailsErrorResponse();
				logger.debug("data is not valid");
				error.setStatusCode("422");
				error.setMessage("Transaction type should not be null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return customerContractDetailsFacade.getCustomerContractDetails(customerContractDetailsReq);

		} catch (SQLException e) {
			logger.debug("SQLException caught!");
			error = new CustomerContractDetailsErrorResponse();
			error.setStatusCode("409");
			error.setMessage("SQLException caught");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception exception) {
			error = new CustomerContractDetailsErrorResponse();
			error.setStatusCode("409");
			error.setMessage("Exception Occured!");
			error.setStatusMessage(exception.getMessage());
			exception.printStackTrace();
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

	}

}
