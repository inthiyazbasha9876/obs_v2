package com.ojas.obs.controller;

import static com.ojas.obs.constants.Constants.DELETE;
import static com.ojas.obs.constants.Constants.GETBYID;
import static com.ojas.obs.constants.Constants.SAVE;
import static com.ojas.obs.constants.Constants.UPDATE;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.facadeimpl.DocumentStageFacadeImpl;
import com.ojas.obs.model.DocumentStage;
import com.ojas.obs.request.DocumentstageRequest;
import com.ojas.obs.response.ErrorResponse;

@RestController
public class DocumentStageController {   
	@Autowired
	private DocumentStageFacadeImpl docfacadeImpl;  
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(value = "/set")
	public ResponseEntity<Object> saveDetails(@RequestBody DocumentstageRequest docRequestObject,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("data coming into controller");

		List<DocumentStage> docstage = docRequestObject.getDoucumentStageList();

	
		try {
			if (docRequestObject.getDoucumentStageList() == null || docstage.isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("reqest object is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for(DocumentStage d:docstage) {
			if (docRequestObject.getTransactionType().equalsIgnoreCase(SAVE)
					&&d.getDocumentstage().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage(" Field is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

			}

		
				if (docRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)
						&& docRequestObject.getDoucumentStageList().get(0).getDocumentstageId() == null
						|| docRequestObject.getTransactionType().equalsIgnoreCase(DELETE)
								&& docRequestObject.getDoucumentStageList().get(0).getDocumentstageId() == null) {
					ErrorResponse error = new ErrorResponse();
					error.setStatusCode("422");
					error.setStatusMessage(" Field is null");
					return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

				}
			}
				return docfacadeImpl.saveDetails(docRequestObject);

			} catch (DuplicateKeyException e) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("409");
				error.setStatusMessage("duplicate key Exception");
				error.setStatusMessage(e.getCause().getLocalizedMessage());
				return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
			} catch (Exception e) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("409");
				error.setStatusMessage(e.getMessage());
				return new ResponseEntity<Object>(error, HttpStatus.CONFLICT);
			}
		}
@PostMapping(value ="/get")
public ResponseEntity<Object> getDetails(@RequestBody DocumentstageRequest docRequestObject,HttpServletRequest servletRequest, HttpServletResponse servletResponse) 
{

   
    try {
        if (docRequestObject == null || docRequestObject.getDoucumentStageList()== null
                || docRequestObject.getDoucumentStageList().isEmpty()
                || docRequestObject.getTransactionType() == null
                || docRequestObject.getTransactionType().isEmpty()) 
        {
            ErrorResponse error = new ErrorResponse();
            error.setStatusCode("422");
            error.setStatusMessage("object is null"); 
            return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        
        if (docRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
                && docRequestObject.getDoucumentStageList().get(0).getDocumentstageId() == null) {
            logger.error("please provide id");
            ErrorResponse error = new ErrorResponse();
            error.setStatusCode("422");
            error.setStatusMessage("Type must be getAll");
            return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return docfacadeImpl.getDetails(docRequestObject);
    } 
    catch (Exception e) {
        logger.debug("inside catch block.***");
        ErrorResponse er = new ErrorResponse();
        er.setStatusCode("409");
        er.setStatusMessage(e.getMessage());
        return new ResponseEntity<>(er, HttpStatus.CONFLICT);
    }



}

}
 

