package com.ojas.obs.actiontype.facadeimpl;

import static com.ojas.obs.actiontype.constants.Constants.DELETE;
import static com.ojas.obs.actiontype.constants.Constants.GETALL;
import static com.ojas.obs.actiontype.constants.Constants.GETBYID;
import static com.ojas.obs.actiontype.constants.Constants.SAVE;
import static com.ojas.obs.actiontype.constants.Constants.UPDATE;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.actiontype.facade.ActionTypeFacade;
import com.ojas.obs.actiontype.model.ActionType;
import com.ojas.obs.actiontype.repository.ActionTypeRepository;
import com.ojas.obs.actiontype.request.ActionTypeRequest;
import com.ojas.obs.actiontype.response.ActionTypeResponse;
import com.ojas.obs.actiontype.response.ErrorResponse;

@Service
public class ActionTypeFacadeImpl implements ActionTypeFacade {
	@Autowired
	private ActionTypeRepository actionTypeRepository;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseEntity<Object> saveDetails(ActionTypeRequest actionTypeRequest) {
		List<ActionType> list = actionTypeRequest.getActionTypeList();
		for (ActionType actionType : list) {
			if (actionTypeRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
				ActionType actionType2 = actionTypeRepository.save(actionType);
				if (actionType2.getId() != null) {
					ActionTypeResponse actionTypeResponse = new ActionTypeResponse();
					actionTypeResponse.setMessage("record saved successfully");
					actionTypeResponse.setStatusCode("200");
					return new ResponseEntity<>(actionTypeResponse, HttpStatus.OK);
				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("failed to save record");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
			}
			if (actionTypeRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
				ActionType actionType2 = actionTypeRepository.getOne(actionType.getId());
				actionType2.setStatus(!actionType2.getStatus());
				ActionType actionType3 = actionTypeRepository.save(actionType2);
				if (actionType3.getId() != null) {
					ActionTypeResponse actionTypeResponse = new ActionTypeResponse();
					actionTypeResponse.setMessage("sucessfully deleted");
					actionTypeResponse.setStatusCode("200");
					return new ResponseEntity<>(actionTypeResponse, HttpStatus.OK);

				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("not deleted");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
			}
		}

		if (actionTypeRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {

			ActionType fetchedRecord = actionTypeRepository.getOne(list.get(0).getId());
			fetchedRecord.setActiontype(list.get(0).getActiontype());
			if (fetchedRecord.getId() != null) {
				actionTypeRepository.save(fetchedRecord);
				ActionTypeResponse response = new ActionTypeResponse();
				response.setMessage("updated successfully");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);

			}
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("not updated");
			errorResponse.setStatusCode("409");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
	

	public ResponseEntity<Object> getAllDetails(ActionTypeRequest actionTypeRequest) {
		logger.debug("request coming to the facade");
		List<ActionType> list2 = new ArrayList<>();
		if (actionTypeRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			list2 = actionTypeRepository.findAll();
			if (!list2.isEmpty()) {
				ActionTypeResponse response = new ActionTypeResponse();
				response.setMessage("successfully get all the records");
				response.setStatusCode("200");
				response.setActionTypeList(list2);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("list is empty");
			errorResponse.setStatusCode("409");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		}
		if (actionTypeRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
			ActionType findById = actionTypeRepository.getOne(actionTypeRequest.getActionTypeList().get(0).getId());
			if (findById.getId() != null) {
				list2.add(findById);
				ActionTypeResponse response = new ActionTypeResponse();
				response.setMessage("suucessfully get the single record");
				response.setStatusCode("200");
				response.setActionTypeList(list2);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("record is not getting");
			errorResponse.setStatusCode("409");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
}
