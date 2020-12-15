package com.ojas.obs.facadeimpl;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.GETALL;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.UPDATE;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.facade.SarStatusFacade;
import com.ojas.obs.model.SarStatus;
import com.ojas.obs.repositories.SarStatusRepository;
import com.ojas.obs.request.SarStatusRequest;
import com.ojas.obs.response.SarStatusResponse;
@Service
public class SarStatusFacadeImpl implements SarStatusFacade {

	@Autowired
	private SarStatusRepository sarStatusRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> saveDetails(SarStatusRequest sarRequestObject) {
	
		SarStatusResponse response=null; 
		
		 logger.debug("request coming to the facade");
		 
		 if (sarRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) {
				response = new SarStatusResponse();
				List<SarStatus> actionOwnerList = sarRequestObject.getSarstatusList();

				for (SarStatus action : actionOwnerList) {
					SarStatus save = sarStatusRepo.save(action);
					if (save.getSarstatusId() != null) {
						logger.debug("save method");
						response.setStatusCode("200");
						response.setMessage("sarStatus details saved successfully");
						return new ResponseEntity<>(response, HttpStatus.OK);
					} else {
						logger.debug("save method");
						response.setStatusCode("409");
						response.setMessage("failed to save");
						return new ResponseEntity<>(response, HttpStatus.CONFLICT);
					}
				}
			}
		
			if (sarRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
				response = new SarStatusResponse();
				List<SarStatus> actionOwnerList = sarRequestObject.getSarstatusList();
				SarStatus action = actionOwnerList.get(0);
				Integer id = action.getSarstatusId();
				SarStatus findById = sarStatusRepo.getOne(id);
				if (findById.getSarstatusId() != null) {
					sarStatusRepo.save(action);
					response.setStatusCode("200");
					response.setMessage("sarStatus Details updated Successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				response.setStatusCode("409");
				response.setMessage("failed to update");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);

			}
			if (sarRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {
				response = new SarStatusResponse();
				Integer id = sarRequestObject.getSarstatusList().get(0).getSarstatusId();
				SarStatus act = sarStatusRepo.getOne(id);
				act.setStatus(!act.getStatus());
				SarStatus actionOwner = sarStatusRepo.save(act);

				if (actionOwner.getSarstatusId() != null) {
					response.setStatusCode("200");
					response.setMessage("SarStatus Details Are deleted Succesfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				response.setStatusCode("409");
				response.setMessage("failed to delete");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);

			}
			return new ResponseEntity<>(response, HttpStatus.OK);

		}

	
		@Override
		public ResponseEntity<Object> getDetails(SarStatusRequest sarRequestObject) {
			List<SarStatus> list = sarRequestObject.getSarstatusList();
			logger.debug(" getAll customer details");
			SarStatusResponse response = null;
			List<SarStatus> getAll = null;

			if (sarRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
				getAll = sarStatusRepo.findAll();
				if (getAll.isEmpty()) {
					response = new SarStatusResponse();
					response.setSarstatusList(new ArrayList<SarStatus>());
					response.setMessage("No records found");
					response.setStatusCode("409");
					return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
				}
				response = new SarStatusResponse();
				response.setSarstatusList(getAll);
				response.setMessage("success");
				response.setStatusCode("200"); 
				return new ResponseEntity<Object>(response, HttpStatus.OK);

			}
			if (sarRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
					&& list.get(0).getSarstatusId() != null) {

				for (SarStatus type : list) {

					if (type.getSarstatusId() != null) {
						Integer id = sarRequestObject.getSarstatusList().get(0).getSarstatusId();

						ArrayList<SarStatus> listDetails = new ArrayList<SarStatus>();

						SarStatus getById = sarStatusRepo.findById(id).orElse(new SarStatus());

						listDetails.add(getById);
 
						if (getById != null && getById.getSarstatusId() != null) {
							response = new SarStatusResponse();
							response.setSarstatusList(listDetails);
							response.setStatusCode("200");
							response.setMessage("success");
							return new ResponseEntity<Object>(response, HttpStatus.OK);
						}
						response = new SarStatusResponse();
						response.setStatusCode("422");
						response.setMessage("please provide valid id");
						return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);

					}
				}
			}
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}

	}
