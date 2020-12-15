package com.ojas.obs.sez.facade;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.sez.request.SezRequest;

public interface SezFacade {
	public ResponseEntity<Object> saveDetails(SezRequest sezRequest)  ;
	public ResponseEntity<Object> getAllDetails(SezRequest sezRequest) ;

}
