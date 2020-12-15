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

import com.ojas.obs.facade.ServiceTypeFacade;
import com.ojas.obs.model.ServiceType;
import com.ojas.obs.repositories.ServiceTypeRepository;
import com.ojas.obs.request.ServiceTypeRequest;
import com.ojas.obs.response.ServiceTypeResponse;

@Service
public class ServiceTypeFacadeImpl implements ServiceTypeFacade {
	@Autowired
	private ServiceTypeRepository serviceTypeRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public ResponseEntity<Object> saveDetails(ServiceTypeRequest serRequestObject) {   
		ServiceTypeResponse response=null;

		logger.debug("request coming to the facade");

		if (serRequestObject.getTransactionType().equalsIgnoreCase(SAVE))
		{
			response = new ServiceTypeResponse();

			List<ServiceType> serviceType = serRequestObject.getServicetypeList();
			List<ServiceType> save = serviceTypeRepo.saveAll(serviceType);

			if (!save.isEmpty())
			{
				logger.debug("save method");
				response.setStatusCode("200");
				response.setMessage("Service Type details has saved successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

			response.setStatusCode("409");
			response.setMessage("failed to save");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} 
		if (serRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new ServiceTypeResponse();

			for (ServiceType tech : serRequestObject.getServicetypeList())
			{
				Integer id = serRequestObject.getServicetypeList().get(0).getId();
				Optional<ServiceType> findById = serviceTypeRepo.findById(id);
				if (findById.isPresent() && findById.get().getId() != null) 
				{
					serviceTypeRepo.save(tech);
					response.setStatusCode("200");
					response.setMessage("service details has updated successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} 
				else {
					logger.debug("update method");
					response.setStatusCode("409");
					response.setMessage("failed to update");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}
			}
		}

		if (serRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) 
		{
			response = new ServiceTypeResponse();


			Integer id = serRequestObject.getServicetypeList().get(0).getId();
			ServiceType ser = serviceTypeRepo.getOne(id);

			ser.setStatus(!ser.getStatus());
			ServiceType locationdata = serviceTypeRepo.save(ser);

			if (locationdata.getId() != null) 
			{
				response.setStatusCode("200");
				response.setMessage("service details has deleted successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} 
			response.setStatusCode("422");
			response.setMessage("failed to delete");
			return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@Override
	public ResponseEntity<Object> getDetails(ServiceTypeRequest serRequestObject) {
		List<ServiceType> list = serRequestObject.getServicetypeList();
		logger.debug(" getAll customer details");
		ServiceTypeResponse response = null;
		List<ServiceType> getAll = null;

		if (serRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = serviceTypeRepo.findAll();
			if (getAll.isEmpty()) {
				response = new ServiceTypeResponse();
				response.setServicetypeList(new ArrayList<ServiceType>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
			}
			response = new ServiceTypeResponse();
			response.setServicetypeList(getAll);
			response.setMessage("success");
			response.setStatusCode("200");
			return new ResponseEntity<Object>(response, HttpStatus.OK);

		}
		if (serRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
				&& list.get(0).getId() != null) {

			for (ServiceType type : list) {

				if (type.getId() != null) {
					Integer id = serRequestObject.getServicetypeList().get(0).getId();

					ArrayList<ServiceType> listDetails = new ArrayList<ServiceType>();

					ServiceType getById = serviceTypeRepo.findById(id).orElse(new ServiceType());

					listDetails.add(getById);

					if (getById != null && getById.getId() != null) {
						response = new ServiceTypeResponse();
						response.setServicetypeList(listDetails);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					} 
					response = new ServiceTypeResponse();
					response.setStatusCode("422");
					response.setMessage("please provide valid id");
					return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

				}
			}
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
