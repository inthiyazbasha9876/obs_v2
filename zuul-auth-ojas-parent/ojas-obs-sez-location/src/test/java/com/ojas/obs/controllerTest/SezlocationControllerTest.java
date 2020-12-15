package com.ojas.obs.controllerTest;

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

import com.ojas.obs.controller.SezlocationController;
import com.ojas.obs.facade.SezlocationFacade;
import com.ojas.obs.facadeimpl.SezlocationFacadeImpl;
import com.ojas.obs.model.SezLocation;
import com.ojas.obs.request.SezlocationRequest;
import com.ojas.obs.response.SezlocationResponse;
import com.ojas.obs.response.ErrorResponse;

public class SezlocationControllerTest
{
	@InjectMocks
	SezlocationController sezlocationcontroller;
	
	@Mock
	SezlocationFacade cmsfacadeImpl;
	
	@Spy
	SezlocationRequest sezlocationreq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	SezlocationResponse sezlocationresponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(sezlocationresponse, HttpStatus.OK);
	
	@Spy
	SezLocation sezlocation;
	
	@Before
	public void init() throws Exception 
	{
		sezlocationcontroller=new SezlocationController();
		cmsfacadeImpl = mock(SezlocationFacadeImpl.class);
		setCollaborator(sezlocationcontroller, "cmsfacadeImpl", cmsfacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	
	public List<SezLocation> getSezlocationList() {
		List<SezLocation> sezlist = new ArrayList<SezLocation>();
		SezLocation sezdatalist = new SezLocation();
		sezdatalist.setSezlocationId(1);
		sezdatalist.setSezlocationName("clientodc");
		
		
		SezLocation sezdatalist1 = new SezLocation();
		sezdatalist1.setSezlocationId(2);
		sezdatalist1.setSezlocationName("ojasodc");
		
		sezlist.add(sezdatalist);
		sezlist.add(sezdatalist1);
		return sezlist;
	}
	
	
	@Test
	public void sezlocationRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	SezlocationRequest sezrequest=new SezlocationRequest();
    	
    	sezrequest.setSezlocationList(this.getSezlocationList());
    	sezrequest.setTransactionType(null);
		when(cmsfacadeImpl.saveDetails(sezrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = sezlocationcontroller.saveDetails(sezrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void sezRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	SezlocationRequest sezrequest=new SezlocationRequest();
    	sezrequest.setSezlocationList(this.getSezlocationList());
    	sezrequest.setTransactionType("save");
		when(cmsfacadeImpl.saveDetails(sezrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setsez = sezlocationcontroller.saveDetails(sezrequest, request, response);
		HttpStatus status = setsez.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	
	@Test
	public void sezRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
    	SezlocationRequest sezrequest=new SezlocationRequest();
    	sezrequest.setSezlocationList(this.getSezlocationList());
    	sezrequest.setTransactionType("update");
		when(cmsfacadeImpl.saveDetails(sezrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = sezlocationcontroller.saveDetails(sezrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void sezRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	SezlocationRequest sezrequest=new SezlocationRequest();
    	sezrequest.setSezlocationList(this.getSezlocationList());
    	sezrequest.setTransactionType("delete");
		when(cmsfacadeImpl.saveDetails(sezrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = sezlocationcontroller.saveDetails(sezrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	SezlocationRequest sezrequest=new SezlocationRequest();
    		
    	SezLocation sezlocation2 = new SezLocation();
    	sezlocation2.setSezlocationName("any cato");
    	sezlocation2.setStatus(true);
    	
    	List<SezLocation> sezlocation  = new ArrayList<SezLocation>();
    	sezlocation.add(sezlocation2);
    	sezrequest.setSezlocationList(sezlocation);
    	sezrequest.setTransactionType("save");	
    	
    	when(cmsfacadeImpl.saveDetails(sezrequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));   
		ResponseEntity<Object> setBus = sezlocationcontroller.saveDetails(sezrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	SezlocationRequest sezrequest=new SezlocationRequest();
    		
    	SezLocation sezlocation2 = new SezLocation();
    	sezlocation2.setSezlocationName("any cato");
    	sezlocation2.setStatus(false);
    	
    	List<SezLocation> sezlocation  = new ArrayList<SezLocation>();
    	sezlocation.add(sezlocation2);
    	
    	sezrequest.setSezlocationList(sezlocation);
    	sezrequest.setTransactionType("save");	
    	when(cmsfacadeImpl.saveDetails(sezrequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = sezlocationcontroller.saveDetails(sezrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	
	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	SezlocationRequest sezrequest=new SezlocationRequest();
    		
    	SezLocation sezlocation2 = new SezLocation();
    	sezlocation2.setSezlocationName("data");
    	sezlocation2.setStatus(false);
    	
    	List<SezLocation> sezlocation  = new ArrayList<SezLocation>();
    	sezlocation.add(sezlocation2);
    	sezrequest.setSezlocationList(sezlocation);
    	sezrequest.setTransactionType("save");		
    	when(cmsfacadeImpl.saveDetails(sezrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = sezlocationcontroller.saveDetails(sezrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	SezlocationRequest sezrequest=new SezlocationRequest();
		
    	SezLocation sezlocation2 = new SezLocation();
    	sezlocation2.setSezlocationName("data");
    	sezlocation2.setStatus(false);
    	
    	List<SezLocation> sezlocation  = new ArrayList<SezLocation>();
    	sezlocation.add(sezlocation2);
    	sezrequest.setSezlocationList(sezlocation);
    	sezrequest.setTransactionType("update");	
    	when(cmsfacadeImpl.saveDetails(sezrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = sezlocationcontroller.saveDetails(sezrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	SezlocationRequest sezrequest=new SezlocationRequest();
    		
    	SezLocation sezlocation2 = new SezLocation();
    	sezlocation2.setSezlocationName("ss");
    	sezlocation2.setStatus(false);
    	
    	List<SezLocation> sezlocation  = new ArrayList<SezLocation>();
    	sezlocation.add(sezlocation2);
    	sezrequest.setSezlocationList(sezlocation);
    	sezrequest.setTransactionType("delete");	
    	when(cmsfacadeImpl.saveDetails(sezrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = sezlocationcontroller.saveDetails(sezrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	//getTestcases
	
	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	SezlocationRequest sezrequest=new SezlocationRequest();
    	
    	sezrequest.setSezlocationList(this.getSezlocationList());
    	sezrequest.setTransactionType(null);
		when(cmsfacadeImpl.saveDetails(sezrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = sezlocationcontroller.getDetails(sezrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	SezlocationRequest sezrequest=new SezlocationRequest();
    
    	sezrequest.setSezlocationList(this.getSezlocationList());
    	sezrequest.setTransactionType("getById");
		
    	sezrequest.getSezlocationList().get(0).getSezlocationId();
		
		when(cmsfacadeImpl.getDetails(sezrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = sezlocationcontroller.getDetails(sezrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	
	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	SezlocationRequest sezrequest=new SezlocationRequest();
    
    	sezrequest.setSezlocationList(this.getSezlocationList());
    	sezrequest.setTransactionType("getById");
		
    	sezrequest.getSezlocationList().get(0).setSezlocationId(null);
		
		when(cmsfacadeImpl.getDetails(sezrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = sezlocationcontroller.getDetails(sezrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	SezlocationRequest sezrequest=new SezlocationRequest();
  
    	sezrequest.setSezlocationList(this.getSezlocationList());
    	sezrequest.setTransactionType("getAll");	
    	when(cmsfacadeImpl.getDetails(sezrequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = sezlocationcontroller.getDetails(sezrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	
}
