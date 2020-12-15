package com.ojas.obs.projecttask.facadeimpl;

import static com.ojas.obs.projecttask.constants.Constants.DELETE;
import static com.ojas.obs.projecttask.constants.Constants.GETALL;
import static com.ojas.obs.projecttask.constants.Constants.GETBYID;
import static com.ojas.obs.projecttask.constants.Constants.SAVE;
import static com.ojas.obs.projecttask.constants.Constants.UPDATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.projecttask.facade.ProjectTaskFacade;
import com.ojas.obs.projecttask.model.ProjectTask;
import com.ojas.obs.projecttask.repository.ProjectTaskRepository;
import com.ojas.obs.projecttask.request.ProjectTaskRequest;
import com.ojas.obs.projecttask.response.ProjectTaskResponse;

@Service
public class ProjectTaskFacadeImpl implements ProjectTaskFacade {
	@Autowired
	private ProjectTaskRepository projecttaskRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> saveProjectTask(ProjectTaskRequest projecttaskRequestObject) {
		ProjectTaskResponse response = null;

		logger.debug("request coming to the facade");

		if (projecttaskRequestObject.getTransactionType().equalsIgnoreCase(SAVE)) {
			response = new ProjectTaskResponse();

			List<ProjectTask> tasklist = projecttaskRequestObject.getProjecttasklist();

			for (ProjectTask task : tasklist) {
				ProjectTask save = projecttaskRepo.save(task);

				if (save != null) {
					logger.debug("save method");
					response.setStatusCode("200");
					response.setMessage("project task details has saved successfully");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				} else {
					logger.debug("update method");
					response.setStatusCode("422");
					response.setMessage("failed to save");
					return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				}

			}

		}

		if (projecttaskRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
			response = new ProjectTaskResponse();

			for (ProjectTask task : projecttaskRequestObject.getProjecttasklist()) {
				Integer id = projecttaskRequestObject.getProjecttasklist().get(0).getProjecttaskId();
				Optional<ProjectTask> findById = projecttaskRepo.findById(id);
				if (findById != null && findById.get().getProjecttaskId() != null) {
					projecttaskRepo.save(task);
					response.setStatusCode("200");
					response.setMessage("project task has updated successfully");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				} else {

					response.setStatusCode("422");
					response.setMessage("failed to update");
					return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
		}
		if (projecttaskRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) {
			response = new ProjectTaskResponse();

			for (ProjectTask task : projecttaskRequestObject.getProjecttasklist()) {
				Integer id = projecttaskRequestObject.getProjecttasklist().get(0).getProjecttaskId();
				ProjectTask ser = projecttaskRepo.getOne(id);

				ser.setStatus(!ser.getStatus());
				ProjectTask projecttask = projecttaskRepo.save(ser);

				if (projecttask != null) {
					response.setStatusCode("200");
					response.setMessage("project task has deleted successfully");
					return new ResponseEntity<Object>(response, HttpStatus.OK);
				} else {

					response.setStatusCode("422");
					response.setMessage("failed to delete");
					return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
		} else {
			response = new ProjectTaskResponse();
			response.setStatusCode("422");
			response.setMessage("not deleted");
			return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getProjectTask(ProjectTaskRequest projecttaskRequestObject) {
		List<ProjectTask> tasklist = projecttaskRequestObject.getProjecttasklist();
		logger.debug(" getAll project task details");
		ProjectTaskResponse response = null;
		List<ProjectTask> getAll = null;

		if (projecttaskRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = projecttaskRepo.findAll();
			if (getAll == null) {
				response = new ProjectTaskResponse();
				response.setProjecttasklist(new ArrayList<ProjectTask>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<Object>(response, HttpStatus.CONFLICT);
			} else {
				response = new ProjectTaskResponse();
				response.setProjecttasklist(getAll);
				response.setMessage("success");
				response.setStatusCode("200");
				return new ResponseEntity<Object>(response, HttpStatus.OK);
			}
		}
		if (projecttaskRequestObject.getTransactionType().equalsIgnoreCase(GETBYID)
				&& tasklist.get(0).getProjecttaskId() != null) {

			for (ProjectTask task : tasklist) {

				if (task.getProjecttaskId() != null) {
					Integer id = projecttaskRequestObject.getProjecttasklist().get(0).getProjecttaskId();

					ArrayList<ProjectTask> tasklist1 = new ArrayList<ProjectTask>();

					ProjectTask getById = projecttaskRepo.findById(id).orElse(new ProjectTask());

					tasklist1.add(getById);

					if (getById != null && getById.getProjecttaskId() != null) {
						response = new ProjectTaskResponse();
						response.setProjecttasklist(tasklist1);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<Object>(response, HttpStatus.OK);
					} else {
						response = new ProjectTaskResponse();
						response.setStatusCode("422");
						response.setMessage("please provide valid id");
						return new ResponseEntity<Object>(response, HttpStatus.UNPROCESSABLE_ENTITY);
					}

				}
			}
		}
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

}
