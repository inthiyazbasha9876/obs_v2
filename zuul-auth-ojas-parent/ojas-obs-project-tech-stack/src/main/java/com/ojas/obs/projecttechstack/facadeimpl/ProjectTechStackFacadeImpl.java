package com.ojas.obs.projecttechstack.facadeimpl;

import static com.ojas.obs.projecttechstack.constant.Constant.DELETE;
import static com.ojas.obs.projecttechstack.constant.Constant.GETALL;
import static com.ojas.obs.projecttechstack.constant.Constant.GETBYID;
import static com.ojas.obs.projecttechstack.constant.Constant.SAVE;
import static com.ojas.obs.projecttechstack.constant.Constant.UPDATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.projecttechstack.facade.ProjectTechStackFacade;
import com.ojas.obs.projecttechstack.model.ProjectTechStack;
import com.ojas.obs.projecttechstack.repository.ProjectTechStackRepository;
import com.ojas.obs.projecttechstack.request.ProjectTechStackRequest;
import com.ojas.obs.projecttechstack.response.ProjectTechStackResponse;

@Service
public class ProjectTechStackFacadeImpl implements ProjectTechStackFacade 
{
	@Autowired
	private ProjectTechStackRepository projectTechStackRepo;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<Object> saveProjectTechStack(ProjectTechStackRequest projectTechStackRequestObject) 
	{
		
		ProjectTechStackResponse response=null;
		
		 logger.debug("request coming to the facade");
		 
		 if (projectTechStackRequestObject.getTransactionType().equalsIgnoreCase(SAVE))
		 {
			response = new ProjectTechStackResponse();
				
			List<ProjectTechStack> projectTechStack = projectTechStackRequestObject.getProjectTechStackList();
				

					List<ProjectTechStack> save = projectTechStackRepo.saveAll(projectTechStack);
					
					if (!save.isEmpty())
					{
						logger.debug("save method");
						response.setStatusCode("200");
						response.setMessage("Project Tech Stack details has saved successfully");
						return new ResponseEntity<>(response, HttpStatus.OK);
					}
					
				response.setStatusCode("409");
				response.setMessage("failed to save");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		 }
		
		 
		 if (projectTechStackRequestObject.getTransactionType().equalsIgnoreCase(UPDATE)) {
				response = new ProjectTechStackResponse();
				
				for (ProjectTechStack tech : projectTechStackRequestObject.getProjectTechStackList())
				{
					Integer id = projectTechStackRequestObject.getProjectTechStackList().get(0).getId();
					Optional<ProjectTechStack> findById = projectTechStackRepo.findById(id);
					if (findById.isPresent() && findById.get().getId() != null) 
					{
						projectTechStackRepo.save(tech);
						response.setStatusCode("200");
						response.setMessage("service details has updated successfully");
						return new ResponseEntity<>(response, HttpStatus.OK);
					} 
					else {
						logger.debug("update method");
						response.setStatusCode("409");
						response.setMessage("failed to update");
						return new ResponseEntity<>(response, HttpStatus.CONFLICT);
					}
				}
			}
					
		 if (projectTechStackRequestObject.getTransactionType().equalsIgnoreCase(DELETE)) 
		 {
				response = new ProjectTechStackResponse();
				
				
					Integer id = projectTechStackRequestObject.getProjectTechStackList().get(0).getId();
					ProjectTechStack ser = projectTechStackRepo.getOne(id);
		                
					ser.setStatus(!ser.getStatus());
					ProjectTechStack locationdata = projectTechStackRepo.save(ser);
					 
					if (locationdata.getId() != null) 
					{
						response.setStatusCode("200");
						response.setMessage("project tech stack has deleted successfully");
						return new ResponseEntity<>(response, HttpStatus.OK);
					} 
						response.setStatusCode("422");
						response.setMessage("failed to delete");
						return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
			}
		 return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Object> getProjectTechStack(ProjectTechStackRequest projectTechStackRequestObject) {
		
		List<ProjectTechStack> list = projectTechStackRequestObject.getProjectTechStackList();
		
		logger.debug(" getAll ProjectTechStack");
		ProjectTechStackResponse response = null;
		
		List<ProjectTechStack> getAll = null;
		
		if (projectTechStackRequestObject.getTransactionType().equalsIgnoreCase(GETALL)) {
			getAll = projectTechStackRepo.findAll();
			
			if (getAll.isEmpty()) {
				response = new ProjectTechStackResponse();
				response.setProjectTechStackList(new ArrayList<ProjectTechStack>());
				response.setMessage("No records found");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			} 
				response = new ProjectTechStackResponse();
				response.setProjectTechStackList(getAll);
				response.setMessage("success");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			
		}
		if (projectTechStackRequestObject.getTransactionType().equalsIgnoreCase(GETBYID) && list.get(0).getId() != null) 
		{
			for (ProjectTechStack details : list)
			{
				if (details.getId() != null)
				{
					Integer id = projectTechStackRequestObject.getProjectTechStackList().get(0).getId();
					ArrayList<ProjectTechStack> projectTechStackList = new ArrayList<>();
					ProjectTechStack getById = projectTechStackRepo.findById(id).orElse(new ProjectTechStack());
					projectTechStackList.add(getById);
					if (getById != null && getById.getId() != null) {
						response = new ProjectTechStackResponse();
						response.setProjectTechStackList(projectTechStackList);
						response.setStatusCode("200");
						response.setMessage("success");
						return new ResponseEntity<>(response, HttpStatus.OK);
					} 
					
						response = new ProjectTechStackResponse();
						response.setStatusCode("422");
						response.setMessage("please provide valid id");
						return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
				}
			}
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
