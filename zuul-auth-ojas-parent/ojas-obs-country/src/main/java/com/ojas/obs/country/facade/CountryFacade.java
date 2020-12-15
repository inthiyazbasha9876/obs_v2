package com.ojas.obs.country.facade;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.country.request.CountryRequest;

public interface CountryFacade {
	public ResponseEntity<Object> saveDetails(CountryRequest countryRequest) throws DuplicateKeyException, Exception;

	public ResponseEntity<Object> getAllDetails(CountryRequest countryRequest) throws DuplicateKeyException, Exception;

}
