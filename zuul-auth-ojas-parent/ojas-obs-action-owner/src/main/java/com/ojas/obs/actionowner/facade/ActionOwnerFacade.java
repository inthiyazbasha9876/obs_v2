package com.ojas.obs.actionowner.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.actionowner.request.ActionOwnerRequest;

@Service
public interface ActionOwnerFacade {
	public ResponseEntity<Object>saveActionOwner(ActionOwnerRequest actionOwnerObjectRequest);
	public ResponseEntity<Object>getActionOwner(ActionOwnerRequest actionOwnerObjectRequest);

}
