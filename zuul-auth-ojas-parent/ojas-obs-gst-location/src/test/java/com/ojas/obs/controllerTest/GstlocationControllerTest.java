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

import com.ojas.obs.controller.GstlocationController;
import com.ojas.obs.facade.GstlocationFacade;
import com.ojas.obs.facadeimpl.GstlocationFacadeImpl;
import com.ojas.obs.model.GstLocation;
import com.ojas.obs.request.GstlocationRequest;
import com.ojas.obs.response.GstlocationResponse;
import com.ojas.obs.response.ErrorResponse;

public class GstlocationControllerTest
{
	@InjectMocks
	GstlocationController gstlocationcontroller;
	
	@Mock
	GstlocationFacade cmsfacadeImpl;
	
	@Spy
	GstlocationRequest gstlocationreq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	GstlocationResponse gstlocationresponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(gstlocationresponse, HttpStatus.OK);
	
	@Spy
	GstLocation gstlocation;
	
	@Before
	public void init() throws Exception 
	{
		gstlocationcontroller=new GstlocationController();
		cmsfacadeImpl = mock(GstlocationFacadeImpl.class);
		setCollaborator(gstlocationcontroller, "cmsfacadeImpl", cmsfacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	
	public List<GstLocation> getGstlocationList() 
	{
		List<GstLocation> gstlist = new ArrayList<GstLocation>();
		GstLocation gstdatalist = new GstLocation();
		gstdatalist.setGstlocationId(1);
		gstdatalist.setGstlocationName("clientodc");
		
		
		GstLocation gstdatalist1 = new GstLocation();
		gstdatalist1.setGstlocationId(2);
		gstdatalist1.setGstlocationName("ojasodc");
		
		gstlist.add(gstdatalist);
		gstlist.add(gstdatalist1);
		return gstlist;
	}
	
	
	@Test
	public void gstlocationRequestNullTest() throws SQLException 
	{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	GstlocationRequest gstrequest=new GstlocationRequest();
    	
    	gstrequest.setGstlocationList(this.getGstlocationList());
    	gstrequest.setTransactionType(null);
    	
		when(cmsfacadeImpl.saveDetails(gstrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = gstlocationcontroller.saveDetails(gstrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void gstRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	GstlocationRequest gstrequest=new GstlocationRequest();
    	gstrequest.setGstlocationList(this.getGstlocationList());
    	gstrequest.setTransactionType("save");
		when(cmsfacadeImpl.saveDetails(gstrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setsez = gstlocationcontroller.saveDetails(gstrequest, request, response);
		HttpStatus status = setsez.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	
	@Test
	public void gstRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
    	GstlocationRequest gstrequest=new GstlocationRequest();
    	gstrequest.setGstlocationList(this.getGstlocationList());
    	gstrequest.setTransactionType("update");
		when(cmsfacadeImpl.saveDetails(gstrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = gstlocationcontroller.saveDetails(gstrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void gstRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
    	GstlocationRequest gstrequest=new GstlocationRequest();
    	gstrequest.setGstlocationList(this.getGstlocationList());
    	gstrequest.setTransactionType("delete");
		when(cmsfacadeImpl.saveDetails(gstrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = gstlocationcontroller.saveDetails(gstrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	GstlocationRequest gstrequest=new GstlocationRequest();
    		
    	GstLocation gstlocation2 = new GstLocation();
    	gstlocation2.setGstlocationName("any cato");
    	gstlocation2.setStatus(true);
    	
    	List<GstLocation> gstlocation  = new ArrayList<GstLocation>();
    	gstlocation.add(gstlocation2);
    	gstrequest.setGstlocationList(gstlocation);
    	gstrequest.setTransactionType("save");	
    	
    	when(cmsfacadeImpl.saveDetails(gstrequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));   
		ResponseEntity<Object> setBus = gstlocationcontroller.saveDetails(gstrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	GstlocationRequest gstrequest=new GstlocationRequest();
    		
    	GstLocation gstlocation2 = new GstLocation();
    	gstlocation2.setGstlocationName("any cato");
    	gstlocation2.setStatus(false);
    	
    	List<GstLocation> gstlocation  = new ArrayList<GstLocation>();
    	gstlocation.add(gstlocation2);
    	
    	gstrequest.setGstlocationList(gstlocation);
    	gstrequest.setTransactionType("save");	
    	when(cmsfacadeImpl.saveDetails(gstrequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = gstlocationcontroller.saveDetails(gstrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	
	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	GstlocationRequest gstrequest=new GstlocationRequest();
    		
    	GstLocation gstlocation2 = new GstLocation();
    	gstlocation2.setGstlocationName("data");
    	gstlocation2.setStatus(false);
    	
    	List<GstLocation> gstlocation  = new ArrayList<GstLocation>();
    	gstlocation.add(gstlocation2);
    	gstrequest.setGstlocationList(gstlocation);
    	gstrequest.setTransactionType("save");		
    	when(cmsfacadeImpl.saveDetails(gstrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = gstlocationcontroller.saveDetails(gstrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	GstlocationRequest gstrequest=new GstlocationRequest();
		
    	GstLocation gstlocation2 = new GstLocation();
    	gstlocation2.setGstlocationName("data");
    	gstlocation2.setStatus(false);
    	
    	List<GstLocation> gstlocation  = new ArrayList<GstLocation>();
    	gstlocation.add(gstlocation2);
    	gstrequest.setGstlocationList(gstlocation);
    	gstrequest.setTransactionType("update");	
    	when(cmsfacadeImpl.saveDetails(gstrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = gstlocationcontroller.saveDetails(gstrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	GstlocationRequest gstrequest=new GstlocationRequest();
    		
    	GstLocation gstlocation2 = new GstLocation();
    	gstlocation2.setGstlocationName("ss");
    	gstlocation2.setStatus(false);
    	
    	List<GstLocation> gstlocation  = new ArrayList<GstLocation>();
    	gstlocation.add(gstlocation2);
    	gstrequest.setGstlocationList(gstlocation);
    	gstrequest.setTransactionType("delete");	
    	when(cmsfacadeImpl.saveDetails(gstrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = gstlocationcontroller.saveDetails(gstrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	
	//getTestcases
	
	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	GstlocationRequest gstrequest=new GstlocationRequest();
    	
    	gstrequest.setGstlocationList(this.getGstlocationList());
    	gstrequest.setTransactionType(null);
		when(cmsfacadeImpl.saveDetails(gstrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = gstlocationcontroller.getDetails(gstrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	GstlocationRequest gstrequest=new GstlocationRequest();
    
    	gstrequest.setGstlocationList(this.getGstlocationList());
    	gstrequest.setTransactionType("getById");
		
    	gstrequest.getGstlocationList().get(0).getGstlocationId();
		
		when(cmsfacadeImpl.getDetails(gstrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = gstlocationcontroller.getDetails(gstrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	
	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	GstlocationRequest gstrequest=new GstlocationRequest();
    
    	gstrequest.setGstlocationList(this.getGstlocationList());
    	gstrequest.setTransactionType("getById");
		
    	gstrequest.getGstlocationList().get(0).setGstlocationId(null);
		
		when(cmsfacadeImpl.getDetails(gstrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = gstlocationcontroller.getDetails(gstrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	GstlocationRequest gstrequest=new GstlocationRequest();
  
    	gstrequest.setGstlocationList(this.getGstlocationList());
    	gstrequest.setTransactionType("getAll");	
    	when(cmsfacadeImpl.getDetails(gstrequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = gstlocationcontroller.getDetails(gstrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	
}
