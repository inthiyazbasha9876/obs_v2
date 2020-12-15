package com.ojas.obs.projecttechstack.controller;

import static com.ojas.obs.projecttechstack.constant.Constant.DELETE;
import static com.ojas.obs.projecttechstack.constant.Constant.GET;
import static com.ojas.obs.projecttechstack.constant.Constant.GETBYID;
//import static com.ojas.obs.projecttechstack.constant.Constant.PROJECTTECHSTACKOBJECTFIELDNULL;
import static com.ojas.obs.projecttechstack.constant.Constant.REQUESTOBJECTNULL;
import static com.ojas.obs.projecttechstack.constant.Constant.SET;
import static com.ojas.obs.projecttechstack.constant.Constant.UPDATE;

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

import com.ojas.obs.projecttechstack.facade.ProjectTechStackFacade;
import com.ojas.obs.projecttechstack.model.ProjectTechStack;
import com.ojas.obs.projecttechstack.request.ProjectTechStackRequest;
import com.ojas.obs.projecttechstack.response.ErrorResponse;

@RestController
//@RequestMapping("obs/projecttechstack")
public class ProjectTechStackController {

	@Autowired
	private ProjectTechStackFacade projectTechStackFacade;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PostMapping(value = SET)
	public ResponseEntity<Object> projectTechStack(@RequestBody ProjectTechStackRequest projectTechStackRequestObject,
			HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		logger.debug("data coming into controller");

		List<ProjectTechStack> projectTechStack = projectTechStackRequestObject.getProjectTechStackList();

		if (projectTechStackRequestObject.getProjectTechStackList().isEmpty()
				|| projectTechStackRequestObject.getProjectTechStackList() == null) {

			ErrorResponse error = new ErrorResponse();
			error.setCode("422");
			error.setStatusMessage(REQUESTOBJECTNULL);
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			for (ProjectTechStack tech : projectTechStack) {
				if (tech == null) {
					ErrorResponse error = new ErrorResponse();
					error.setCode("422");
					error.setStatusMessage(" field is null");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

				}

				if (projectTechStackRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)
						&& projectTechStackRequestObject.getProjectTechStackList().get(0).getId() == null
						|| projectTechStackRequestObject.getTransactionType().equalsIgnoreCase(DELETE)
								&& projectTechStackRequestObject.getProjectTechStackList().get(0).getId() == null) {
					ErrorResponse error = new ErrorResponse();
					error.setCode("422");
					error.setStatusMessage(" field is null");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);

				}
			}

			return projectTechStackFacade.saveProjectTechStack(projectTechStackRequestObject);

		} catch (DuplicateKeyException e) {
			ErrorResponse error = new ErrorResponse();
			error.setCode("409");
			error.setStatusMessage("duplicate key Exception");
			error.setStatusMessage(e.getCause().getLocalizedMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.setCode("409");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}

	@PostMapping(value = GET)
	public ResponseEntity<Object> getProjectTechStack(
			@RequestBody ProjectTechStackRequest projectTechStackRequestObject, HttpServletRequest servletRequest,
			HttpServletResponse servletResponse) {

		logger.debug("received data into controller", projectTechStackRequestObject);

		try {
			if (projectTechStackRequestObject == null || projectTechStackRequestObject.getProjectTechStackList() == null
					|| projectTechStackRequestObject.getProjectTechStackList().isEmpty()
					|| projectTechStackRequestObject.getTransactionType() == null
					|| projectTechStackRequestObject.getTransactionType().isEmpty()) {

				ErrorResponse error = new ErrorResponse();
				error.setCode("422");
				error.setStatusMessage("REQUESTOBJECTNULL");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (projectTechStackRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
					&& projectTechStackRequestObject.getProjectTechStackList().get(0).getId() == null) {
				logger.error("please provide id");
				ErrorResponse error = new ErrorResponse();
				error.setCode("422");
				error.setStatusMessage("Type must be getAll");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return projectTechStackFacade.getProjectTechStack(projectTechStackRequestObject);
		} catch (Exception e) {
			logger.debug("inside catch block.***");
			ErrorResponse error = new ErrorResponse();
			error.setCode("409");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}

	}
}
