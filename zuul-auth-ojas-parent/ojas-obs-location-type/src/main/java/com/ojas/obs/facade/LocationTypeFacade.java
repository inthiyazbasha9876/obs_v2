package com.ojas.obs.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.request.LocationTypeRequest;
@Service
public interface LocationTypeFacade {
	public ResponseEntity<Object> saveDetails(LocationTypeRequest locationTypeRequestObject);

	public ResponseEntity<Object> getDetails(LocationTypeRequest locationTypeRequestObject);

}
