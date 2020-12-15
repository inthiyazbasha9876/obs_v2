package com.ojas.obs.actiontype.facade;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.actiontype.request.ActionTypeRequest;

public interface ActionTypeFacade {
	public ResponseEntity<Object> saveDetails(ActionTypeRequest actionTypeRequest);

	public ResponseEntity<Object> getAllDetails(ActionTypeRequest actionTypeRequest);

}
