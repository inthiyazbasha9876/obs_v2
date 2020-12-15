package com.ojas.obs.actionowner.facadeImpl;

import static com.ojas.obs.actionowner.constants.Constants.SAVE;
import static com.ojas.obs.actionowner.constants.Constants.UPDATE;
import static com.ojas.obs.actionowner.constants.Constants.DELETE;
import static com.ojas.obs.actionowner.constants.Constants.GETALL;
import static com.ojas.obs.actionowner.constants.Constants.GETBYID;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.actionowner.facade.ActionOwnerFacade;
import com.ojas.obs.actionowner.model.ActionOwner;
import com.ojas.obs.actionowner.repository.ActionOwnerRepository;
import com.ojas.obs.actionowner.request.ActionOwnerRequest;
import com.ojas.obs.actionowner.response.ActionOwnerResponse;

@Service
public class ActionOwnerFacadeImpl implements ActionOwnerFacade {

	@Autowired
	private ActionOwnerRepository actionOwnerRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> saveActionOwner(ActionOwnerRequest actionOwnerObjectRequest) {
		ActionOwnerResponse response = null;
		logger.debug("request coming to the facade");

		if (actionOwnerObjectRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
			response = new ActionOwnerResponse();
			List<ActionOwner> actionOwnerList = actionOwnerObjectRequest.getActionOwnerList();

			for (ActionOwner action : actionOwnerList) {
				ActionOwner save = actionOwnerRepo.save(action);
				if (save.getActionownerId() != null) {
					logger.debug("save method");
					response.setStatusCode("200");
					response.setMessage("action owner details saved successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				logger.debug("save method");
				response.setStatusCode("422");
				response.setMessage("failed to save");
				return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		}
		if (actionOwnerObjectRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new ActionOwnerResponse();
			List<ActionOwner> actionOwnerList = actionOwnerObjectRequest.getActionOwnerList();
			for (ActionOwner action : actionOwnerList) {
				Integer id = actionOwnerObjectRequest.getActionOwnerList().get(0).getActionownerId();
				Optional<ActionOwner> findById = actionOwnerRepo.findById(id);
				if (findById.isPresent()&&findById.get().getActionownerId() != null) {
					actionOwnerRepo.save(action);
					response.setStatusCode("200");
					response.setMessage("Action Owner Details updated Successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
					response.setStatusCode("422");
					response.setMessage("failed to update");
					return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				
			}
		}
		if (actionOwnerObjectRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
			response = new ActionOwnerResponse();
				Integer id = actionOwnerObjectRequest.getActionOwnerList().get(0).getActionownerId();
				ActionOwner act = actionOwnerRepo.getOne(id);
				act.setStatus(!act.getStatus());
				ActionOwner actionOwner = actionOwnerRepo.save(act);

				if (actionOwner.getActionownerId() != null) {
					response.setStatusCode("200");
					response.setMessage("action owner details are deleted Succesfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
					response.setStatusCode("422");
					response.setMessage("failed to delete");
					return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getActionOwner(ActionOwnerRequest actionOwnerObjectRequest) {
		List<ActionOwner> actionOwnerlist = actionOwnerObjectRequest.getActionOwnerList();
		logger.debug(" getAll action owner details");
		ActionOwnerResponse response = null;
		List<ActionOwner> getAll = null;

		if (actionOwnerObjectRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = actionOwnerRepo.findAll();
			if (getAll.isEmpty()) {
				response = new ActionOwnerResponse();
				response.setActionOwnerList(new ArrayList<ActionOwner>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
				response = new ActionOwnerResponse();
				response.setActionOwnerList(getAll);
				response.setMessage("success");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			
		}
		if (actionOwnerObjectRequest.getTransactionType().equalsIgnoreCase(GETBYID)
				&& actionOwnerlist.get(0).getActionownerId() != null) {

			for (ActionOwner action : actionOwnerlist) {

				if (action.getActionownerId() != null) {
					Integer id = actionOwnerObjectRequest.getActionOwnerList().get(0).getActionownerId();

					ArrayList<ActionOwner> list = new ArrayList<ActionOwner>();

					ActionOwner getById = actionOwnerRepo.findById(id).orElse(new ActionOwner());

					list.add(getById);

					if (getById != null && getById.getActionownerId() != null) {
						response = new ActionOwnerResponse();
						response.setActionOwnerList(list);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<>(response, HttpStatus.OK);
					} 
						response = new ActionOwnerResponse();
						response.setStatusCode("422");
						response.setMessage("please provide valid id");
						return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
