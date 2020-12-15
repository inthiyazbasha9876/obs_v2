package com.ojas.obs.projecttechstack.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.projecttechstack.controller.ProjectTechStackController;
import com.ojas.obs.projecttechstack.facade.ProjectTechStackFacade;
import com.ojas.obs.projecttechstack.facadeimpl.ProjectTechStackFacadeImpl;
import com.ojas.obs.projecttechstack.model.ProjectTechStack;
import com.ojas.obs.projecttechstack.request.ProjectTechStackRequest;
import com.ojas.obs.projecttechstack.response.ErrorResponse;
import com.ojas.obs.projecttechstack.response.ProjectTechStackResponse;

public class ProjectTechStackControllerTest {

	@InjectMocks
	ProjectTechStackController projectTechStackController;

	@Mock
	ProjectTechStackFacade projectTechStackFacade;

	@Spy
	ProjectTechStackRequest projectTechStackRequest;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	ProjectTechStackResponse projectTechStackResponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(projectTechStackResponse, HttpStatus.OK);

	@Spy
	ProjectTechStack projectTechStack;

	@Before
	public void init() throws Exception {
		projectTechStackController = new ProjectTechStackController();
		projectTechStackFacade = mock(ProjectTechStackFacadeImpl.class);
		setCollaborator(projectTechStackController, "projectTechStackFacade", projectTechStackFacade);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<ProjectTechStack> getProjectTechStack() {

		List<ProjectTechStack> projectTechStackList = new ArrayList<ProjectTechStack>();
		ProjectTechStack tech = new ProjectTechStack();
		tech.setId(1);
		tech.setTechnology("cTA");

		ProjectTechStack tech1 = new ProjectTechStack();
		tech1.setId(1);
		tech1.setTechnology("KK");

		projectTechStackList.add(tech);
		projectTechStackList.add(tech1);
		return projectTechStackList;
	}

	@Test
	public void ProjectTechStackNullTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		ProjectTechStackRequest projectTechStackRequest = new ProjectTechStackRequest();

		projectTechStackRequest.setProjectTechStackList(this.getProjectTechStack());
		projectTechStackRequest.setTransactionType(null);

		when(projectTechStackFacade.saveProjectTechStack(projectTechStackRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = projectTechStackController.projectTechStack(projectTechStackRequest,
				request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void ProjectTechStackRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		ProjectTechStackRequest projectTechStackRequest = new ProjectTechStackRequest();
		projectTechStackRequest.setProjectTechStackList(this.getProjectTechStack());
		projectTechStackRequest.setTransactionType("save");

		when(projectTechStackFacade.saveProjectTechStack(projectTechStackRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = projectTechStackController.projectTechStack(projectTechStackRequest,
				request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test

	public void ProjectTechStackRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		ProjectTechStackRequest projectTechStackRequest = new ProjectTechStackRequest();
		projectTechStackRequest.setProjectTechStackList(this.getProjectTechStack());
		projectTechStackRequest.setTransactionType("update");

		when(projectTechStackFacade.saveProjectTechStack(projectTechStackRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = projectTechStackController.projectTechStack(projectTechStackRequest,
				request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void ProjectTechStackRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		ProjectTechStackRequest projectTechStackRequest = new ProjectTechStackRequest();
		projectTechStackRequest.setProjectTechStackList(this.getProjectTechStack());
		projectTechStackRequest.setTransactionType("delete");

		when(projectTechStackFacade.saveProjectTechStack(projectTechStackRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = projectTechStackController.projectTechStack(projectTechStackRequest,
				request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		ProjectTechStackRequest projectTechStackRequest = new ProjectTechStackRequest();

		ProjectTechStack tech2 = new ProjectTechStack();
		tech2.setTechnology("any cato");
		tech2.setStatus(true);

		List<ProjectTechStack> projectTechStack = new ArrayList<ProjectTechStack>();
		projectTechStack.add(tech2);
		projectTechStackRequest.setProjectTechStackList(projectTechStack);
		projectTechStackRequest.setTransactionType("save");

		when(projectTechStackFacade.saveProjectTechStack(projectTechStackRequest))
				.thenThrow(new DuplicateKeyException(null, new Throwable()));
		ResponseEntity<Object> setBus = projectTechStackController.projectTechStack(projectTechStackRequest,
				request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		ProjectTechStackRequest projectTechStackRequest = new ProjectTechStackRequest();

		ProjectTechStack tech2 = new ProjectTechStack();
		tech2.setTechnology("any cato");
		tech2.setStatus(false);

		List<ProjectTechStack> projectTechStack = new ArrayList<ProjectTechStack>();
		projectTechStack.add(tech2);
		projectTechStackRequest.setProjectTechStackList(projectTechStack);
		projectTechStackRequest.setTransactionType("save");

		when(projectTechStackFacade.saveProjectTechStack(projectTechStackRequest)).thenThrow(RuntimeException.class);
		ResponseEntity<Object> setBus = projectTechStackController.projectTechStack(projectTechStackRequest,
				request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

//	@Test
//	public void setsavesucces() throws Exception {
//		HttpServletRequest request = null;
//		HttpServletResponse response = null;
//		ProjectTechStackRequest projectTechStackRequest = new ProjectTechStackRequest();
//
//		ProjectTechStack tech2 = new ProjectTechStack();
//		tech2.setTechnology("any cato");
//		tech2.setStatus(false);
//
//		List<ProjectTechStack> projectTechStack = new ArrayList<ProjectTechStack>();
//		projectTechStack.add(tech2);
//		projectTechStackRequest.setProjectTechStackList(projectTechStack);
//		projectTechStackRequest.setTransactionType("save");
//		when(projectTechStackFacade.saveProjectTechStack(projectTechStackRequest)).thenReturn(successResponse);
//
//		ResponseEntity<Object> setBus = projectTechStackController.projectTechStack(projectTechStackRequest,
//				request, response);
//		HttpStatus unitCode = setBus.getStatusCode();
//		assertEquals(HttpStatus.OK, unitCode);
//	}

	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		ProjectTechStackRequest projectTechStackRequest = new ProjectTechStackRequest();

		ProjectTechStack tech2 = new ProjectTechStack();
		tech2.setTechnology("any cato");
		tech2.setStatus(false);

		List<ProjectTechStack> projectTechStack = new ArrayList<ProjectTechStack>();
		projectTechStack.add(tech2);
		projectTechStackRequest.setProjectTechStackList(projectTechStack);
		projectTechStackRequest.setTransactionType("update");
		when(projectTechStackFacade.saveProjectTechStack(projectTechStackRequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = projectTechStackController.projectTechStack(projectTechStackRequest,
				request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		ProjectTechStackRequest projectTechStackRequest = new ProjectTechStackRequest();

		ProjectTechStack tech2 = new ProjectTechStack();
		tech2.setTechnology("any cato");
		tech2.setStatus(false);

		List<ProjectTechStack> projectTechStack = new ArrayList<ProjectTechStack>();
		projectTechStack.add(tech2);
		projectTechStackRequest.setProjectTechStackList(projectTechStack);
		projectTechStackRequest.setTransactionType("update");
		when(projectTechStackFacade.saveProjectTechStack(projectTechStackRequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = projectTechStackController.projectTechStack(projectTechStackRequest,
				request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

//get testcases
	@Test

	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		ProjectTechStackRequest projectTechStackRequest = new ProjectTechStackRequest();

		projectTechStackRequest.setProjectTechStackList(this.getProjectTechStack());
		projectTechStackRequest.setTransactionType(null);

		when(projectTechStackFacade.getProjectTechStack(projectTechStackRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = projectTechStackController.getProjectTechStack(projectTechStackRequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ProjectTechStackRequest projectTechStackRequest = new ProjectTechStackRequest();

    
    	projectTechStackRequest.setProjectTechStackList(this.getProjectTechStack());
    	projectTechStackRequest.setTransactionType("getbyid");
		
    	projectTechStackRequest.getProjectTechStackList().get(0).getId();
		
		when(projectTechStackFacade.getProjectTechStack(projectTechStackRequest)).thenReturn(successResponse);
		ResponseEntity<Object> project= projectTechStackController.getProjectTechStack(projectTechStackRequest, request, response);
		HttpStatus unitCode = project.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ProjectTechStackRequest projectTechStackRequest = new ProjectTechStackRequest();

    	projectTechStackRequest.setProjectTechStackList(this.getProjectTechStack());
    	projectTechStackRequest.setTransactionType("getbyid");
    	
    	projectTechStackRequest.getProjectTechStackList().get(0).setId(null);
		
		when(projectTechStackFacade.getProjectTechStack(projectTechStackRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = projectTechStackController.getProjectTechStack(projectTechStackRequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	

	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ProjectTechStackRequest projectTechStackRequest = new ProjectTechStackRequest();
  

    	projectTechStackRequest.setProjectTechStackList(this.getProjectTechStack());
    	projectTechStackRequest.setTransactionType("getAll");
    	
    	when(projectTechStackFacade.getProjectTechStack(projectTechStackRequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = projectTechStackController.getProjectTechStack(projectTechStackRequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
}
