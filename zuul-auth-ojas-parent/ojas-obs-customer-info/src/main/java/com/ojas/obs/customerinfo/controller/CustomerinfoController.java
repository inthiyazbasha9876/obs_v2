package com.ojas.obs.customerinfo.controller;

import static com.ojas.obs.customerinfo.constants.UrlConstants.SET;
import static com.ojas.obs.customerinfo.constants.UrlConstants.GET;

import static com.ojas.obs.customerinfo.constants.UtilConstants.DELETE;
import static com.ojas.obs.customerinfo.constants.UtilConstants.SAVE;
import static com.ojas.obs.customerinfo.constants.UtilConstants.UPDATE;
import static com.ojas.obs.customerinfo.constants.UtilConstants.GETBYID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ojas.obs.customerinfo.facade.CustomerinfoFacade;
import com.ojas.obs.customerinfo.model.Customer;
import com.ojas.obs.customerinfo.request.CustomerinfoRequest;
import com.ojas.obs.customerinfo.response.ErrorResponse;

//@RequestMapping("obs/customerinfo")
@Controller
public class CustomerinfoController {
	@Autowired
	private CustomerinfoFacade customerinfoFacade;

//	@Autowired

	@PostMapping(SET)
	public ResponseEntity<Object> setCustomerinfo(@RequestBody CustomerinfoRequest request, HttpServletRequest req,
			HttpServletResponse res) {
		if (request == null || request.getTransactionType() == null || request.getTransactionType().isEmpty()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage("Invalid Request");
			return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			if (request.getTransactionType().equalsIgnoreCase(SAVE)) {
				if (request.getShippingaddressList() == null || request.getCustomerList() == null
						|| request.getContactinfoList() == null || request.getCustomergstList() == null
						|| request.getBillinginfoList() == null || request.getRegisteredaddress() == null
						|| request.getServicetype() == null || request.getBillingaddressList()==null) {
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("422");
					error.setStatusMessage(" field is null");
					return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
			if (request.getTransactionType().equalsIgnoreCase(UPDATE)) {
				if (request.getShippingaddressList() == null || request.getCustomerList() == null
						|| request.getContactinfoList() == null || request.getCustomergstList() == null
						|| request.getBillinginfoList() == null || request.getRegisteredaddress() == null
						|| request.getServicetype() == null || request.getBillingaddressList()==null) {
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("424");
					error.setStatusMessage(" field is null");
					return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
			if (request.getTransactionType().equalsIgnoreCase(DELETE)) {
				if ( request.getCustomerList() == null) {
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("426");
					error.setStatusMessage(" field is null");
					return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
			return customerinfoFacade.setCustomer(request);
		} catch (DuplicateKeyException e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage("duplicate key Exception");
			error.setStatusMessage(e.getCause().getLocalizedMessage());
			return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
		}

		catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setMessage("Exception caught!");
			error.setStatusMessage(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
		}

	}

	@PostMapping(GET)
	public ResponseEntity<Object> getCustomerinfo(@RequestBody CustomerinfoRequest request, HttpServletRequest req,
			HttpServletResponse res) {

		if (request == null || request.getTransactionType() == null || request.getTransactionType().isEmpty()) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage("Invalid Request");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			Customer customer = request.getCustomerList();

			if (request.getTransactionType().equalsIgnoreCase(GETBYID)) {
				if (request.getCustomerList() == null) {
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("422");
					error.setMessage("customer ID must not be null");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}

			}
			return customerinfoFacade.getCustomerinfo(request);

		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setMessage("Exception caught!");
			error.setStatusMessage(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
		}
	}

}
