package com.ojas.obs.permstatus.controllertest;

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

import com.ojas.obs.permstatus.controller.PermStatusController;
import com.ojas.obs.permstatus.facade.PermStatusFacade;
import com.ojas.obs.permstatus.facadeimpl.PermStatusFacadeImpl;
import com.ojas.obs.permstatus.model.PermStatus;
import com.ojas.obs.permstatus.request.PermStatusRequest;
import com.ojas.obs.permstatus.response.ErrorResponse;
import com.ojas.obs.permstatus.response.PermStatusResponse;

public class PermStatusControllerTest {
	@InjectMocks
	PermStatusController permStatusController;
	
	@Mock
	PermStatusFacade permStatusFacadeImpl;
	
	@Spy
	PermStatusRequest permStatusReq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	PermStatusResponse permStatusResponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(permStatusResponse, HttpStatus.OK);
	
	@Spy
	PermStatus permStatus;
	
	@Before
	public void init() throws Exception 
	{
		permStatusController=new PermStatusController();
		permStatusFacadeImpl = mock(PermStatusFacadeImpl.class);
		setCollaborator(permStatusController, "permStatusFacadeImpl", permStatusFacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	
	public List<PermStatus> getpermStatusList() {
		List<PermStatus>permStatusList = new ArrayList<PermStatus>();
		PermStatus permStatus = new PermStatus();
		permStatus.setPermstatusId(1);
		permStatus.setPermstatus("clientodc");
		
		
		PermStatus permStatus1 = new PermStatus();
		permStatus1.setPermstatusId(2);
		permStatus1.setPermstatus("ojasodc");
		
		permStatusList.add(permStatus);
		permStatusList.add(permStatus1);
		return permStatusList;
	}
	
	@Test
	public void permStatusRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	PermStatusRequest permStatusRequest=new PermStatusRequest();
    	
    	permStatusRequest.setPermStatusList(this.getpermStatusList());
    	permStatusRequest.setTransactionType(null);
		when(permStatusFacadeImpl.savePermStatus(permStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = permStatusController.savePermStatus(permStatusRequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void servicecategoryRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	PermStatusRequest permStatusRequest=new PermStatusRequest();
    	
    	permStatusRequest.setPermStatusList(this.getpermStatusList());
    	permStatusRequest.setTransactionType("save");
		when(permStatusFacadeImpl.savePermStatus(permStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = permStatusController.savePermStatus(permStatusRequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	
	@Test
	public void servicecategoryRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	PermStatusRequest permStatusRequest=new PermStatusRequest();
    	
    	permStatusRequest.setPermStatusList(this.getpermStatusList());
    	permStatusRequest.setTransactionType("update");
		when(permStatusFacadeImpl.savePermStatus(permStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = permStatusController.savePermStatus(permStatusRequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	PermStatusRequest permStatusRequest=new PermStatusRequest();
    	
    	permStatusRequest.setPermStatusList(this.getpermStatusList());
    	permStatusRequest.setTransactionType("delete");
		when(permStatusFacadeImpl.savePermStatus(permStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = permStatusController.savePermStatus(permStatusRequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	
	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	PermStatusRequest permStatusRequest=new PermStatusRequest();
    		
    	PermStatus permStatus2 = new PermStatus();
    	permStatus2.setPermstatus("any cato");
    	permStatus2.setStatus(true);
    	
    	List<PermStatus> permStatusList  = new ArrayList<PermStatus>();
    	permStatusList.add(permStatus2);
    	permStatusRequest.setPermStatusList(permStatusList);
    	permStatusRequest.setTransactionType("save");	
    	
    	when(permStatusFacadeImpl.savePermStatus(permStatusRequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));   
		ResponseEntity<Object> setBus = permStatusController.savePermStatus(permStatusRequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	PermStatusRequest deliveryrequest=new PermStatusRequest();
    		
    	PermStatus permStatus2 = new PermStatus();
    	permStatus2.setPermstatus("any cato");
    	permStatus2.setStatus(false);
    	
    	List<PermStatus> permStatusList  = new ArrayList<PermStatus>();
    	permStatusList.add(permStatus2);
    	deliveryrequest.setPermStatusList(permStatusList);
    	deliveryrequest.setTransactionType("save");	
    	when(permStatusFacadeImpl.savePermStatus(deliveryrequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = permStatusController.savePermStatus(deliveryrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	PermStatusRequest permStatusRequest=new PermStatusRequest();
    		
    	PermStatus permStatus = new PermStatus();
    	permStatus.setPermstatus("data");
    	permStatus.setStatus(false);
    	
    	List<PermStatus> permStatusList  = new ArrayList<PermStatus>();
    	permStatusList.add(permStatus);
    	permStatusRequest.setPermStatusList(permStatusList);
    	permStatusRequest.setTransactionType("save");		
    	when(permStatusFacadeImpl.savePermStatus(permStatusRequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = permStatusController.savePermStatus(permStatusRequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	PermStatusRequest permStatusRequest=new PermStatusRequest();
		
    	PermStatus permStatus = new PermStatus();
    	permStatus.setPermstatus("data");
    	permStatus.setStatus(false);
    	
    	List<PermStatus> permStatusList  = new ArrayList<PermStatus>();
    	permStatusList.add(permStatus);
    	permStatusRequest.setPermStatusList(permStatusList);
    	permStatusRequest.setTransactionType("update");	
    	when(permStatusFacadeImpl.savePermStatus(permStatusRequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = permStatusController.savePermStatus(permStatusRequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	PermStatusRequest permStatusRequest=new PermStatusRequest();
    		
    	PermStatus permStatus = new PermStatus();
    	permStatus.setPermstatus("ss");
    	permStatus.setStatus(false);
    	
    	List<PermStatus> permStatusList  = new ArrayList<PermStatus>();
    	permStatusList.add(permStatus);
    	permStatusRequest.setPermStatusList(permStatusList);
    	permStatusRequest.setTransactionType("delete");	
    	when(permStatusFacadeImpl.savePermStatus(permStatusRequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = permStatusController.savePermStatus(permStatusRequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	

	//getTestcases
	
	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	PermStatusRequest permStatusRequest=new PermStatusRequest();
    	
    	permStatusRequest.setPermStatusList(this.getpermStatusList());
    	permStatusRequest.setTransactionType(null);
		when(permStatusFacadeImpl.savePermStatus(permStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = permStatusController.getPermStatus(permStatusRequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	PermStatusRequest permStatusRequest=new PermStatusRequest();
    
    	permStatusRequest.setPermStatusList(this.getpermStatusList());
    	permStatusRequest.setTransactionType("getById");
		
    	permStatusRequest.getPermStatusList().get(0).getPermstatusId();
		
		when(permStatusFacadeImpl.getPermStatus(permStatusRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = permStatusController.getPermStatus(permStatusRequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	PermStatusRequest permStatusRequest=new PermStatusRequest();
    
    	permStatusRequest.setPermStatusList(this.getpermStatusList());
    	permStatusRequest.setTransactionType("getById");
		
    	permStatusRequest.getPermStatusList().get(0).setPermstatusId(null);
		
		when(permStatusFacadeImpl.getPermStatus(permStatusRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = permStatusController.getPermStatus(permStatusRequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	PermStatusRequest permStatusRequest=new PermStatusRequest();
  
    	permStatusRequest.setPermStatusList(this.getpermStatusList());
    	permStatusRequest.setTransactionType("getAll");	
    	when(permStatusFacadeImpl.getPermStatus(permStatusRequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = permStatusController.getPermStatus(permStatusRequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
}