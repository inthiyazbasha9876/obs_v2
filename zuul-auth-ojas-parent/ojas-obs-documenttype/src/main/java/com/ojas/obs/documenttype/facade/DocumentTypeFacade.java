package com.ojas.obs.documenttype.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.documenttype.request.DocumentTypeRequest;

@Service
public interface DocumentTypeFacade {
	public ResponseEntity<Object> saveDocumentType(DocumentTypeRequest documenttyperequestobject);

	public ResponseEntity<Object> getDocumentType(DocumentTypeRequest documenttyperequestobject);

}
