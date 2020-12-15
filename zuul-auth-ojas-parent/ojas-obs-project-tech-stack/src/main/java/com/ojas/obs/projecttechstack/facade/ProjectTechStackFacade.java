package com.ojas.obs.projecttechstack.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.projecttechstack.request.ProjectTechStackRequest;

@Service
public interface ProjectTechStackFacade {
	public ResponseEntity<Object>saveProjectTechStack(ProjectTechStackRequest projectTechStackRequestObject);
	 public ResponseEntity<Object> getProjectTechStack(ProjectTechStackRequest projectTechStackRequestObject);
}
