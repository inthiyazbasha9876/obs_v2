package com.ojas.obs.facadeImpltest;

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

import com.ojas.obs.projecttype.facadeimpl.ProjectTypeFacadeImpl;
import com.ojas.obs.projecttype.model.ProjectType;
import com.ojas.obs.projecttype.repositories.ProjectTypeRepository;
import com.ojas.obs.projecttype.request.ProjectTypeRequest;
import com.ojas.obs.projecttype.response.ErrorResponse;
import com.ojas.obs.projecttype.response.ProjectTypeResponse;

public class ProjectTypeFacadeImplTest {

	@InjectMocks
	ProjectTypeFacadeImpl impl;
	@Mock
	ProjectTypeRepository projectTypeDao;
	@Spy
	ProjectType projectType;
	@Spy
	ProjectTypeRequest request;
	@Spy
	ProjectTypeResponse response;
	@Spy
	ErrorResponse errorResponse;

	@Before
	public void init() throws Exception {
		impl = new ProjectTypeFacadeImpl();
		projectTypeDao = mock(ProjectTypeRepository.class);
		setCollaborator(impl, "projectTypeRepository", projectTypeDao);
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
	public void setTransaction() throws DuplicateKeyException, Exception {
		request = new ProjectTypeRequest();
		request.setTransactionType("save");
		request.setProjectTypeList(getProjectTypeList());
		ProjectType ProjectType = new ProjectType();
		ProjectType.setId(2);
		when(projectTypeDao.save(request.getProjectTypeList().get(0))).thenReturn(ProjectType);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setTransactionNull() throws DuplicateKeyException, Exception {
		request = new ProjectTypeRequest();
		request.setTransactionType("save");
		request.setProjectTypeList(getProjectTypeList());
		ProjectType ProjectType = new ProjectType();
		ProjectType.setId(null);
		when(projectTypeDao.save(request.getProjectTypeList().get(0))).thenReturn(ProjectType);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setUpdateNotNull() throws DuplicateKeyException, Exception {
		request = new ProjectTypeRequest();
		request.setTransactionType("update");
		request.setProjectTypeList(getProjectTypeList());
		ProjectType ProjectType = new ProjectType();
		ProjectType.setId(3);
		when(projectTypeDao.getOne(1)).thenReturn(ProjectType);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setUpdateNull() throws DuplicateKeyException, Exception {
		request = new ProjectTypeRequest();
		request.setTransactionType("update");
		request.setProjectTypeList(getProjectTypeList());
		ProjectType ProjectType = new ProjectType();
		ProjectType.setId(null);
		when(projectTypeDao.getOne(1)).thenReturn(ProjectType);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setDeleteNotNull() throws DuplicateKeyException, Exception {
		request = new ProjectTypeRequest();
		request.setTransactionType("delete");
		request.setProjectTypeList(getProjectTypeList());
		ProjectType ProjectType = new ProjectType();
		ProjectType.setStatus(false);
		when(projectTypeDao.getOne(1)).thenReturn(ProjectType);
		when(projectTypeDao.save(ProjectType)).thenReturn(request.getProjectTypeList().get(0));
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setDeleteNull() throws DuplicateKeyException, Exception {
		request = new ProjectTypeRequest();
		request.setTransactionType("delete");
		request.setProjectTypeList(getProjectTypeList());
		ProjectType ProjectType = new ProjectType();
		ProjectType.setStatus(getProjectTypeList().isEmpty());
		when(projectTypeDao.getOne(1)).thenReturn(ProjectType);
		// when(ProjectTypeDao.save(ProjectType)).thenReturn(request.getProjectTypeList().get(0));
		when(projectTypeDao.save(ProjectType)).thenReturn(ProjectType);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setDeleteNotAcceptable() throws DuplicateKeyException, Exception {
		request = new ProjectTypeRequest();
		request.setTransactionType("");
		request.setProjectTypeList(getProjectTypeList());
		ProjectType ProjectType = new ProjectType();
		ProjectType.setStatus(getProjectTypeList().isEmpty());
		when(projectTypeDao.getOne(1)).thenReturn(ProjectType);
		when(projectTypeDao.save(ProjectType)).thenReturn(ProjectType);
		ResponseEntity<Object> saveStatus = impl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.NOT_ACCEPTABLE, statusCode);
	}

	@Test
	public void setGetAllNotNull() throws DuplicateKeyException, Exception {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.setTransactionType("getall");
		ProjectType ProjectType = new ProjectType();
		ProjectType.setStatus(false);
		when(projectTypeDao.findAll()).thenReturn(request.getProjectTypeList());
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setGetAllNull() throws DuplicateKeyException, Exception {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.setTransactionType("getall");
		request.getProjectTypeList().get(0).setId(1);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setGetByIdNotNull() throws DuplicateKeyException, Exception {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.setTransactionType("getbyid");
		ProjectType ProjectType = new ProjectType();
		ProjectType.setId(1);
		when(projectTypeDao.getOne(1)).thenReturn(ProjectType);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setGetByIdNull() throws DuplicateKeyException, Exception {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.setTransactionType("getbyid");
		ProjectType ProjectType = new ProjectType();
		ProjectType.setId(null);
		when(projectTypeDao.getOne(1)).thenReturn(ProjectType);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setGetByIdMethodNull() throws DuplicateKeyException, Exception {
		request = new ProjectTypeRequest();
		request.setProjectTypeList(getProjectTypeList());
		request.setTransactionType("");
		ProjectType ProjectType = new ProjectType();
		when(projectTypeDao.getOne(1)).thenReturn(ProjectType);
		ResponseEntity<Object> responseEntity = impl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.NOT_ACCEPTABLE, status);
	}
}