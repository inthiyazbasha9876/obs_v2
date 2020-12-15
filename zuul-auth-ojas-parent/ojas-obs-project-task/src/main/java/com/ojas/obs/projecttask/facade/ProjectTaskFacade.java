package com.ojas.obs.projecttask.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.projecttask.request.ProjectTaskRequest;

@Service
public interface ProjectTaskFacade {
	public ResponseEntity<Object> saveProjectTask(ProjectTaskRequest projecttaskRequestObject);

	public ResponseEntity<Object> getProjectTask(ProjectTaskRequest projecttaskRequestObject);

}
