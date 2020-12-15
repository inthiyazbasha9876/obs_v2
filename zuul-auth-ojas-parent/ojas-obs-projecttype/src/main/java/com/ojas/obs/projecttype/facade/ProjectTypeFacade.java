package com.ojas.obs.projecttype.facade;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.projecttype.request.ProjectTypeRequest;

public interface ProjectTypeFacade {
	public ResponseEntity<Object> saveDetails(ProjectTypeRequest projectTypeRequest)  ;
	public ResponseEntity<Object> getAllDetails(ProjectTypeRequest projectTypeRequest) ;

}
