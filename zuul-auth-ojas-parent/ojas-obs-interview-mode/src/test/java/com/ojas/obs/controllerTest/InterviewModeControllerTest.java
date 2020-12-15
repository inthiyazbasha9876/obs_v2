package com.ojas.obs.controllerTest;

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

import com.ojas.obs.interviewmode.controller.InterviewModeController;
import com.ojas.obs.interviewmode.facadeimpl.InterviewModeFacadeImpl;
import com.ojas.obs.interviewmode.model.InterviewMode;
import com.ojas.obs.interviewmode.request.InterviewModeRequest;
import com.ojas.obs.interviewmode.response.ErrorResponse;
import com.ojas.obs.interviewmode.response.InterviewModeResponse;

public class InterviewModeControllerTest {

	@InjectMocks
	InterviewModeController InterviewModeController;
	@Mock
	InterviewModeFacadeImpl impl;
	@Spy
	InterviewModeRequest request;
	@Spy
	InterviewModeResponse response = new InterviewModeResponse();
	@Spy
	ErrorResponse errorResponse = new ErrorResponse();
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<Object>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<Object>(response, HttpStatus.OK);
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<Object>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	InterviewMode InterviewMode;

	@Before
	public void init() throws Exception {
		InterviewModeController = new InterviewModeController();
		impl = mock(InterviewModeFacadeImpl.class);
		setCollaborator(InterviewModeController, "interviewModeFacadeImpl", impl);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<InterviewMode> getInterviewModelist() {
		List<InterviewMode> list = new ArrayList<InterviewMode>();
		InterviewMode InterviewMode = new InterviewMode();
		InterviewMode.setInterviewmodeId(1);
		InterviewMode.setInterviewMode("kusuma");
		list.add(InterviewMode);
		return list;
	}

	@Test
	public void setInterviewModeNullList() throws DuplicateKeyException {
		request = new InterviewModeRequest();
		ResponseEntity<Object> responseEntity = InterviewModeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void setInterviewModeNullType() throws NullPointerException {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		// request.setTransactionType(null);
		ResponseEntity<Object> responseEntity = InterviewModeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setTransactionEmpty() {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.setTransactionType("");
		ResponseEntity<Object> responseEntity = InterviewModeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void InterviewModeStatusNull() {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.getInterviewmodeList().get(0).setStatus(null);
		request.setTransactionType("save");
		ResponseEntity<Object> responseEntity = InterviewModeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void InterviewModeNameEmpty() {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.getInterviewmodeList().get(0).setInterviewMode("");
		request.setTransactionType("save");
		ResponseEntity<Object> responseEntity = InterviewModeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setUpdateNull() {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.getInterviewmodeList().get(0).setInterviewmodeId(null);
		request.setTransactionType("update");
		ResponseEntity<Object> responseEntity = InterviewModeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setUpdateNulll() {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.getInterviewmodeList().get(0).setStatus(null);
		request.setTransactionType("update");
		ResponseEntity<Object> responseEntity = InterviewModeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDeleteIdNull() {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.getInterviewmodeList().get(0).setInterviewmodeId(null);
		request.setTransactionType("delete");
		ResponseEntity<Object> responseEntity = InterviewModeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDeleteNameNull() {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.getInterviewmodeList().get(0).setStatus(null);
		request.setTransactionType("delete");
		ResponseEntity<Object> responseEntity = InterviewModeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setInterviewModeSuccessList() throws Exception {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.setTransactionType("save");
		request.getInterviewmodeList().get(0).setStatus(true);
		request.getInterviewmodeList().get(0).setInterviewMode("test");
		when(impl.saveDetails(request)).thenReturn(successResponse);
		ResponseEntity<Object> responseEntity = InterviewModeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setInterviewMode_DuplicateKeyException() throws Exception {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.setTransactionType("save");
		request.getInterviewmodeList().get(0).setStatus(true);
		request.getInterviewmodeList().get(0).setInterviewMode("test");
		when(impl.saveDetails(request)).thenThrow(DuplicateKeyException.class);
		ResponseEntity<Object> responseEntity = InterviewModeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setInterviewMode_Exception() throws Exception {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.setTransactionType("save");
		request.getInterviewmodeList().get(0).setStatus(true);
		request.getInterviewmodeList().get(0).setInterviewMode("test");
		when(impl.saveDetails(request)).thenThrow(new RuntimeException());
		ResponseEntity<Object> responseEntity = InterviewModeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void getExceptionNull() throws Exception {
		InterviewModeRequest request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.setTransactionType(null);
		ResponseEntity<Object> responseEntity = InterviewModeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void getByIdEmpty() throws Exception {
		InterviewModeRequest request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.setTransactionType("");
		ResponseEntity<Object> responseEntity = InterviewModeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void getByIdNull() {
		InterviewModeRequest request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.setTransactionType("getbyid");
		request.getInterviewmodeList().get(0).setInterviewmodeId(null);
		ResponseEntity<Object> responseEntity = InterviewModeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void getAllDetails() throws DuplicateKeyException, Exception {
		InterviewModeRequest request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.setTransactionType("getbyid");
		request.getInterviewmodeList().get(0).setInterviewmodeId(1);
		when(impl.getAllDetails(request)).thenReturn(successResponse);
		ResponseEntity<Object> responseEntity = InterviewModeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		  assertEquals(HttpStatus.OK, status);
		 
	}

	@Test
	public void set_DuplicateKeyException() throws Exception {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.setTransactionType("getbyid");
		request.getInterviewmodeList().get(0).setStatus(true);
		request.getInterviewmodeList().get(0).setInterviewMode("test");
		when(impl.getAllDetails(request)).thenThrow(DuplicateKeyException.class);
		ResponseEntity<Object> responseEntity = InterviewModeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void set_Exception() throws Exception {
		request = new InterviewModeRequest();
		request.setInterviewmodeList(getInterviewModelist());
		request.setTransactionType("getall");
		request.getInterviewmodeList().get(0).setStatus(true);
		request.getInterviewmodeList().get(0).setInterviewMode("test");
		when(impl.getAllDetails(request)).thenThrow(new RuntimeException());
		ResponseEntity<Object> responseEntity = InterviewModeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

}
