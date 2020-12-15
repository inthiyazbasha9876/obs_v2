package com.ojas.obs.facadeimpl;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.GETALL;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.UPDATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.model.Geo;

import com.ojas.obs.repository.GeoRepository;
import com.ojas.obs.request.GeoRequest;
import com.ojas.obs.response.GeoResponse;


@Service
public class GeoFacadeImpl {  
	@Autowired
	private GeoRepository geoRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseEntity<Object> saveDetails(GeoRequest geoRequestObject) {

		GeoResponse response = null;

		logger.debug("request coming to the facade");

		if (geoRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) {
			response = new GeoResponse();

			List<Geo> geo = geoRequestObject.getGeoList();
			List<Geo> save = geoRepo.saveAll(geo);

			if (!save.isEmpty()) {
				logger.debug("save method"); 
				response.setStatusCode("200");
				response.setMessage("Geo type details saved successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			response.setStatusCode("409");
			response.setMessage("failed to save");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (geoRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new GeoResponse();

			for (Geo tech : geoRequestObject.getGeoList()) {
				Integer id = geoRequestObject.getGeoList().get(0).getGeoId();
				Optional<Geo> findById = geoRepo.findById(id);
				if (findById.isPresent() && findById.get().getGeoId() != null) {
					geoRepo.save(tech);
					response.setStatusCode("200");
					response.setMessage("GeoType details updated successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					logger.debug("update method");
					response.setStatusCode("409");
					response.setMessage("failed to update");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}
			}
		}  

		if (geoRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {
			response = new GeoResponse();

			Integer id = geoRequestObject.getGeoList().get(0).getGeoId();
			Geo ser = geoRepo.getOne(id);

			ser.setStatus(!ser.getStatus());
			Geo locationdata = geoRepo.save(ser);

			if (locationdata.getGeoId() != null) {
				response.setStatusCode("200");
				response.setMessage("geoType details deleted successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setStatusCode("422");
			response.setMessage("failed to delete");
			return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<Object> getDetails(GeoRequest geoRequestObject) {
		List<Geo> list = geoRequestObject.getGeoList();
		logger.debug(" getAll customer details");
		GeoResponse response = null;
		List<Geo> getAll = null;

		if (geoRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = geoRepo.findAll();
			if (getAll.isEmpty()) {
				response = new GeoResponse();
				response.setGeoList(new ArrayList<Geo>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
			}
			response = new GeoResponse();
			response.setGeoList(getAll);
			response.setMessage("success");
			response.setStatusCode("200");
			return new ResponseEntity<Object>(response, HttpStatus.OK);

		}
		if (geoRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
				&& list.get(0).getGeoId() != null) {

			for (Geo type : list) {

				if (type.getGeoId() != null) {
					Integer id = geoRequestObject.getGeoList().get(0).getGeoId();

					ArrayList<Geo> listDetails = new ArrayList<Geo>();

					Geo getById = geoRepo.findById(id).orElse(new Geo());

					listDetails.add(getById);

					if (getById != null && getById.getGeoId() != null) {
						response = new GeoResponse();
						response.setGeoList(listDetails);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<Object>(response, HttpStatus.OK); 
					}
					response = new GeoResponse();
					response.setStatusCode("422");
					response.setMessage("please provide valid id");
					return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

				}
			}
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
