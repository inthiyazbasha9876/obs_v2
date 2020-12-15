package com.ojas.obs.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.request.GstlocationRequest;

@Service
public interface GstlocationFacade
{

	public ResponseEntity<Object> saveDetails(GstlocationRequest cmsRequestObject);
	public ResponseEntity<Object> getDetails(GstlocationRequest cmsRequestObject);
}
