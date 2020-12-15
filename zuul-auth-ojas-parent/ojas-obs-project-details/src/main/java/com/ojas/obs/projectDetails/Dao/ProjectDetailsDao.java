package com.ojas.obs.projectDetails.Dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.projectDetails.model.ProjectDetails;
import com.ojas.obs.projectDetails.request.ProjectDetailsRequest;

public interface ProjectDetailsDao {

	Integer saveProjectDetails(ProjectDetailsRequest projectDetailsRequestObject) throws SQLException;

	Integer updateProjectDetails(ProjectDetailsRequest projectDetailsRequestObject) throws SQLException ;

	List<ProjectDetails> getAll() throws SQLException ;

	List<ProjectDetails> getById(Integer id) throws SQLException ;

	Integer deleteProjectDetails(ProjectDetailsRequest projectDetailsRequestObject) throws SQLException ;

	List<ProjectDetails> getByEmpId(String employeeId)throws SQLException;

}
