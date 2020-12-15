package com.ojas.obs.permstatus.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.permstatus.request.PermStatusRequest;

@Service
public interface PermStatusFacade {

	public ResponseEntity<Object> savePermStatus(PermStatusRequest permStatusRequestObject);
	 public	ResponseEntity<Object> getPermStatus(PermStatusRequest permStatusRequestObject);

}
