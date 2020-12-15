package com.ojas.obs.projectDetails.facadeImpl;

import static com.ojas.obs.projectDetails.constants.UserConstants.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.obs.projectDetails.Dao.ProjectDetailsDao;
import com.ojas.obs.projectDetails.facade.ProjectDetailsFacade;
import com.ojas.obs.projectDetails.model.ErrorResponse;
import com.ojas.obs.projectDetails.model.ProjectDetails;
import com.ojas.obs.projectDetails.request.ProjectDetailsRequest;
import com.ojas.obs.projectDetails.response.ProjectDetailsResponse;


@Component
public class ProjectDetailsFacadeImpl implements ProjectDetailsFacade {
	@Autowired
	ProjectDetailsDao projectDetailsDao;

	ProjectDetailsResponse projectDetailsResponse = new ProjectDetailsResponse();
	Logger logger = Logger.getLogger(this.getClass());
@Transactional
	@Override
	public ProjectDetailsResponse setProjectDetails(ProjectDetailsRequest projectDetailsRequestObject)
			throws SQLException {
		logger.debug(
				" Received input data in ProjectDetailsServiceImpl/setProjectDetails :" + projectDetailsRequestObject);
		projectDetailsResponse = new ProjectDetailsResponse();

		if (projectDetailsRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) {

			logger.debug(" Received input data in ProjectDetailsServiceImpl/saveProjectDetails :"
					+ projectDetailsRequestObject);
			projectDetailsResponse.setStatusCode("409");
			projectDetailsResponse.setStatusMessage("sorry ProjectDetails is not saved");
			Integer saveProjectDetails = projectDetailsDao.saveProjectDetails(projectDetailsRequestObject);
			logger.debug("received at service by calling the saveProjectDetails is" + saveProjectDetails);
			if (saveProjectDetails > 0 ) {
				projectDetailsResponse.setStatusCode("200");
				projectDetailsResponse.setStatusMessage("ProjectDetails has saved successfully");
			}
			return projectDetailsResponse;
		}

		if (projectDetailsRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {

			logger.debug(" Received input data in ProjectDetailsServiceImpl/updateProjectDetails :"
					+ projectDetailsRequestObject);

			Integer updateProjectDetails = projectDetailsDao.updateProjectDetails(projectDetailsRequestObject);
			projectDetailsResponse.setStatusCode("409");
			projectDetailsResponse.setStatusMessage("sorry ProjectDetails is not updated");
			logger.debug("received at service by calling the updateProjectDetails is" + updateProjectDetails);
			if (updateProjectDetails>0) {
				projectDetailsResponse.setStatusCode("200");
				projectDetailsResponse.setStatusMessage("ProjectDetails has updated successfully");
			}
			return projectDetailsResponse;
		}

		if (projectDetailsRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {

			logger.debug(" Received input data in ProjectDetailsServiceImpl/deleteProjectDetails :"
					+ projectDetailsRequestObject);

			Integer deleteProjectDetails = projectDetailsDao.deleteProjectDetails(projectDetailsRequestObject);
			projectDetailsResponse.setStatusCode("409");
			projectDetailsResponse.setStatusMessage("sorry ProjectDetails is not delete");
			logger.debug("received at service by calling the deleteProjectDetails is" + deleteProjectDetails);
			if (deleteProjectDetails> 0) {
				projectDetailsResponse.setStatusCode("200");
				projectDetailsResponse.setStatusMessage("ProjectDetails has deactivated successfully");
			}
			return projectDetailsResponse;
		}

		return projectDetailsResponse;
	}


@Override
public ResponseEntity<Object> getProjectDetails(ProjectDetailsRequest projectDetailsRequestObject) {
	logger.debug("@projectDetailsRequestObject in projectDetailsFacadeImpl ::" + projectDetailsRequestObject);
	
	try {
		if (projectDetailsRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
			
			List<ProjectDetails> allProjectDetails = projectDetailsDao.getAll();
			if (allProjectDetails == null || allProjectDetails.isEmpty()) {
				projectDetailsResponse.setProjectDetailsList(new ArrayList<>());
				projectDetailsResponse.setStatusMessage("no records found");
				projectDetailsResponse.setStatusCode("422");
			
				return new ResponseEntity<>(projectDetailsResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			} else {
				projectDetailsResponse.setProjectDetailsList(allProjectDetails);
				projectDetailsResponse.setStatusMessage("success");
				projectDetailsResponse.setStatusCode("200");
				
				return new ResponseEntity<>(projectDetailsResponse, HttpStatus.OK);
			}
		}
		if (projectDetailsRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)) {
			ProjectDetails details = projectDetailsRequestObject.getProjectDetailsList().get(0);
			List<ProjectDetails> allProjectDetails1 = null;
			if(details.getEmployeeId() != null) {
			allProjectDetails1 = projectDetailsDao.getByEmpId(details.getEmployeeId());
			} else {
			allProjectDetails1 = projectDetailsDao.getById(details.getId());
			}
			if (allProjectDetails1 == null || allProjectDetails1.isEmpty()) {
				projectDetailsResponse.setProjectDetailsList(new ArrayList<>());
				projectDetailsResponse.setStatusMessage("no records found");
				projectDetailsResponse.setStatusCode("422");
			
				return new ResponseEntity<>(projectDetailsResponse, HttpStatus.UNPROCESSABLE_ENTITY);
			} else {
				projectDetailsResponse.setProjectDetailsList(allProjectDetails1);
				projectDetailsResponse.setStatusMessage("success");
				projectDetailsResponse.setStatusCode("200");
				
				return new ResponseEntity<>(projectDetailsResponse, HttpStatus.OK);
			}
		}
	} catch (Exception e) {
		ErrorResponse error = new ErrorResponse();
		error.setStatusMessage(e.getMessage());
		error.setStatusCode("409");
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
	return new ResponseEntity<>(projectDetailsResponse, HttpStatus.OK);
}
	
}
