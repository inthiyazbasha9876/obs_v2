package com.ojas.obs.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.request.DocumentstageRequest;
@Service
public interface DocumentStageFacade {
	public ResponseEntity<Object> saveDetails(DocumentstageRequest docRequestObject);

	public ResponseEntity<Object> getDetails(DocumentstageRequest docRequestObject);
}
