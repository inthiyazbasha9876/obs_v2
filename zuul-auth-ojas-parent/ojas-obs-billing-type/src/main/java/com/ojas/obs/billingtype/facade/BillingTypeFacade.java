package com.ojas.obs.billingtype.facade;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.billingtype.request.BillingTypeRequest;

public interface BillingTypeFacade {
	public ResponseEntity<Object> saveDetails(BillingTypeRequest billingTypeRequest);

	public ResponseEntity<Object> getAllDetails(BillingTypeRequest billingTypeRequest);

}
