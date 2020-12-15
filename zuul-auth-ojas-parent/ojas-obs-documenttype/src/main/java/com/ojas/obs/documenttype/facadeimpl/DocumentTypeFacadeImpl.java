package com.ojas.obs.documenttype.facadeimpl;

import java.util.ArrayList;
import java.util.List;

import static com.ojas.obs.documenttype.constants.Constants.SAVE;
import static com.ojas.obs.documenttype.constants.Constants.UPDATE;
import static com.ojas.obs.documenttype.constants.Constants.DELETE;
import static com.ojas.obs.documenttype.constants.Constants.GETBYID;
import static com.ojas.obs.documenttype.constants.Constants.GETALL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.documenttype.facade.DocumentTypeFacade;
import com.ojas.obs.documenttype.model.DocumentType;
import com.ojas.obs.documenttype.repository.DocumentTypeRepository;
import com.ojas.obs.documenttype.request.DocumentTypeRequest;
import com.ojas.obs.documenttype.response.DocumentTypeResponse;

@Service
public class DocumentTypeFacadeImpl implements DocumentTypeFacade {
	@Autowired
	private DocumentTypeRepository documenttypeRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> saveDocumentType(DocumentTypeRequest documenttyperequestobject) {
		DocumentTypeResponse response = null;
		logger.debug("request coming to the facade");

		if (documenttyperequestobject.getTransactionType().equalsIgnoreCase(SAVE)) {
			response = new DocumentTypeResponse();
			List<DocumentType> documenttypeList = documenttyperequestobject.getDocumenttypelist();

			for (DocumentType document : documenttypeList) {
				DocumentType save = documenttypeRepo.save(document);
				if (save.getDocumenttypeId()!= null) {
					logger.debug("save method");
					response.setStatusCode("200");
					response.setMessage("Document Type saved successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} else {
					logger.debug("save method");
					response.setStatusCode("409");
					response.setMessage("failed to save");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}

			}
		}

		if (documenttyperequestobject.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new DocumentTypeResponse();
			List<DocumentType> documenttypeList = documenttyperequestobject.getDocumenttypelist();
			DocumentType doc=documenttypeList.get(0);
			Integer id = doc.getDocumenttypeId();
			DocumentType findById = documenttypeRepo.getOne(id);
			
				if (findById.getDocumenttypeId() != null) {
					documenttypeRepo.save(doc);
					response.setStatusCode("200");
					response.setMessage("Document Type updated Successfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} 
					response.setStatusCode("422");
					response.setMessage("failed to update");
					return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				}
		

		if (documenttyperequestobject.getTransactionType().equalsIgnoreCase(DELETE)) {
			response = new DocumentTypeResponse();
				Integer id = documenttyperequestobject.getDocumenttypelist().get(0).getDocumenttypeId();
				DocumentType type = documenttypeRepo.getOne(id);
				type.setStatus(!type.getStatus());
				DocumentType actionOwner = documenttypeRepo.save(type);

				if (actionOwner.getDocumenttypeId()!= null) {
					response.setStatusCode("200");
					response.setMessage("Document Type deleted Succesfully");
					return new ResponseEntity<>(response, HttpStatus.OK);
				} 
					response.setStatusCode("409");
					response.setMessage("failed to delete");
					return new ResponseEntity<>(response, HttpStatus.CONFLICT);
				}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getDocumentType(DocumentTypeRequest documenttyperequestobject) {

		List<DocumentType> documenttypeList = documenttyperequestobject.getDocumenttypelist();
		logger.debug(" getAll Document Type  details");
		DocumentTypeResponse response = null;
		List<DocumentType> getAll = null;

		if (documenttyperequestobject.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = documenttypeRepo.findAll();
			if (getAll.isEmpty()) {
				response = new DocumentTypeResponse();
				response.setDocumenttypelist(new ArrayList<DocumentType>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			} 
				response = new DocumentTypeResponse();
				response.setDocumenttypelist(getAll);
				response.setMessage("success");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		
		if (documenttyperequestobject.getTransactionType().equalsIgnoreCase(GETBYID)
				&& documenttypeList.get(0).getDocumenttypeId() != null) {

			for (DocumentType document : documenttypeList) {

				if (document.getDocumenttypeId() != null) {
					Integer id = documenttyperequestobject.getDocumenttypelist().get(0).getDocumenttypeId();

					ArrayList<DocumentType> list = new ArrayList<>();

					DocumentType getById = documenttypeRepo.findById(id).orElse(new DocumentType());

					list.add(getById);

					if (getById != null && getById.getDocumenttypeId() != null) {
						response = new DocumentTypeResponse();
						response.setDocumenttypelist(list);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<>(response, HttpStatus.OK);
					} 
						response = new DocumentTypeResponse();
						response.setStatusCode("422");
						response.setMessage("please provide valid id");
						return new ResponseEntity<>(response, HttpStatus.CONFLICT);
					}

				}
			}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
