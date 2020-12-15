package com.ojas.obs.permstatus.facadeimpl;

import static com.ojas.obs.permstatus.constants.Constants.DELETE;
import static com.ojas.obs.permstatus.constants.Constants.GETALL;
import static com.ojas.obs.permstatus.constants.Constants.GETBYID;
import static com.ojas.obs.permstatus.constants.Constants.SAVE;
import static com.ojas.obs.permstatus.constants.Constants.UPDATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.permstatus.facade.PermStatusFacade;
import com.ojas.obs.permstatus.model.PermStatus;
import com.ojas.obs.permstatus.repository.PermStatusRepository;
import com.ojas.obs.permstatus.request.PermStatusRequest;
import com.ojas.obs.permstatus.response.PermStatusResponse;

@Service
public class PermStatusFacadeImpl implements PermStatusFacade {
	@Autowired
	private PermStatusRepository permStatusRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> savePermStatus(PermStatusRequest permStatusRequestObject) {
		PermStatusResponse response = null;

		logger.debug("request coming to the facade");

		if (permStatusRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) {
			response = new PermStatusResponse();

			List<PermStatus> permStatusList = permStatusRequestObject.getPermStatusList();

			for (PermStatus perm : permStatusList) {
				PermStatus save = permStatusRepo.save(perm);

				if (save.getPermstatusId() != null) {
					logger.debug("save method");
					response.setStatusCode("200");
					response.setMessage("PermStatus saved successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					logger.debug("save method");
					response.setStatusCode("409");
					response.setMessage("failed to save");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}

			}

		}
		if (permStatusRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new PermStatusResponse();
			List<PermStatus> permStatusList = permStatusRequestObject.getPermStatusList();
			
			PermStatus status = permStatusList.get(0);
			Integer id = status.getPermstatusId();
			Optional<PermStatus> findById = permStatusRepo.findById(id);
				if ( findById.get().getPermstatusId() != null) {
					permStatusRepo.save(status);
					response.setStatusCode("200");
					response.setMessage("PermStatus updated successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} 

					response.setStatusCode("409");
					response.setMessage("failed to update");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}
			
		if (permStatusRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {
			response = new PermStatusResponse();
				Integer id = permStatusRequestObject.getPermStatusList().get(0).getPermstatusId();
				PermStatus status = permStatusRepo.getOne(id);

				status.setStatus(!status.getStatus());
				PermStatus data = permStatusRepo.save(status);

				if (data.getPermstatusId()!= null) {
					response.setStatusCode("200");
					response.setMessage("PermStatus deleted successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}

					response.setStatusCode("409");
					response.setMessage("failed to delete");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

	@Override
	public ResponseEntity<Object> getPermStatus(PermStatusRequest permStatusRequestObject) {
		List<PermStatus> list = permStatusRequestObject.getPermStatusList();
		logger.debug(" getAll perm status");
		PermStatusResponse response = null;
		List<PermStatus> getAll = null;

		if (permStatusRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = permStatusRepo.findAll();
			if (getAll.isEmpty()) {
				response = new PermStatusResponse();
				response.setPermStatusList(new ArrayList<PermStatus>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			} 
				response = new PermStatusResponse();
				response.setPermStatusList(getAll);
				response.setMessage("success");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		if (permStatusRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
				&& list.get(0).getPermstatusId() != null) {

			for (PermStatus details : list) {

				if (details.getPermstatusId() != null) {
					Integer id = permStatusRequestObject.getPermStatusList().get(0).getPermstatusId();

					ArrayList<PermStatus> permStatusList = new ArrayList<>();

					PermStatus getById = permStatusRepo.findById(id).orElse(new PermStatus());

					permStatusList.add(getById);

					if (getById != null && getById.getPermstatusId() != null) {
						response = new PermStatusResponse();
						response.setPermStatusList(permStatusList);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<>(response, HttpStatus.OK);
					} 
						response = new PermStatusResponse();
						response.setStatusCode("409");
						response.setMessage("please provide valid id");
						return new ResponseEntity<>(response, HttpStatus.CONFLICT);
					}

				}
			}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
