package com.ojas.obs.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.request.SarStatusRequest;
@Service
public interface SarStatusFacade {
	public ResponseEntity<Object> saveDetails(SarStatusRequest sarRequestObject);

	public ResponseEntity<Object> getDetails(SarStatusRequest sarRequestObject);

}
