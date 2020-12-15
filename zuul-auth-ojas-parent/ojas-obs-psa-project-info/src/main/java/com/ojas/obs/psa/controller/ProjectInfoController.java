package com.ojas.obs.psa.controller;

import static com.ojas.obs.psa.constants.UrlConstants.GET;
import static com.ojas.obs.psa.constants.UrlConstants.SET;
import static com.ojas.obs.psa.constants.UtilConstants.DELETE;
import static com.ojas.obs.psa.constants.UtilConstants.EMPTY;
import static com.ojas.obs.psa.constants.UtilConstants.GETBYCONTID;
import static com.ojas.obs.psa.constants.UtilConstants.GETBYCUSTID;
import static com.ojas.obs.psa.constants.UtilConstants.GETBYPROID;
import static com.ojas.obs.psa.constants.UtilConstants.SAVE;
import static com.ojas.obs.psa.constants.UtilConstants.STATUSUPDATE;
import static com.ojas.obs.psa.constants.UtilConstants.UPDATE;
import static com.ojas.obs.psa.constants.UtilConstants.ADDMILESTONE;
import static com.ojas.obs.psa.constants.UtilConstants.UPDATEMILESTONE;
import static com.ojas.obs.psa.constants.UtilConstants.VALIDATEPROJECTNAME;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ojas.obs.psa.facade.ProjectInfoFacade;
import com.ojas.obs.psa.model.ProjectInfo;
import com.ojas.obs.psa.request.ProjectInfoRequest;
import com.ojas.obs.psa.response.ErrorResponse;

@Controller
//@RequestMapping("/obs/projectInfo")
public class ProjectInfoController {
	@Autowired
	private ProjectInfoFacade infoFacade;
	Logger logger = Logger.getLogger(ProjectInfoController.class);

	@PostMapping(SET)
	public ResponseEntity<Object> setProjectInfo(@RequestBody ProjectInfoRequest infoRequest) {
		logger.debug("Incoming request : " + infoRequest);
		if (infoRequest == null || infoRequest.getTransactionType() == null
				|| infoRequest.getTransactionType().isEmpty()) {
			logger.error("Invalid request");
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage("Invalid Request");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			if (infoRequest.getProjectInfo() == null && ((!infoRequest.getTransactionType().equalsIgnoreCase(DELETE)
					|| !infoRequest.getTransactionType().equalsIgnoreCase(STATUSUPDATE))
					&& infoRequest.getRateCard() == null || infoRequest.getResourceMap() == null)) {
				logger.error("Data must not be null");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage("Data must not be empty");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if ((infoRequest.getTransactionType().equalsIgnoreCase(SAVE)
					|| infoRequest.getTransactionType().equalsIgnoreCase(UPDATE))) {
				String msg = validateProject(infoRequest);
				if (msg != null) {
					logger.error(msg);
					ErrorResponse error = new ErrorResponse();
					error.setMessage(msg);
					error.setStatusCode("422");
					return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
				}

			}
			if ((infoRequest.getTransactionType().equalsIgnoreCase(DELETE)
					|| infoRequest.getTransactionType().equalsIgnoreCase(STATUSUPDATE))
					&& (infoRequest.getProjectInfo().getProjectId() == null
							|| infoRequest.getProjectInfo().getUpdatedBy() == null)) {
				logger.error("Data must not be null");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage("Data must not be empty");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (((infoRequest.getTransactionType().equalsIgnoreCase(ADDMILESTONE)
					&& infoRequest.getProjectInfo().getProjectId() == null)
					|| (infoRequest.getTransactionType().equalsIgnoreCase(UPDATEMILESTONE))
							&& infoRequest.getMilestones().get(0).getMilestoneId() == null)
					&& (infoRequest.getMilestones() == null || infoRequest.getMilestones().isEmpty())) {
				logger.error("Data must not be null");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage("Data must not be empty");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return infoFacade.setProjectInfo(infoRequest);
		} catch (Exception e) {
			logger.error("Exception caught : " + e.getMessage());
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setMessage("Exception caught!");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}

	@PostMapping(GET)
	public ResponseEntity<Object> getProjectInfo(@RequestBody ProjectInfoRequest infoRequest) {
		logger.debug("Incoming request : " + infoRequest);
		if (infoRequest == null || infoRequest.getTransactionType() == null
				|| infoRequest.getTransactionType().isEmpty()) {
			logger.error("Invalid request");
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("422");
			error.setMessage("Invalid Request");
			return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		try {
			ProjectInfo project = infoRequest.getProjectInfo();
			if (project == null) {
				logger.error("Project info must not be empty");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage("Project info must not be empty");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if ((infoRequest.getTransactionType().equalsIgnoreCase(GETBYPROID) && project.getProjectId() == null)
					|| (infoRequest.getTransactionType().equalsIgnoreCase(GETBYCUSTID)
							&& project.getCustomerId() == null)
					|| (infoRequest.getTransactionType().equalsIgnoreCase(GETBYCONTID)
							&& project.getContractId() == null)) {
				logger.error("Id must not be null");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage("Id must not be empty");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			if (infoRequest.getTransactionType().equalsIgnoreCase(VALIDATEPROJECTNAME)
					&& (null == project.getProjectName() || project.getProjectName().isEmpty())) {
				logger.error("Project name must not be null");
				ErrorResponse error = new ErrorResponse();
				error.setStatusCode("422");
				error.setMessage("Project name must not be empty");
				return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return infoFacade.getProjectInfo(infoRequest);
		} catch (Exception e) {
			logger.error("Exception caught : " + e.getMessage());
			ErrorResponse error = new ErrorResponse();
			error.setStatusCode("409");
			error.setMessage("Exception caught!");
			error.setStatusMessage(e.getMessage());
			return new ResponseEntity<>(error, HttpStatus.CONFLICT);
		}
	}

	private String validateProject(ProjectInfoRequest request) throws IllegalAccessException {
		String msg = null;
		for (Field f : request.getProjectInfo().getClass().getDeclaredFields()) {
			f.setAccessible(true);
			if ((request.getTransactionType().equalsIgnoreCase(UPDATE) && f.getName().equalsIgnoreCase("projectId"))
					|| f.getName().equalsIgnoreCase("projectName") || f.getName().equalsIgnoreCase("customerId")
					|| f.getName().equalsIgnoreCase("contractId")) {
				Object pro = f.get(request.getProjectInfo());
				if (pro == null || pro.toString().trim().isEmpty()) {
					msg = f.getName() + EMPTY;
				}
			}
		}
		return msg;
	}

}
