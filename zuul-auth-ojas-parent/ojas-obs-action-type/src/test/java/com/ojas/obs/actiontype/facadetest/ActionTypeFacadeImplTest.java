package com.ojas.obs.actiontype.facadetest;

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

import com.ojas.obs.actiontype.facade.ActionTypeFacade;
import com.ojas.obs.actiontype.facadeimpl.ActionTypeFacadeImpl;
import com.ojas.obs.actiontype.model.ActionType;
import com.ojas.obs.actiontype.repository.ActionTypeRepository;
import com.ojas.obs.actiontype.request.ActionTypeRequest;
import com.ojas.obs.actiontype.response.ActionTypeResponse;
import com.ojas.obs.actiontype.response.ErrorResponse;

public class ActionTypeFacadeImplTest {
	@InjectMocks
	ActionTypeFacadeImpl actionTypeFacadeImpl;

	@Mock
	ActionTypeRepository actionTypeRepo;

	@Mock
	ActionTypeFacade actionTypeFacade;

	@Spy
	ActionTypeRequest actionTypeReq;

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
		actionTypeFacadeImpl = new ActionTypeFacadeImpl();
		actionTypeRepo = mock(ActionTypeRepository.class);
		setCollaborator(actionTypeFacadeImpl, "actionTypeRepository", actionTypeRepo);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<ActionType> getActionTypeList() {
		List<ActionType> list = new ArrayList<ActionType>();
		ActionType ActionType = new ActionType();
		ActionType.setId(1);
		ActionType.setActiontype("kusuma");
		list.add(ActionType);
		return list;
	}

	@Test
	public void setTransaction() throws DuplicateKeyException, Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setTransactionType("save");
		request.setActionTypeList(getActionTypeList());
		ActionType ActionType = new ActionType();
		ActionType.setId(2);
		when(actionTypeRepo.save(request.getActionTypeList().get(0))).thenReturn(ActionType);
		ResponseEntity<Object> saveStatus = actionTypeFacadeImpl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setTransactionNull() throws DuplicateKeyException, Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setTransactionType("save");
		request.setActionTypeList(getActionTypeList());
		ActionType ActionType = new ActionType();
		ActionType.setId(null);
		when(actionTypeRepo.save(request.getActionTypeList().get(0))).thenReturn(ActionType);
		ResponseEntity<Object> saveStatus = actionTypeFacadeImpl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setUpdateNotNull() throws DuplicateKeyException, Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setTransactionType("update");
		request.setActionTypeList(getActionTypeList());
		ActionType ActionType = new ActionType();
		ActionType.setId(3);
		when(actionTypeRepo.getOne(1)).thenReturn(ActionType);
		ResponseEntity<Object> saveStatus = actionTypeFacadeImpl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setUpdateNull() throws DuplicateKeyException, Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setTransactionType("update");
		request.setActionTypeList(getActionTypeList());
		ActionType ActionType = new ActionType();
		ActionType.setId(null);
		when(actionTypeRepo.getOne(1)).thenReturn(ActionType);
		ResponseEntity<Object> saveStatus = actionTypeFacadeImpl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setDeleteNotNull() throws DuplicateKeyException, Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setTransactionType("delete");
		request.setActionTypeList(getActionTypeList());
		ActionType ActionType = new ActionType();
		ActionType.setStatus(false);
		when(actionTypeRepo.getOne(1)).thenReturn(ActionType);
		when(actionTypeRepo.save(ActionType)).thenReturn(request.getActionTypeList().get(0));
		ResponseEntity<Object> saveStatus = actionTypeFacadeImpl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setDeleteNull() throws DuplicateKeyException, Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setTransactionType("delete");
		request.setActionTypeList(getActionTypeList());
		ActionType ActionType = new ActionType();
		ActionType.setStatus(getActionTypeList().isEmpty());
		when(actionTypeRepo.getOne(1)).thenReturn(ActionType);
		// when(ActionTypeRepository.save(ActionType)).thenReturn(request.getActionTypeList().get(0));
		when(actionTypeRepo.save(ActionType)).thenReturn(ActionType);
		ResponseEntity<Object> saveStatus = actionTypeFacadeImpl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setDeleteNotAcceptable() throws DuplicateKeyException, Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setTransactionType("");
		request.setActionTypeList(getActionTypeList());
		ActionType ActionType = new ActionType();
		ActionType.setStatus(getActionTypeList().isEmpty());
		when(actionTypeRepo.getOne(1)).thenReturn(ActionType);
		when(actionTypeRepo.save(ActionType)).thenReturn(ActionType);
		ResponseEntity<Object> saveStatus = actionTypeFacadeImpl.saveDetails(request);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.NOT_ACCEPTABLE, statusCode);
	}

	@Test
	public void setGetAllNotNull() throws DuplicateKeyException, Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypeList());
		request.setTransactionType("getall");
		ActionType ActionType = new ActionType();
		ActionType.setStatus(false);
		when(actionTypeRepo.findAll()).thenReturn(request.getActionTypeList());
		ResponseEntity<Object> responseEntity = actionTypeFacadeImpl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setGetAllNull() throws DuplicateKeyException, Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypeList());
		request.setTransactionType("getall");
		request.getActionTypeList().get(0).setId(1);
		ResponseEntity<Object> responseEntity = actionTypeFacadeImpl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setGetByIdNotNull() throws DuplicateKeyException, Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypeList());
		request.setTransactionType("getbyid");
		ActionType ActionType = new ActionType();
		ActionType.setId(1);
		when(actionTypeRepo.getOne(1)).thenReturn(ActionType);
		ResponseEntity<Object> responseEntity = actionTypeFacadeImpl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}

	@Test
	public void setGetByIdNull() throws DuplicateKeyException, Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypeList());
		request.setTransactionType("getbyid");
		ActionType ActionType = new ActionType();
		ActionType.setId(null);
		when(actionTypeRepo.getOne(1)).thenReturn(ActionType);
		ResponseEntity<Object> responseEntity = actionTypeFacadeImpl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void setGetByIdMethodNull() throws DuplicateKeyException, Exception {
		ActionTypeRequest request = new ActionTypeRequest();
		request.setActionTypeList(getActionTypeList());
		request.setTransactionType("");
		ActionType ActionType = new ActionType();
		when(actionTypeRepo.getOne(1)).thenReturn(ActionType);
		ResponseEntity<Object> responseEntity = actionTypeFacadeImpl.getAllDetails(request);
		HttpStatus status = responseEntity.getStatusCode();
		assertEquals(HttpStatus.NOT_ACCEPTABLE, status);
	}
}