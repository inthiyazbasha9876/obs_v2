package com.ojas.obs.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.request.ServicecategoryRequest;


@Service
public interface ServicecategoryFacade {

	public ResponseEntity<Object> saveDetails(ServicecategoryRequest cmsRequestObject);
	public ResponseEntity<Object> getDetails(ServicecategoryRequest cmsRequestObject);
	
}
