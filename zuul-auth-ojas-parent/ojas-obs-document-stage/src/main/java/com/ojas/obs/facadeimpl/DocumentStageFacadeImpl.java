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

import com.ojas.obs.model.DocumentStage;
import com.ojas.obs.repositories.DocumentStageRepository;
import com.ojas.obs.request.DocumentstageRequest;
import com.ojas.obs.response.DocumentStageResponse;

@Service
public class DocumentStageFacadeImpl {

	@Autowired
	private DocumentStageRepository documentStageRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseEntity<Object> saveDetails(DocumentstageRequest docRequestObject)  {

		DocumentStageResponse response = null;

		logger.debug("request coming to the facade");

		if (docRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) {
			response = new DocumentStageResponse();

			List<DocumentStage> details = docRequestObject.getDoucumentStageList();
			List<DocumentStage> save = documentStageRepo.saveAll(details);
				if (!save.isEmpty()) {
					logger.debug("save method");
					response.setStatusCode("200");
					response.setMessage("document stage details has saved successfully");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				} 
					logger.debug("update method");
					response.setStatusCode("422");
					response.setMessage("failed to save");
					return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);				
			}

		if (docRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new DocumentStageResponse();
			for (DocumentStage type : docRequestObject.getDoucumentStageList()) {
				Integer id = docRequestObject.getDoucumentStageList().get(0).getDocumentstageId();
				Optional<DocumentStage> findById = documentStageRepo.findById(id);
				if (findById.isPresent() && findById.get().getDocumentstageId() != null){
					documentStageRepo.save(type);
					response.setStatusCode("200");
					response.setMessage("document stage details has updated successfully");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				} else {
					response.setStatusCode("422");
					response.setMessage("failed to update");
					return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
		}

		if (docRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {
			response = new DocumentStageResponse();

				Integer id = docRequestObject.getDoucumentStageList().get(0).getDocumentstageId();
				DocumentStage ser = documentStageRepo.getOne(id);

				ser.setStatus(!ser.getStatus());
				DocumentStage servicedata = documentStageRepo.save(ser);
				if (servicedata.getDocumentstageId()!=null) {
					response.setStatusCode("200");
					response.setMessage("document stage deatils has deleted successfully");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				} 

					response.setStatusCode("422");
					response.setMessage("failed to delete");
					return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);			
			}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	public ResponseEntity<Object> getDetails(DocumentstageRequest docRequestObject) {
		List<DocumentStage> list = docRequestObject.getDoucumentStageList();
		logger.debug(" getAll customer details");
		DocumentStageResponse response = null;
		List<DocumentStage> getAll = null;

		if (docRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = documentStageRepo.findAll();
			if (getAll.isEmpty()) {
				response = new DocumentStageResponse();
				response.setDoucumentStageList(new ArrayList<DocumentStage>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
			} 
				response = new DocumentStageResponse();
				response.setDoucumentStageList(getAll);
				response.setMessage("success");
				response.setStatusCode("200");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		if (docRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
				&& list.get(0).getDocumentstageId() != null) {

			for (DocumentStage type : list) {

				if (type.getDocumentstageId() != null) {
					Integer id = docRequestObject.getDoucumentStageList().get(0).getDocumentstageId();

					ArrayList<DocumentStage> listDetails = new ArrayList<DocumentStage>();

					DocumentStage getById = documentStageRepo.findById(id).orElse(new DocumentStage());

					listDetails.add(getById);

					if (getById != null && getById.getDocumentstageId() != null) {
						response = new DocumentStageResponse();
						response.setDoucumentStageList(listDetails);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					} 
						response = new DocumentStageResponse();
						response.setStatusCode("422");
						response.setMessage("please provide valid id");  
						return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
