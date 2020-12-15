package com.ojas.obs.projectDetails.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.projectDetails.request.ProjectDetailsRequest;
import com.ojas.obs.projectDetails.response.ProjectDetailsResponse;

public interface ProjectDetailsFacade {

	ProjectDetailsResponse setProjectDetails(ProjectDetailsRequest projectDetailsRequestObject) throws SQLException;

	ResponseEntity<Object> getProjectDetails(ProjectDetailsRequest projectDetailsRequestObject) throws SQLException;

}
