package com.ojas.obs.projecttask.controller;

import static com.ojas.obs.projecttask.constants.Constants.DELETE;
import static com.ojas.obs.projecttask.constants.Constants.GETBYID;
import static com.ojas.obs.projecttask.constants.Constants.SAVE;
import static com.ojas.obs.projecttask.constants.Constants.UPDATE;
import static com.ojas.obs.projecttask.constants.Constants.SET;
import static com.ojas.obs.projecttask.constants.Constants.GET;

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

import com.ojas.obs.projecttask.facadeimpl.ProjectTaskFacadeImpl;
import com.ojas.obs.projecttask.model.ProjectTask;
import com.ojas.obs.projecttask.request.ProjectTaskRequest;
import com.ojas.obs.projecttask.response.ErrorResponse;

@RestController
//@RequestMapping("obs/projecttask")
public class ProjectTaskController {
	@Autowired
	private ProjectTaskFacadeImpl projecttaskfacadeImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(value = SET)
	public ResponseEntity<Object> saveProjectTask(@RequestBody ProjectTaskRequest projecttaskRequestObject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		logger.debug("data coming into controller");

		List<ProjectTask> tasklist = projecttaskRequestObject.getProjecttasklist();

		try {
			if ((projecttaskRequestObject == null) || (projecttaskRequestObject.getProjecttasklist() == null)
					|| projecttaskRequestObject.getTransactionType() == null) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("reqest object is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (projecttaskRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) {
				for (ProjectTask task : tasklist) {
					if (task.getProjecttask().isEmpty() || task.getStatus() == null) {
						ErrorResponse error = new ErrorResponse();
						error.setStatusCode("422");
						error.setStatusMessage(" field is null");
						return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					}
				}
			}

			if (projecttaskRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
				for (ProjectTask task : tasklist) {
					if (task.getProjecttask().isEmpty() || task.getStatus() == null) {
						ErrorResponse error = new ErrorResponse();
						error.setStatusCode("422");
						error.setStatusMessage(" field is null");
						return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					}
				}
			}
			if (projecttaskRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {
				for (ProjectTask task : tasklist) {
					if (task.getProjecttask() == null || task.getStatus() == null) {
						ErrorResponse error = new ErrorResponse();
						error.setStatusCode("422");
						error.setStatusMessage(" field is null");
						return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
					}
				}
			}

			return projecttaskfacadeImpl.saveProjectTask(projecttaskRequestObject);

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

	@PostMapping(value = GET)
	public ResponseEntity<Object> getProjectTask(@RequestBody ProjectTaskRequest projecttaskRequestObject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {

		logger.debug("received data into controller" + projecttaskRequestObject);
		try {
			if (projecttaskRequestObject == null || projecttaskRequestObject.getProjecttasklist() == null
					|| projecttaskRequestObject.getProjecttasklist().isEmpty()
					|| projecttaskRequestObject.getTransactionType() == null
					|| projecttaskRequestObject.getTransactionType().isEmpty()) {
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("object is null");
				return new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			if (projecttaskRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
					&& projecttaskRequestObject.getProjecttasklist().get(0).getProjecttaskId() == null) {
				logger.error("please provide id");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setStatusMessage("Project Task must be getAll");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return projecttaskfacadeImpl.getProjectTask(projecttaskRequestObject);
		} catch (Exception e) {
			logger.debug("inside catch block.***");
			ErrorResponse er = new ErrorResponse();
			er.setStatusCode("409");
			er.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(er, HttpStatus.CONFLICT);
		}
	}
}
