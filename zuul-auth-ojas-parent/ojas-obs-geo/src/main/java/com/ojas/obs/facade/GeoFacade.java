package com.ojas.obs.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.request.GeoRequest;

@Service
public interface GeoFacade {
	public ResponseEntity<Object> saveDetails(GeoRequest geoRequestObject);

	public ResponseEntity<Object> getDetails(GeoRequest geoRequestObject);

}
