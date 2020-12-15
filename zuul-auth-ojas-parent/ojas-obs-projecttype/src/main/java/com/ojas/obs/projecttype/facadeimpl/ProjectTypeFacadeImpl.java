package com.ojas.obs.projecttype.facadeimpl;

import static com.ojas.obs.projecttype.constants.Constants.DELETE;
import static com.ojas.obs.projecttype.constants.Constants.GETALL;
import static com.ojas.obs.projecttype.constants.Constants.GETBYID;
import static com.ojas.obs.projecttype.constants.Constants.SAVE;
import static com.ojas.obs.projecttype.constants.Constants.UPDATE;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.projecttype.facade.ProjectTypeFacade;
import com.ojas.obs.projecttype.model.ProjectType;
import com.ojas.obs.projecttype.repositories.ProjectTypeRepository;
import com.ojas.obs.projecttype.request.ProjectTypeRequest;
import com.ojas.obs.projecttype.response.ErrorResponse;
import com.ojas.obs.projecttype.response.ProjectTypeResponse;

@Service
public class ProjectTypeFacadeImpl implements ProjectTypeFacade {
	@Autowired
	private ProjectTypeRepository projectTypeRepository;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ResponseEntity<Object> saveDetails(ProjectTypeRequest request)  {
		List<ProjectType> list = request.getProjectTypeList();
		for (ProjectType projectType : list) {
			if (request.getTransactionType().equalsIgnoreCase(SAVE)) {
				ProjectType sez2 = projectTypeRepository.save(projectType);
				if (sez2.getId() != null) {
					ProjectTypeResponse sezResponse = new ProjectTypeResponse();
					sezResponse.setMessage("record saved successfully");
					sezResponse.setStatusCode("200");
					return new ResponseEntity<>(sezResponse, HttpStatus.OK);
				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("failed to save record");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
			}
			if (request.getTransactionType().equalsIgnoreCase(DELETE)) {
				ProjectType projectType2 = projectTypeRepository.getOne(projectType.getId());
				projectType2.setStatus(!projectType2.getStatus());
				ProjectType sez3 = projectTypeRepository.save(projectType2);
				if (sez3.getId() != null) {
					ProjectTypeResponse sezResponse = new ProjectTypeResponse();
					sezResponse.setMessage("sucessfully deleted");
					sezResponse.setStatusCode("200");
					return new ResponseEntity<>(sezResponse, HttpStatus.OK);
				}
				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setMessage("not deleted");
				errorResponse.setStatusCode("409");
				return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
			}
		}

		if (request.getTransactionType().equalsIgnoreCase(UPDATE)) {

			ProjectType fetchedRecord = projectTypeRepository.getOne(list.get(0).getId());
			if (fetchedRecord.getId() != null) {
				projectTypeRepository.save(list.get(0));
				ProjectTypeResponse response = new ProjectTypeResponse();
				response.setMessage("updated successfully");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("not updated");
			errorResponse.setStatusCode("409");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	public ResponseEntity<Object> getAllDetails(ProjectTypeRequest projectTypeRequest)  {
		logger.debug("request coming to the facade");
		List<ProjectType> list2 = new ArrayList<>();
		if (projectTypeRequest.getTransactionType().equalsIgnoreCase(GETALL)) {
			list2 = projectTypeRepository.findAll();
			if (!list2.isEmpty()) {
				ProjectTypeResponse response = new ProjectTypeResponse();
				response.setMessage("successfully get all the records");
				response.setStatusCode("200");
				response.setProjectTypeList(list2);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("list is empty");
			errorResponse.setStatusCode("409");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
		}
		if (projectTypeRequest.getTransactionType().equalsIgnoreCase(GETBYID)) {
			ProjectType findById = projectTypeRepository.getOne(projectTypeRequest.getProjectTypeList().get(0).getId());
			if (findById.getId() != null) {
				list2.add(findById);
				ProjectTypeResponse response = new ProjectTypeResponse();
				response.setMessage("suucessfully get the single record");
				response.setStatusCode("200");
				response.setProjectTypeList(list2);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("record is not getting");
			errorResponse.setStatusCode("409");
			return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
}
