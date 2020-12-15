package com.ojas.obs.controllertest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.projecttype.controller.ProjectTypeController;
import com.ojas.obs.projecttype.facadeimpl.ProjectTypeFacadeImpl;
import com.ojas.obs.projecttype.model.ProjectType;
import com.ojas.obs.projecttype.request.ProjectTypeRequest;
import com.ojas.obs.projecttype.response.ErrorResponse;
import com.ojas.obs.projecttype.response.ProjectTypeResponse;

public class ProjectTypeControllerTest {

	@InjectMocks
	ProjectTypeController projectTypeController;
	@Mock
	ProjectTypeFacadeImpl impl;
	@Spy
	ProjectTypeRequest request;
	@Spy
	ProjectTypeResponse response = new ProjectTypeResponse();
	@Spy
	ErrorResponse errorResponse = new ErrorResponse();
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<Object>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<Object>(response, HttpStatus.OK);
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<Object>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	ProjectType projectType;

	@Before
	public void init() throws Exception {
		projectTypeController = new ProjectTypeController();
		impl = mock(ProjectTypeFacadeImpl.class);
		setCollaborator(projectTypeController, "facadeImpl", impl);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<ProjectType> getProjectTypeList() {
		List<ProjectType> list = new ArrayList<ProjectType>();
		ProjectType ProjectType = new ProjectType();
		ProjectType.setId(1);
		ProjectType.setProjectType("kusuma");
		list.add(ProjectType);
		return list;
	}

	@Test
	public void setProjectTypeNullList() throws DuplicateKeyException {
		request = new ProjectTypeRequest();
		ResponseEntity<Object> responseEntity = projectTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void setProjectTypeNullType() throws NullPointerException {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		// request.setTransactionType(null);
		ResponseEntity<Object> responseEntity = projectTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setTransactionEmpty() {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.setTransactionType("");
		ResponseEntity<Object> responseEntity = projectTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void ProjectTypeStatusNull() {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.getProjectTypeList().get(0).setStatus(null);
		request.setTransactionType("save");
		ResponseEntity<Object> responseEntity = projectTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void ProjectTypeNameEmpty() {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.getProjectTypeList().get(0).setProjectType("");
		request.setTransactionType("save");
		ResponseEntity<Object> responseEntity = projectTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setUpdateNull() {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.getProjectTypeList().get(0).setId(null);
		request.setTransactionType("update");
		ResponseEntity<Object> responseEntity = projectTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setUpdateNulll() {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.getProjectTypeList().get(0).setStatus(null);
		request.setTransactionType("update");
		ResponseEntity<Object> responseEntity = projectTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDeleteIdNull() {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.getProjectTypeList().get(0).setId(null);
		request.setTransactionType("delete");
		ResponseEntity<Object> responseEntity = projectTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDeleteNameNull() {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.getProjectTypeList().get(0).setStatus(null);
		request.setTransactionType("delete");
		ResponseEntity<Object> responseEntity = projectTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setProjectTypeSuccessList() throws Exception {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.setTransactionType("save");
		request.getProjectTypeList().get(0).setStatus(true);
		request.getProjectTypeList().get(0).setProjectType("test");
		when(impl.saveDetails(request)).thenReturn(successResponse);
		ResponseEntity<Object> responseEntity = projectTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setProjectType_DuplicateKeyException() throws Exception {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.setTransactionType("save");
		request.getProjectTypeList().get(0).setStatus(true);
		request.getProjectTypeList().get(0).setProjectType("test");
		when(impl.saveDetails(request)).thenThrow(DuplicateKeyException.class);
		ResponseEntity<Object> responseEntity = projectTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setProjectType_Exception() throws Exception {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.setTransactionType("save");
		request.getProjectTypeList().get(0).setStatus(true);
		request.getProjectTypeList().get(0).setProjectType("test");
		when(impl.saveDetails(request)).thenThrow(new RuntimeException());
		ResponseEntity<Object> responseEntity = projectTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void getExceptionNull() throws Exception {
		ProjectTypeRequest request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.setTransactionType(null);
		ResponseEntity<Object> responseEntity = projectTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void getByIdEmpty() throws Exception {
		ProjectTypeRequest request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.setTransactionType("");
		ResponseEntity<Object> responseEntity = projectTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void getByIdNull() {
		ProjectTypeRequest request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.setTransactionType("getbyid");
		request.getProjectTypeList().get(0).setId(null);
		ResponseEntity<Object> responseEntity = projectTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}
	@Test
	public void getAllDetails() {
		ProjectTypeRequest request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.setTransactionType("getbyid");
		request.getProjectTypeList().get(0).setId(1);
		ResponseEntity<Object> responseEntity = projectTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}
	@Test
	public void set_DuplicateKeyException() throws Exception {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.setTransactionType("getbyid");
		request.getProjectTypeList().get(0).setStatus(true);
		request.getProjectTypeList().get(0).setProjectType("test");
		when(impl.getAllDetails(request)).thenThrow(DuplicateKeyException.class);
		ResponseEntity<Object> responseEntity = projectTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}
	@Test
	public void set_Exception() throws Exception {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.setTransactionType("getall");
		request.getProjectTypeList().get(0).setStatus(true);
		request.getProjectTypeList().get(0).setProjectType("test");
		when(impl.getAllDetails(request)).thenThrow(new RuntimeException());
		ResponseEntity<Object> responseEntity = projectTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

}
