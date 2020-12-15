package com.ojas.obs.country.facadeImpl;

import static com.ojas.obs.country.constants.Constants.DELETE;
import static com.ojas.obs.country.constants.Constants.GETALL;
import static com.ojas.obs.country.constants.Constants.GETBYID;
import static com.ojas.obs.country.constants.Constants.SAVE;
import static com.ojas.obs.country.constants.Constants.UPDATE;
import static com.ojas.obs.country.constants.Constants.GETBYGEOID;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.country.facade.CountryFacade;
import com.ojas.obs.country.model.Country;
import com.ojas.obs.country.repositories.CountryRepository;
import com.ojas.obs.country.request.CountryRequest;
import com.ojas.obs.country.response.ErrorResponse;

import com.ojas.obs.country.response.CountryResponse;

@Service
public class CountryFacadeImpl implements CountryFacade {
	@Autowired
	private CountryRepository countryDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseEntity<Object> saveDetails(CountryRequest countryRequest) throws DuplicateKeyException, Exception {
		List<Country> list = countryRequest.getCountrylist();
		for (Country country : list) {
			if (countryRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
				Country country2 = countryDao.save(country);
				if (country2.getId() != null) {
					CountryResponse countryResponse = new CountryResponse();
					countryResponse.setMessage("record saved successfully");
					countryResponse.setStatusCode("200");
					return new ResponseEntity<Object>(countryResponse, HttpStatus.OK);
				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("failed to save record");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<Object>(errorResponse, HttpStatus.CONFLICT);
			}

			if (countryRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {

				Country fetchedRecord = countryDao.getOne(country.getId());
				if (fetchedRecord.getId() != null) {
					countryDao.save(country);
					CountryResponse response = new CountryResponse();
					response.setMessage("updated successfully");
					response.setStatusCode("200");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("not updated");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<Object>(errorResponse, HttpStatus.CONFLICT);
			}
			if (countryRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
				Country country2 = countryDao.getOne(country.getId());
				country2.setStatus(!country2.getStatus());
				Country country3 = countryDao.save(country2);
				if (country3.getId() != null) {
					CountryResponse countryResponse = new CountryResponse();
					countryResponse.setMessage("sucessfully deleted");
					countryResponse.setStatusCode("200");
					return new ResponseEntity<Object>(countryResponse, HttpStatus.OK);
				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("not deleted");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<Object>(errorResponse, HttpStatus.CONFLICT);

			}
		}
		return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
	}

	public ResponseEntity<Object> getAllDetails(CountryRequest countryRequest) throws DuplicateKeyException, Exception {
		logger.debug("request coming to the facade");
		List<Country> list = countryRequest.getCountrylist();
		List<Country> list2 = new ArrayList<Country>();
		if (countryRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			list2 = countryDao.findAll();
			if (!list2.isEmpty()) {
				CountryResponse response = new CountryResponse();
				response.setMessage("successfully get all the records");
				response.setStatusCode("200");
				response.setCountrylist(list2);
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("list is empty");
			errorResponse.setStatusCode("409");
			return new ResponseEntity<Object>(errorResponse, HttpStatus.CONFLICT);
		}
		if (countryRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
			Country findById = countryDao.getOne(countryRequest.getCountrylist().get(0).getId());

			if (findById != null) {
				list2.add(findById);
				CountryResponse response = new CountryResponse();
				response.setMessage("successfully get the single record");
				response.setStatusCode("200");
				response.setCountrylist(list2);
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("record is not getting");
			errorResponse.setStatusCode("409");
			return new ResponseEntity<Object>(errorResponse, HttpStatus.CONFLICT);

		}
 
		if (countryRequest.getTransactionType().equalsIgnoreCase(GETBYGEOID) && list.get(0).getGeoId() != null) {

		
			ArrayList<Country> listDetails = new ArrayList<>(); 

			for (Country geodetails : list) {
				if (geodetails.getGeoId() != null) {

					Integer geoId = geodetails.getGeoId();
					ArrayList<Country> geoId2 = countryDao.getByGeoId(geoId);

					if (!geoId2.isEmpty()) {
						CountryResponse response = new CountryResponse();
						response.setCountrylist(geoId2);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<>(response, HttpStatus.OK);
					}
					CountryResponse response = new CountryResponse();
					response.setStatusCode("422");
					response.setMessage("feild is null");
					return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);

				}
			}
		}
		return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
	}
}
