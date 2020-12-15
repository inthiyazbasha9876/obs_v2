package com.ojas.obs.actionowner.controllertest;

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

import com.ojas.obs.actionowner.controller.ActionOwnerController;
import com.ojas.obs.actionowner.facade.ActionOwnerFacade;
import com.ojas.obs.actionowner.facadeImpl.ActionOwnerFacadeImpl;
import com.ojas.obs.actionowner.model.ActionOwner;
import com.ojas.obs.actionowner.request.ActionOwnerRequest;
import com.ojas.obs.actionowner.response.ActionOwnerResponse;
import com.ojas.obs.actionowner.response.ErrorResponse;

public class ActionOwnerControllerTest {
	@InjectMocks
	ActionOwnerController actionOwnerController;
	
	@Mock
	ActionOwnerFacade actionOwnerFacadeImpl;
	
	@Spy
	ActionOwnerRequest actionOwnerreq;
	
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
	public void init() throws Exception 
	{
		actionOwnerController=new ActionOwnerController();
		actionOwnerFacadeImpl = mock(ActionOwnerFacadeImpl.class);
		setCollaborator(actionOwnerController, "actionOwnerFacadeImpl", actionOwnerFacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	
	public List<ActionOwner> getActionOwnerList() {
		List<ActionOwner> actionOwnerList = new ArrayList<ActionOwner>();
		ActionOwner actionOwner = new ActionOwner();
		actionOwner.setActionownerId(1);
		actionOwner.setActionowner("clientodc");
		
		
		ActionOwner owner = new ActionOwner();
		owner.setActionownerId(2);
		owner.setActionowner("ojasodc");
		
		actionOwnerList.add(actionOwner);
		actionOwnerList.add(owner);
		return actionOwnerList;
	}
	
	
	@Test
	public void actionOwnerRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ActionOwnerRequest actionOwnerRequest=new ActionOwnerRequest();
    	
    	actionOwnerRequest.setActionOwnerList(this.getActionOwnerList());
    	actionOwnerRequest.setTransactionType(null);
		when(actionOwnerFacadeImpl.saveActionOwner(actionOwnerRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = actionOwnerController.saveActionOwner(actionOwnerRequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void servicecategoryRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ActionOwnerRequest actionOwnerRequest=new ActionOwnerRequest();
    	
    	actionOwnerRequest.setActionOwnerList(this.getActionOwnerList());
    	actionOwnerRequest.setTransactionType("save");
		when(actionOwnerFacadeImpl.saveActionOwner(actionOwnerRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = actionOwnerController.saveActionOwner(actionOwnerRequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void servicecategoryRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ActionOwnerRequest actionOwnerRequest=new ActionOwnerRequest();
    	
    	actionOwnerRequest.setActionOwnerList(this.getActionOwnerList());
    	actionOwnerRequest.setTransactionType("update");
		when(actionOwnerFacadeImpl.saveActionOwner(actionOwnerRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = actionOwnerController.saveActionOwner(actionOwnerRequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ActionOwnerRequest actionOwnerRequest=new ActionOwnerRequest();
    	
    	actionOwnerRequest.setActionOwnerList(this.getActionOwnerList());
    	actionOwnerRequest.setTransactionType("delete");
    	when(actionOwnerFacadeImpl.saveActionOwner(actionOwnerRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = actionOwnerController.saveActionOwner(actionOwnerRequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	
	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ActionOwnerRequest actionRequest=new ActionOwnerRequest();
    		
    	ActionOwner actionOwner = new ActionOwner();
    	actionOwner.setActionowner("any cato");
    	actionOwner.setStatus(true);
    	
    	List<ActionOwner> list = new ArrayList<ActionOwner>();
    	list.add(actionOwner);
    	actionRequest.setActionOwnerList(list);
    	actionRequest.setTransactionType("save");	
    	
    	when(actionOwnerFacadeImpl.saveActionOwner(actionRequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));   
		ResponseEntity<Object> setBus = actionOwnerController.saveActionOwner(actionRequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ActionOwnerRequest actionRequest=new ActionOwnerRequest();
    		
    	ActionOwner actionOwner = new ActionOwner();
    	actionOwner.setActionowner("any cato");
    	actionOwner.setStatus(false);
    	
    	List<ActionOwner> list = new ArrayList<ActionOwner>();
    	list.add(actionOwner);
    	actionRequest.setActionOwnerList(list);
    	actionRequest.setTransactionType("save");	
    	when(actionOwnerFacadeImpl.saveActionOwner(actionRequest)).thenThrow(RuntimeException.class);
    	ResponseEntity<Object> setBus = actionOwnerController.saveActionOwner(actionRequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	
	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ActionOwnerRequest actionRequest=new ActionOwnerRequest();
    		
    	ActionOwner actionOwner = new ActionOwner();
    	actionOwner.setActionowner("data");
    	actionOwner.setStatus(false);
    	
    	List<ActionOwner> list  = new ArrayList<ActionOwner>();
    	list.add(actionOwner);
    	actionRequest.setActionOwnerList(list);
    	actionRequest.setTransactionType("save");		
    	when(actionOwnerFacadeImpl.saveActionOwner(actionRequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = actionOwnerController.saveActionOwner(actionRequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ActionOwnerRequest actionRequest=new ActionOwnerRequest();
		
    	ActionOwner actionOwner = new ActionOwner();
    	actionOwner.setActionowner("data");
    	actionOwner.setStatus(false);
    	
    	List<ActionOwner> list  = new ArrayList<ActionOwner>();
    	list.add(actionOwner);
    	actionRequest.setActionOwnerList(list);
    	actionRequest.setTransactionType("update");	
    	when(actionOwnerFacadeImpl.saveActionOwner(actionRequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = actionOwnerController.saveActionOwner(actionRequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ActionOwnerRequest actionrequest=new ActionOwnerRequest();
    		
    	ActionOwner actionOwner = new ActionOwner();
    	actionOwner.setActionowner("ss");
    	actionOwner.setStatus(false);
    	
    	List<ActionOwner> list  = new ArrayList<ActionOwner>();
    	list.add(actionOwner);
    	actionrequest.setActionOwnerList(list);
    	actionrequest.setTransactionType("delete");	
    	when(actionOwnerFacadeImpl.saveActionOwner(actionrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = actionOwnerController.saveActionOwner(actionrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	//getTestcases
	
	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ActionOwnerRequest actionRequest=new ActionOwnerRequest();
    	
    	actionRequest.setActionOwnerList(this.getActionOwnerList());
    	actionRequest.setTransactionType(null);
		when(actionOwnerFacadeImpl.saveActionOwner(actionRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = actionOwnerController.getActionOwner(actionRequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ActionOwnerRequest actionRequest=new ActionOwnerRequest();
    
    	actionRequest.setActionOwnerList(this.getActionOwnerList());
    	actionRequest.setTransactionType("getById");
		
    	actionRequest.getActionOwnerList().get(0).getActionownerId();
		
		when(actionOwnerFacadeImpl.getActionOwner(actionRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = actionOwnerController.getActionOwner(actionRequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ActionOwnerRequest actionRequest=new ActionOwnerRequest();
    
    	actionRequest.setActionOwnerList(this.getActionOwnerList());
    	actionRequest.setTransactionType("getById");
		
    	actionRequest.getActionOwnerList().get(0).setActionownerId(null);
		
		when(actionOwnerFacadeImpl.getActionOwner(actionRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = actionOwnerController.getActionOwner(actionRequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ActionOwnerRequest actionRequest=new ActionOwnerRequest();
  
    	actionRequest.setActionOwnerList(this.getActionOwnerList());
    	actionRequest.setTransactionType("getAll");	
    	when(actionOwnerFacadeImpl.getActionOwner(actionRequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = actionOwnerController.getActionOwner(actionRequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
}

   
