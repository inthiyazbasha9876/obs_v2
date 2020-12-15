package com.obs.psa.customercontactinfo.facade;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.obs.psa.customercontactinfo.model.CustomerContactInfo;
import com.obs.psa.customercontactinfo.request.CustomerContactInfoRequest;

public interface CustomerContactInfoFacade {
	
	/*
	 * public CustomerContactInfo saveCustomerContactInfo(CustomerContactInfo
	 * customerContactInfo); public CustomerContactInfo
	 * getCustomerContactInfo(Integer customerId) ; public CustomerContactInfo
	 * editCustomerContactInfo(CustomerContactInfo customerContactInfo); public
	 * List<CustomerContactInfo> getAllCustomerContactInfo(); public void
	 * deleteCustomerContactInfo(Integer customerId);
	 */
	
	
	ResponseEntity<Object> setCustomerContactInfo(CustomerContactInfoRequest customerContactInfoRequest);

	ResponseEntity<Object> getCustomerContactInfo(CustomerContactInfoRequest customerContactInfoRequest);
	
	
	
	
	

}
