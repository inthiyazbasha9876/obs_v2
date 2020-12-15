package com.ojas.obs.actiontype.controllerTest;

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

import com.ojas.obs.actiontype.controller.ActionTypeController;
import com.ojas.obs.actiontype.facadeimpl.ActionTypeFacadeImpl;
import com.ojas.obs.actiontype.model.ActionType;
import com.ojas.obs.actiontype.request.ActionTypeRequest;
import com.ojas.obs.actiontype.response.ActionTypeResponse;
import com.ojas.obs.actiontype.response.ErrorResponse;

public class ActionTypeControllerTest {
	@InjectMocks
	ActionTypeController actionTypeController;

	@Mock
	ActionTypeFacadeImpl actionTypeFacadeImpl ;

	@Spy
	ActionTypeRequest actionTypereq;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	ActionTypeResponse actionTypeResponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(actionTypeResponse, HttpStatus.OK);

	@Spy
	ActionType actionType;

	@Before
	public void init() throws Exception {
		actionTypeController = new ActionTypeController();
		actionTypeFacadeImpl = mock(ActionTypeFacadeImpl.class);
		setCollaborator(actionTypeController, "actionTypeFacadeImpl", actionTypeFacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<ActionType> getActionTypelist() {
		List<ActionType> list = new ArrayList<ActionType>();
		ActionType ActionType = new ActionType();
		ActionType.setId(1);
		ActionType.setActiontype("kusuma");
		list.add(ActionType);
		return list;
	}

	@Test
	public void setActionTypeNullList() throws DuplicateKeyException {
		ActionTypeRequest request = new ActionTypeRequest();
		ResponseEntity<Object> responseEntity = actionTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void setActionTypeNullType() throws NullPointerException {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		// request.setTransactionType(null);
		ResponseEntity<Object> responseEntity = actionTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setTransactionEmpty() {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.setTransactionType("");
		ResponseEntity<Object> responseEntity = actionTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void ActionTypeStatusNull() {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.getActionTypeList().get(0).setStatus(null);
		request.setTransactionType("save");
		ResponseEntity<Object> responseEntity = actionTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void ActionTypeNameEmpty() {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.getActionTypeList().get(0).setActiontype("");
		request.setTransactionType("save");
		ResponseEntity<Object> responseEntity = actionTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setUpdateNull() {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.getActionTypeList().get(0).setId(null);
		request.setTransactionType("update");
		ResponseEntity<Object> responseEntity = actionTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setUpdateNulll() {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.getActionTypeList().get(0).setStatus(null);
		request.setTransactionType("update");
		ResponseEntity<Object> responseEntity = actionTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDeleteIdNull() {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.getActionTypeList().get(0).setId(null);
		request.setTransactionType("delete");
		ResponseEntity<Object> responseEntity = actionTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setDeleteNameNull() {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.getActionTypeList().get(0).setStatus(null);
		request.setTransactionType("delete");
		ResponseEntity<Object> responseEntity = actionTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void setActionTypeSuccessList() throws Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.setTransactionType("save");
		request.getActionTypeList().get(0).setStatus(true);
		request.getActionTypeList().get(0).setActiontype("test");
		when(actionTypeFacadeImpl.saveDetails(request)).thenReturn(successResponse);
		ResponseEntity<Object> responseEntity = actionTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setActionType_DuplicateKeyException() throws Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.setTransactionType("save");
		request.getActionTypeList().get(0).setStatus(true);
		request.getActionTypeList().get(0).setActiontype("test");
		when(actionTypeFacadeImpl.saveDetails(request)).thenThrow(DuplicateKeyException.class);
		ResponseEntity<Object> responseEntity = actionTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setActionType_Exception() throws Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.setTransactionType("save");
		request.getActionTypeList().get(0).setStatus(true);
		request.getActionTypeList().get(0).setActiontype("test");
		when(actionTypeFacadeImpl.saveDetails(request)).thenThrow(new RuntimeException());
		ResponseEntity<Object> responseEntity = actionTypeController.save(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void getExceptionNull() throws Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.setTransactionType(null);
		ResponseEntity<Object> responseEntity = actionTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void getByIdEmpty() throws Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.setTransactionType("");
		ResponseEntity<Object> responseEntity = actionTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void getByIdNull() {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.setTransactionType("getbyid");
		request.getActionTypeList().get(0).setId(null);
		ResponseEntity<Object> responseEntity = actionTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@Test
	public void getAllDetails() throws DuplicateKeyException, Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.setTransactionType("getbyid");
		request.getActionTypeList().get(0).setId(1);
		when(actionTypeFacadeImpl.getAllDetails(request)).thenReturn(successResponse);
		ResponseEntity<Object> responseEntity = actionTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);

	}

	@Test
	public void set_DuplicateKeyException() throws Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.setTransactionType("getbyid");
		request.getActionTypeList().get(0).setStatus(true);
		request.getActionTypeList().get(0).setActiontype("test");
		when(actionTypeFacadeImpl.getAllDetails(request)).thenThrow(DuplicateKeyException.class);
		ResponseEntity<Object> responseEntity = actionTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void set_Exception() throws Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypelist());
		request.setTransactionType("getall");
		request.getActionTypeList().get(0).setStatus(true);
		request.getActionTypeList().get(0).setActiontype("test");
		when(actionTypeFacadeImpl.getAllDetails(request)).thenThrow(new RuntimeException());
		ResponseEntity<Object> responseEntity = actionTypeController.getAllIssues(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

}
