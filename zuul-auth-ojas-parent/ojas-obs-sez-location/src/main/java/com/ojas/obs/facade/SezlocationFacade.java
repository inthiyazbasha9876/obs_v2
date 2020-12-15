package com.ojas.obs.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.request.SezlocationRequest;

@Service
public interface SezlocationFacade
{

	public ResponseEntity<Object> saveDetails(SezlocationRequest cmsRequestObject);
	public ResponseEntity<Object> getDetails(SezlocationRequest cmsRequestObject);
}
