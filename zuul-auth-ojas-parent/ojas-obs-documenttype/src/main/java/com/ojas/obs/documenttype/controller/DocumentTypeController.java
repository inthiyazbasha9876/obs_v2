package com.ojas.obs.documenttype.controller;

import static com.ojas.obs.documenttype.constants.Constants.DELETE;
import static com.ojas.obs.documenttype.constants.Constants.GET;
import static com.ojas.obs.documenttype.constants.Constants.GETBYID;
import static com.ojas.obs.documenttype.constants.Constants.SAVE;
import static com.ojas.obs.documenttype.constants.Constants.SET;
import static com.ojas.obs.documenttype.constants.Constants.UPDATE;
import static com.ojas.obs.documenttype.constants.Constants.NULLVALUE;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ojas.obs.documenttype.facadeimpl.DocumentTypeFacadeImpl;
import com.ojas.obs.documenttype.model.DocumentType;
import com.ojas.obs.documenttype.request.DocumentTypeRequest;
import com.ojas.obs.documenttype.response.ErrorResponse;

@RestController
//@RequestMapping("obs/documenttype")
public class DocumentTypeController {

	@Autowired
	private DocumentTypeFacadeImpl documenttypeFacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(SET)
	public ResponseEntity<Object> saveDocumentType(@RequestBody DocumentTypeRequest documenttyperequestobject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		logger.debug("data coming into the controller");

		List<DocumentType> documenttypeList = documenttyperequestobject.getDocumenttypelist();

		try {
		
			if (documenttyperequestobject == null || documenttyperequestobject.getDocumenttypelist() == null
					|| documenttyperequestobject.getTransactionType() == null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("reqest object is null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			for (DocumentType document : documenttypeList) {
			if (documenttyperequestobject.getTransactionType().equalsIgnoreCase(SAVE)
                && document.getDocumenttype().isEmpty()) {
						ErrorResponse response = new ErrorResponse();
						response.setStatusCode("422");
						response.setStatusMessage(NULLVALUE);
						return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
					}


			if ((documenttyperequestobject.getTransactionType().equalsIgnoreCase(UPDATE)
					||documenttyperequestobject.getTransactionType().equalsIgnoreCase(DELETE))
					&& document.getDocumenttypeId()==null) {
						ErrorResponse response = new ErrorResponse();
						response.setStatusCode("422");
						response.setStatusMessage(NULLVALUE);
						return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
					}
				}
			
			return documenttypeFacadeImpl.saveDocumentType(documenttyperequestobject);

		} catch (DuplicateKeyException e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage("duplicate key Exception");
			error.setStatusMessage(e.getCause().getLocalizedMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}

	@PostMapping(GET)
	public ResponseEntity<Object> getDocumenttype(@RequestBody DocumentTypeRequest documenttyperequestobject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

		logger.debug("received data into controller" );
		try {
			if (documenttyperequestobject == null || documenttyperequestobject.getDocumenttypelist() == null
					|| documenttyperequestobject.getDocumenttypelist().isEmpty()
					|| documenttyperequestobject.getTransactionType() == null
					|| documenttyperequestobject.getTransactionType().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("object is null");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			 if (documenttyperequestobject.getTransactionType().equalsIgnoreCase(GETBYID)
					&& documenttyperequestobject.getDocumenttypelist().get(0).getDocumenttypeId() == null) {
				logger.error("please provide id");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("document type must be getAll");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return documenttypeFacadeImpl.getDocumentType(documenttyperequestobject);
		} catch (Exception e) {
			logger.debug("inside catch block.***");
			ErrorResponse er = new ErrorResponse();
			er.setStatusCode("409");
			er.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(er, HttpStatus.CONFLICT);
		}

	}
}
