package com.ojas.obs.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.request.DeliverylocationRequest;

@Service
public interface DeliverylocationFacade
{

	public ResponseEntity<Object> saveDetails(DeliverylocationRequest cmsRequestObject);
	public ResponseEntity<Object> getDetails(DeliverylocationRequest cmsRequestObject);
}
