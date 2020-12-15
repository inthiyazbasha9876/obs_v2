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

import com.ojas.obs.facade.LocationTypeFacade;
import com.ojas.obs.model.LocationType;
import com.ojas.obs.repositories.LocationTypeRepository;
import com.ojas.obs.request.LocationTypeRequest;
import com.ojas.obs.response.LocationTypeResponse;

@Service
public class LocationTypeFacadeImpl implements LocationTypeFacade {

	@Autowired 
	private LocationTypeRepository locationTypeRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass()); 

	@Override
	public ResponseEntity<Object> saveDetails(LocationTypeRequest locationTypeRequestObject) {
		LocationTypeResponse response = null; 

		logger.debug("request coming to the facade");

		if (locationTypeRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) {
			response = new LocationTypeResponse();

			List<LocationType> locationtype = locationTypeRequestObject.getLocationTypeList();
			List<LocationType> save = locationTypeRepo.saveAll(locationtype);

			if (!save.isEmpty()) {
				logger.debug("save method"); 
				response.setStatusCode("200");
				response.setMessage("Location type details has saved successfully");
				return new ResponseEntity<>(response, HttpStatus.OK); 
			}

			response.setStatusCode("409");
			response.setMessage("failed to save");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (locationTypeRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new LocationTypeResponse();

			for (LocationType location : locationTypeRequestObject.getLocationTypeList()) {
				Integer id = locationTypeRequestObject.getLocationTypeList().get(0).getLocationtypeId();
				Optional<LocationType> findById = locationTypeRepo.findById(id);
				if (findById.isPresent() && findById.get().getLocationtypeId() != null) {
					locationTypeRepo.save(location);
					response.setStatusCode("200");
					response.setMessage("LocationType details has updated successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					logger.debug("update method");
					response.setStatusCode("409");
					response.setMessage("failed to update");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}
			}
		}  

		if (locationTypeRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {
			response = new LocationTypeResponse();

			Integer id = locationTypeRequestObject.getLocationTypeList().get(0).getLocationtypeId();
			LocationType location1 = locationTypeRepo.getOne(id);

			location1.setStatus(!location1.getStatus());
			LocationType locationdata = locationTypeRepo.save(location1);

			if (locationdata.getLocationtypeId() != null) {
				response.setStatusCode("200");
				response.setMessage("locationtype details has deleted successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setStatusCode("422");
			response.setMessage("failed to delete");
			return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getDetails(LocationTypeRequest locationTypeRequestObject) {
		List<LocationType> list = locationTypeRequestObject.getLocationTypeList();
		logger.debug(" getAll customer details");
		LocationTypeResponse response = null;
		List<LocationType> getAll = null;

		if (locationTypeRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = locationTypeRepo.findAll();
			if (getAll.isEmpty()) {
				response = new LocationTypeResponse();
				response.setLocationTypeList(new ArrayList<LocationType>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
			}
			response = new LocationTypeResponse();
			response.setLocationTypeList(getAll);
			response.setMessage("success");
			response.setStatusCode("200"); 
			return new ResponseEntity<Object>(response, HttpStatus.OK);

		}
		if (locationTypeRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
				&& list.get(0).getLocationtypeId() != null) {

			for (LocationType location : list) {

				if (location.getLocationtypeId() != null) {
					Integer id = locationTypeRequestObject.getLocationTypeList().get(0).getLocationtypeId();

					ArrayList<LocationType> listDetails = new ArrayList<LocationType>();

					LocationType getById = locationTypeRepo.findById(id).orElse(new LocationType());

					listDetails.add(getById);

					if (getById != null && getById.getLocationtypeId() != null) {
						response = new LocationTypeResponse();
						response.setLocationTypeList(listDetails);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					}
					response = new LocationTypeResponse();
					response.setStatusCode("422");
					response.setMessage("please provide valid id");
					return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

				}
			}
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
