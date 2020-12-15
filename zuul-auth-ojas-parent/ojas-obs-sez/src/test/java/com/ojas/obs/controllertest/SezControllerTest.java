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

import com.ojas.obs.sez.controller.SezController;
import com.ojas.obs.sez.facadeimpl.SezFacadeImpl;
import com.ojas.obs.sez.model.Sez;
import com.ojas.obs.sez.request.SezRequest;
import com.ojas.obs.sez.response.ErrorResponse;
import com.ojas.obs.sez.response.SezResponse;

public class SezControllerTest {

	@InjectMocks
	SezController sezController;
	@Mock
	SezFacadeImpl impl;
	@Spy
	SezRequest request;
	@Spy
	SezResponse response = new SezResponse();
	@Spy
	ErrorResponse errorResponse = new ErrorResponse();
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<Object>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<Object>(response, HttpStatus.OK);
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<Object>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	Sez sez;

	@Before
	public void init() throws Exception {
		sezController = new SezController();
		impl = mock(SezFacadeImpl.class);
		setCollaborator(sezController, "sezFacadeImpl", impl);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<Sez> getSezlist() {
		List<Sez> list = new ArrayList<Sez>();
		Sez sez = new Sez();
		sez.setId(1);
		sez.setName("kusuma");
		list.add(sez);
		return list;
	}

	@Test
	public void setSezNullList() throws DuplicateKeyException {
		request = new SezRequest();
		ResponseEntity<Object> responseEntity = sezController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void setSezNullType() throws NullPointerException {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		// request.setTransactionType(null);
		ResponseEntity<Object> responseEntity = sezController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setTransactionEmpty() {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.setTransactionType("");
		ResponseEntity<Object> responseEntity = sezController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void sezStatusNull() {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.getSezlist().get(0).setStatus(null);
		request.setTransactionType("save");
		ResponseEntity<Object> responseEntity = sezController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void sezNameEmpty() {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.getSezlist().get(0).setName("");
		request.setTransactionType("save");
		ResponseEntity<Object> responseEntity = sezController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setUpdateNull() {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.getSezlist().get(0).setId(null);
		request.setTransactionType("update");
		ResponseEntity<Object> responseEntity = sezController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setUpdateNulll() {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.getSezlist().get(0).setStatus(null);
		request.setTransactionType("update");
		ResponseEntity<Object> responseEntity = sezController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDeleteIdNull() {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.getSezlist().get(0).setId(null);
		request.setTransactionType("delete");
		ResponseEntity<Object> responseEntity = sezController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDeleteNameNull() {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.getSezlist().get(0).setStatus(null);
		request.setTransactionType("delete");
		ResponseEntity<Object> responseEntity = sezController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setSezSuccessList() throws Exception {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.setTransactionType("save");
		request.getSezlist().get(0).setStatus(true);
		request.getSezlist().get(0).setName("test");
		when(impl.saveDetails(request)).thenReturn(successResponse);
		ResponseEntity<Object> responseEntity = sezController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setSez_DuplicateKeyException() throws Exception {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.setTransactionType("save");
		request.getSezlist().get(0).setStatus(true);
		request.getSezlist().get(0).setName("test");
		when(impl.saveDetails(request)).thenThrow(DuplicateKeyException.class);
		ResponseEntity<Object> responseEntity = sezController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setSez_Exception() throws Exception {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.setTransactionType("save");
		request.getSezlist().get(0).setStatus(true);
		request.getSezlist().get(0).setName("test");
		when(impl.saveDetails(request)).thenThrow(new RuntimeException());
		ResponseEntity<Object> responseEntity = sezController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void getExceptionNull() throws Exception {
		SezRequest request = new SezRequest();
		request.setSezlist(getSezlist());
		request.setTransactionType(null);
		ResponseEntity<Object> responseEntity = sezController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void getByIdEmpty() throws Exception {
		SezRequest request = new SezRequest();
		request.setSezlist(getSezlist());
		request.setTransactionType("");
		ResponseEntity<Object> responseEntity = sezController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void getByIdNull() {
		SezRequest request = new SezRequest();
		request.setSezlist(getSezlist());
		request.setTransactionType("getbyid");
		request.getSezlist().get(0).setId(null);
		ResponseEntity<Object> responseEntity = sezController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}
	@Test
	public void getAllDetails() {
		SezRequest request = new SezRequest();
		request.setSezlist(getSezlist());
		request.setTransactionType("getbyid");
		request.getSezlist().get(0).setId(1);
		ResponseEntity<Object> responseEntity = sezController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}
	@Test
	public void set_DuplicateKeyException() throws Exception {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.setTransactionType("getbyid");
		request.getSezlist().get(0).setStatus(true);
		request.getSezlist().get(0).setName("test");
		when(impl.getAllDetails(request)).thenThrow(DuplicateKeyException.class);
		ResponseEntity<Object> responseEntity = sezController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}
	@Test
	public void set_Exception() throws Exception {
		request = new SezRequest();
		request.setSezlist(getSezlist());
		request.setTransactionType("getall");
		request.getSezlist().get(0).setStatus(true);
		request.getSezlist().get(0).setName("test");
		when(impl.getAllDetails(request)).thenThrow(new RuntimeException());
		ResponseEntity<Object> responseEntity = sezController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

}
