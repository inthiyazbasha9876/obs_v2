package com.ojas.obs.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.request.ServiceTypeRequest;

@Service
public interface ServiceTypeFacade {
	public ResponseEntity<Object> saveDetails(ServiceTypeRequest serRequestObject);

	public ResponseEntity<Object> getDetails(ServiceTypeRequest serRequestObject);
}
