package com.ojas.obs.actionowner.facadetest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.actionowner.facade.ActionOwnerFacade;
import com.ojas.obs.actionowner.facadeImpl.ActionOwnerFacadeImpl;
import com.ojas.obs.actionowner.model.ActionOwner;
import com.ojas.obs.actionowner.repository.ActionOwnerRepository;
import com.ojas.obs.actionowner.request.ActionOwnerRequest;
import com.ojas.obs.actionowner.response.ActionOwnerResponse;
import com.ojas.obs.actionowner.response.ErrorResponse;

public class ActionOwnerFacadeImplTest {
	@InjectMocks
	ActionOwnerFacadeImpl actionOwnerFacadeImpl;

	@Mock
	ActionOwnerRepository actionOwnerRepo;

	@Mock
	ActionOwnerFacade actionOwnerFacade;

	@Spy
	ActionOwnerRequest actionOwnerReq;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	ActionOwnerResponse actionOwnerResponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(actionOwnerResponse, HttpStatus.OK);

	@Spy
	ActionOwner actionOwner;

	@Before
	public void init() throws Exception {
		actionOwnerFacadeImpl = new ActionOwnerFacadeImpl();
		actionOwnerRepo = mock(ActionOwnerRepository.class);
		setCollaborator(actionOwnerFacadeImpl, "actionOwnerRepo", actionOwnerRepo);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<ActionOwner> getActionOwnerList() {
		List<ActionOwner> ownerList = new ArrayList<ActionOwner>();
		ActionOwner actionOwner = new ActionOwner();
		actionOwner.setActionownerId(1);
		actionOwner.setActionowner("clientodc");

		ActionOwner actionOwner1 = new ActionOwner();
		actionOwner1.setActionownerId(2);
		actionOwner1.setActionowner("ojasodc");

		ownerList.add(actionOwner);
		ownerList.add(actionOwner1);
		return ownerList;
	}

	@Test
	public void testSaveError() throws SQLException {

		ActionOwnerRequest actionOwnerReq = new ActionOwnerRequest();

		actionOwnerReq.setTransactionType("save");

		actionOwnerReq.setActionOwnerList(this.getActionOwnerList());

		ActionOwner actionOwner = new ActionOwner();
		List<ActionOwner> ownerList = new ArrayList<ActionOwner>();
		ownerList.add(actionOwner);
		actionOwnerReq.setActionOwnerList(ownerList);


		when(actionOwnerRepo.save(actionOwner)).thenReturn(actionOwner);

		ResponseEntity<Object> saveStatus = actionOwnerFacadeImpl.saveActionOwner(actionOwnerReq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	
	}

	@Test
	public void testSavesuccescheck() throws SQLException {

		ActionOwnerRequest actionOwnerReq = new ActionOwnerRequest();

		actionOwnerReq.setTransactionType("save");

		actionOwnerReq.setActionOwnerList(this.getActionOwnerList());

		ActionOwner actionOwner = new ActionOwner();
		actionOwner.setActionownerId(1);
		actionOwner.setActionowner("cat");

		List<ActionOwner> ownerList = new ArrayList<ActionOwner>();
		ownerList.add(actionOwner);
		actionOwnerReq.setActionOwnerList(ownerList);

		when(actionOwnerRepo.save(actionOwner)).thenReturn(actionOwner);

		ResponseEntity<Object> saveStatus = actionOwnerFacadeImpl.saveActionOwner(actionOwnerReq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testupdatesuccesscheck() throws SQLException {

		ActionOwnerRequest actionOwnerReq = new ActionOwnerRequest();

		actionOwnerReq.setTransactionType("update");

		actionOwnerReq.setActionOwnerList(this.getActionOwnerList());

		ActionOwner actionOwner = new ActionOwner();
		actionOwner.setActionownerId(1);
		actionOwner.setActionowner("cat");

		List<ActionOwner> ownerList = new ArrayList<ActionOwner>();
		ownerList.add(actionOwner);
		actionOwnerReq.setActionOwnerList(ownerList);

		Integer id = actionOwnerReq.getActionOwnerList().get(0).getActionownerId();

		when(actionOwnerRepo.findById(id)).thenReturn(Optional.of(actionOwner));

		ResponseEntity<Object> saveStatus = actionOwnerFacadeImpl.saveActionOwner(actionOwnerReq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testupdateErrorcheck() throws SQLException {

		ActionOwnerRequest actionOwnerReq = new ActionOwnerRequest();

		actionOwnerReq.setTransactionType("update");

		actionOwnerReq.setActionOwnerList(this.getActionOwnerList());

		ActionOwner actionOwner = new ActionOwner();
		actionOwner.setActionownerId(null);
		actionOwner.setActionowner("cat");

		List<ActionOwner> ownerList = new ArrayList<ActionOwner>();
		ownerList.add(actionOwner);
		actionOwnerReq.setActionOwnerList(ownerList);

		Integer id = actionOwnerReq.getActionOwnerList().get(0).getActionownerId();

		when(actionOwnerRepo.findById(id)).thenReturn(Optional.of(actionOwner));

		ResponseEntity<Object> saveStatus = actionOwnerFacadeImpl.saveActionOwner(actionOwnerReq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void testdeletesuccesscheck() throws SQLException {

		ActionOwnerRequest actionOwnerReq = new ActionOwnerRequest();

		actionOwnerReq.setTransactionType("delete");

		actionOwnerReq.setActionOwnerList(this.getActionOwnerList());

		ActionOwner actionOwner = new ActionOwner();
		actionOwner.setActionownerId(1);
		actionOwner.setActionowner("cat");

		List<ActionOwner> ownerList = new ArrayList<ActionOwner>();
		ownerList.add(actionOwner);
		actionOwnerReq.setActionOwnerList(ownerList);

		Integer id = actionOwnerReq.getActionOwnerList().get(0).getActionownerId();

		when(actionOwnerRepo.getOne(id)).thenReturn(actionOwner);

		actionOwner.setStatus(actionOwner.getStatus() == null);

		when(actionOwnerRepo.save(actionOwner)).thenReturn(actionOwner);

		ResponseEntity<Object> saveStatus = actionOwnerFacadeImpl.saveActionOwner(actionOwnerReq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testdeleteErrorcheck() throws SQLException {

		ActionOwnerRequest actionOwnerReq = new ActionOwnerRequest();

		actionOwnerReq.setTransactionType("delete");

		actionOwnerReq.setActionOwnerList(this.getActionOwnerList());

		ActionOwner actionOwner = new ActionOwner();
		actionOwner.setActionownerId(null);
		actionOwner.setActionowner(null);
		actionOwner.setStatus(null);

		List<ActionOwner> ownerList = new ArrayList<ActionOwner>();
		ownerList.add(actionOwner);
		actionOwnerReq.setActionOwnerList(ownerList);


		Integer id = actionOwnerReq.getActionOwnerList().get(0).getActionownerId();

		when(actionOwnerRepo.getOne(id)).thenReturn(actionOwner);

		actionOwner.setStatus(actionOwner.getStatus() != null);

		when(actionOwnerRepo.save(actionOwner)).thenReturn(actionOwner);

		ResponseEntity<Object> saveStatus = actionOwnerFacadeImpl.saveActionOwner(actionOwnerReq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	
	@Test
	public void TestElseError() throws SQLException {

		ActionOwnerRequest actionOwnerReq = new ActionOwnerRequest();

		actionOwnerReq.setTransactionType("ss");

		actionOwnerReq.setActionOwnerList(this.getActionOwnerList());

		ActionOwner actionOwner = new ActionOwner();

		when(actionOwnerRepo.save(actionOwner)).thenReturn(actionOwner);

		ResponseEntity<Object> saveStatus = actionOwnerFacadeImpl.saveActionOwner(actionOwnerReq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	// getTestcases

	@Test
	public void getAllSuccess() throws SQLException {

		ActionOwnerRequest actionOwnerReq = new ActionOwnerRequest();

		actionOwnerReq.setActionOwnerList(this.getActionOwnerList());

		actionOwnerReq.setTransactionType("getAll");

		ActionOwner actionOwner = new ActionOwner();

		List<ActionOwner> ownerList = new ArrayList<ActionOwner>();
		ownerList.add(actionOwner);
		actionOwnerReq.setActionOwnerList(ownerList);

		when(actionOwnerRepo.findAll()).thenReturn(ownerList);

		ResponseEntity<Object> saveStatus = actionOwnerFacadeImpl.getActionOwner(actionOwnerReq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getAllError() throws SQLException {

		ActionOwnerRequest actionOwnerReq = new ActionOwnerRequest();

		actionOwnerReq.setActionOwnerList(this.getActionOwnerList());

		actionOwnerReq.setTransactionType("getAll");

		ActionOwner actionOwner = new ActionOwner();

		List<ActionOwner> ownerList = new ArrayList<ActionOwner>();
		ownerList.add(actionOwner);
		actionOwnerReq.setActionOwnerList(ownerList);

		when(actionOwnerRepo.findAll()).thenReturn(ownerList);
		
		ResponseEntity<Object> saveStatus = actionOwnerFacadeImpl.getActionOwner(actionOwnerReq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getByIdError() throws SQLException {

		ActionOwnerRequest actionOwnerReq = new ActionOwnerRequest();

		actionOwnerReq.setActionOwnerList(this.getActionOwnerList());

		ActionOwner actionOwner = new ActionOwner();
		actionOwner.setActionownerId(1);

		List<ActionOwner> ownerList = new ArrayList<ActionOwner>();
		ownerList.add(actionOwner);
		actionOwnerReq.setActionOwnerList(ownerList);

		actionOwnerReq.setTransactionType("getById");
		Integer id = ownerList.get(0).getActionownerId();

		when(actionOwnerRepo.findAll()).thenReturn(ownerList);

		ResponseEntity<Object> saveStatus = actionOwnerFacadeImpl.getActionOwner(actionOwnerReq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getByIdSuccess() throws SQLException {

		ActionOwnerRequest actionOwnerReq = new ActionOwnerRequest();

		actionOwnerReq.setActionOwnerList(this.getActionOwnerList());

		ActionOwner actionOwner = new ActionOwner();
		actionOwner.setActionownerId(1);

		List<ActionOwner> ownerList = new ArrayList<ActionOwner>();
		ownerList.add(actionOwner);
		actionOwnerReq.setActionOwnerList(ownerList);

		actionOwnerReq.setTransactionType("getById");
		Integer id = ownerList.get(0).getActionownerId();

		when(actionOwnerRepo.findById(id)).thenReturn(Optional.of(actionOwner));
		ownerList.add(actionOwner);

		ResponseEntity<Object> saveStatus = actionOwnerFacadeImpl.getActionOwner(actionOwnerReq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

}
