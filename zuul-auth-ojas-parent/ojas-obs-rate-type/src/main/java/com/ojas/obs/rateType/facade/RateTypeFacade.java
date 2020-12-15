package com.ojas.obs.rateType.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.rateType.request.RateTypeRequest;

@Service
public interface RateTypeFacade {
	public ResponseEntity<Object>saveRateType(RateTypeRequest rateTypeRequest);
	 public ResponseEntity<Object> getCustomerDetails(RateTypeRequest rateTypeRequest);
}
