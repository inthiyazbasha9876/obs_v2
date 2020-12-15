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

import com.ojas.obs.billingtype.controller.BillingTypeController;
import com.ojas.obs.billingtype.facadeimpl.BillingTypeFacadeImpl;
import com.ojas.obs.billingtype.model.BillingType;
import com.ojas.obs.billingtype.request.BillingTypeRequest;
import com.ojas.obs.billingtype.response.BillingTypeResponse;
import com.ojas.obs.billingtype.response.ErrorResponse;

public class BillingTypeControllerTest {

	@InjectMocks
	BillingTypeController billingTypeController;
	@Mock
	BillingTypeFacadeImpl impl;
	@Spy
	BillingTypeRequest request;
	@Spy
	BillingTypeResponse response = new BillingTypeResponse();
	@Spy
	ErrorResponse errorResponse = new ErrorResponse();
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<Object>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<Object>(response, HttpStatus.OK);
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<Object>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	BillingType BillingType;

	@Before
	public void init() throws Exception {
		billingTypeController = new BillingTypeController();
		impl = mock(BillingTypeFacadeImpl.class);
		setCollaborator(billingTypeController, "billingTypeFacadeImpl", impl);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<BillingType> getBillingTypelist() {
		List<BillingType> list = new ArrayList<BillingType>();
		BillingType BillingType = new BillingType();
		BillingType.setId(1);
		BillingType.setName("kusuma");
		list.add(BillingType);
		return list;
	}

	@Test
	public void setBillingTypeNullList() throws DuplicateKeyException {
		request = new BillingTypeRequest();
		ResponseEntity<Object> responseEntity = billingTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void setBillingTypeNullType() throws NullPointerException {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		// request.setTransactionType(null);
		ResponseEntity<Object> responseEntity = billingTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setTransactionEmpty() {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.setTransactionType("");
		ResponseEntity<Object> responseEntity = billingTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void BillingTypeStatusNull() {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.getBillingList().get(0).setStatus(null);
		request.setTransactionType("save");
		ResponseEntity<Object> responseEntity = billingTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void BillingTypeNameEmpty() {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.getBillingList().get(0).setName("");
		request.setTransactionType("save");
		ResponseEntity<Object> responseEntity = billingTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setUpdateNull() {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.getBillingList().get(0).setId(null);
		request.setTransactionType("update");
		ResponseEntity<Object> responseEntity = billingTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setUpdateNulll() {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.getBillingList().get(0).setStatus(null);
		request.setTransactionType("update");
		ResponseEntity<Object> responseEntity = billingTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDeleteIdNull() {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.getBillingList().get(0).setId(null);
		request.setTransactionType("delete");
		ResponseEntity<Object> responseEntity = billingTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDeleteNameNull() {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.getBillingList().get(0).setStatus(null);
		request.setTransactionType("delete");
		ResponseEntity<Object> responseEntity = billingTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setBillingTypeSuccessList() throws Exception {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.setTransactionType("save");
		request.getBillingList().get(0).setStatus(true);
		request.getBillingList().get(0).setName("test");
		when(impl.saveDetails(request)).thenReturn(successResponse);
		ResponseEntity<Object> responseEntity = billingTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setBillingType_DuplicateKeyException() throws Exception {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.setTransactionType("save");
		request.getBillingList().get(0).setStatus(true);
		request.getBillingList().get(0).setName("test");
		when(impl.saveDetails(request)).thenThrow(DuplicateKeyException.class);
		ResponseEntity<Object> responseEntity = billingTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setBillingType_Exception() throws Exception {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.setTransactionType("save");
		request.getBillingList().get(0).setStatus(true);
		request.getBillingList().get(0).setName("test");
		when(impl.saveDetails(request)).thenThrow(new RuntimeException());
		ResponseEntity<Object> responseEntity = billingTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void getExceptionNull() throws Exception {
		BillingTypeRequest request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.setTransactionType(null);
		ResponseEntity<Object> responseEntity = billingTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void getByIdEmpty() throws Exception {
		BillingTypeRequest request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.setTransactionType("");
		ResponseEntity<Object> responseEntity = billingTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void getByIdNull() {
		BillingTypeRequest request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.setTransactionType("getbyid");
		request.getBillingList().get(0).setId(null);
		ResponseEntity<Object> responseEntity = billingTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void getAllDetails() throws DuplicateKeyException, Exception {
		BillingTypeRequest request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.setTransactionType("getbyid");
		request.getBillingList().get(0).setId(1);
		when(impl.getAllDetails(request)).thenReturn(successResponse);
		ResponseEntity<Object> responseEntity = billingTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		  assertEquals(HttpStatus.OK, status);
		 
	}

	@Test
	public void set_DuplicateKeyException() throws Exception {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.setTransactionType("getbyid");
		request.getBillingList().get(0).setStatus(true);
		request.getBillingList().get(0).setName("test");
		when(impl.getAllDetails(request)).thenThrow(DuplicateKeyException.class);
		ResponseEntity<Object> responseEntity = billingTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void set_Exception() throws Exception {
		request = new BillingTypeRequest();
		request.setBillingList(getBillingTypelist());
		request.setTransactionType("getall");
		request.getBillingList().get(0).setStatus(true);
		request.getBillingList().get(0).setName("test");
		when(impl.getAllDetails(request)).thenThrow(new RuntimeException());
		ResponseEntity<Object> responseEntity = billingTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

}
