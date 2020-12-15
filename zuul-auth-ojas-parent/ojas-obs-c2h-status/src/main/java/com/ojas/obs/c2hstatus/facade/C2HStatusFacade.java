package com.ojas.obs.c2hstatus.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.c2hstatus.request.C2HStatusRequest;

@Service
public interface C2HStatusFacade {

	public ResponseEntity<Object> saveC2HStatus(C2HStatusRequest c2hstatusRequestObject);
	public ResponseEntity<Object> getC2HStatus(C2HStatusRequest c2hstatusRequestObject);
}
