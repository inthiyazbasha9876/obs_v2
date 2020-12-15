package com.ojas.obs.c2hstatus.controllertest;

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

import com.ojas.obs.c2hstatus.controller.C2HStatusController;
import com.ojas.obs.c2hstatus.facade.C2HStatusFacade;
import com.ojas.obs.c2hstatus.facadeimpl.C2HStatusFacadeImpl;
import com.ojas.obs.c2hstatus.model.C2HStatus;
import com.ojas.obs.c2hstatus.request.C2HStatusRequest;
import com.ojas.obs.c2hstatus.response.C2HStatusResponse;
import com.ojas.obs.c2hstatus.response.ErrorResponse;

public class C2HStatusControllerTest {
	@InjectMocks
	C2HStatusController c2hstatuscontroller;
	
	@Mock
	C2HStatusFacade c2hstatusfacadeImpl;
	
	@Spy
	C2HStatusRequest c2hstatusreq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	C2HStatusResponse c2hstatusresponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(c2hstatusresponse, HttpStatus.OK);
	
	@Spy
	C2HStatus c2hstatus;
	
	@Before
	public void init() throws Exception 
	{
		c2hstatuscontroller=new C2HStatusController();
		c2hstatusfacadeImpl = mock(C2HStatusFacadeImpl.class);
		setCollaborator(c2hstatuscontroller, "c2hstatusfacadeImpl", c2hstatusfacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	
	public List<C2HStatus> getC2HStatusList() {
		List<C2HStatus> c2hlist = new ArrayList<C2HStatus>();
		C2HStatus contact = new C2HStatus();
		contact.setC2hstatusId(1);
		contact.setC2hstatus("clientodc");
		
		
		C2HStatus contact1 = new C2HStatus();
		contact1.setC2hstatusId(2);
		contact1.setC2hstatus("ojasodc");
		
		c2hlist.add(contact);
		c2hlist.add(contact1);
		return c2hlist;
	}
	
	@Test
	public void c2hstatusRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	C2HStatusRequest c2hstatusrequest=new C2HStatusRequest();
    	
    	c2hstatusrequest.setC2hstatuslist(this.getC2HStatusList());
    	c2hstatusrequest.setTransactionType(null);
		when(c2hstatusfacadeImpl.saveC2HStatus(c2hstatusrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = c2hstatuscontroller.saveC2HStatus(c2hstatusrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void servicecategoryRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	C2HStatusRequest c2hstatusrequest=new C2HStatusRequest();
    	
    	c2hstatusrequest.setC2hstatuslist(this.getC2HStatusList());
    	c2hstatusrequest.setTransactionType("save");
		when(c2hstatusfacadeImpl.saveC2HStatus(c2hstatusrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = c2hstatuscontroller.saveC2HStatus(c2hstatusrequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	
	@Test
	public void servicecategoryRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	C2HStatusRequest c2hstatusrequest=new C2HStatusRequest();
    	
    	c2hstatusrequest.setC2hstatuslist(this.getC2HStatusList());
    	c2hstatusrequest.setTransactionType("update");
		when(c2hstatusfacadeImpl.saveC2HStatus(c2hstatusrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = c2hstatuscontroller.saveC2HStatus(c2hstatusrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	C2HStatusRequest c2hstatusrequest=new C2HStatusRequest();
    	
    	c2hstatusrequest.setC2hstatuslist(this.getC2HStatusList());
    	c2hstatusrequest.setTransactionType("delete");
		when(c2hstatusfacadeImpl.saveC2HStatus(c2hstatusrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = c2hstatuscontroller.saveC2HStatus(c2hstatusrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	C2HStatusRequest c2hstatusrequest=new C2HStatusRequest();
    		
    	C2HStatus c2hstatus2 = new C2HStatus();
    	c2hstatus2.setC2hstatus("any cato");
    	c2hstatus2.setStatus(true);
    	
    	List<C2HStatus> c2hlist  = new ArrayList<C2HStatus>();
    	c2hlist.add(c2hstatus2);
    	c2hstatusrequest.setC2hstatuslist(c2hlist);
    	c2hstatusrequest.setTransactionType("save");	
    	
    	when(c2hstatusfacadeImpl.saveC2HStatus(c2hstatusrequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));   
		ResponseEntity<Object> setBus = c2hstatuscontroller.saveC2HStatus(c2hstatusrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	C2HStatusRequest c2hstatusrequest=new C2HStatusRequest();
    		
    	C2HStatus c2hstatus2 = new C2HStatus();
    	c2hstatus2.setC2hstatus("any cato");
    	c2hstatus2.setStatus(false);
    	
    	List<C2HStatus> c2hlist  = new ArrayList<C2HStatus>();
    	c2hlist.add(c2hstatus2);
    	c2hstatusrequest.setC2hstatuslist(c2hlist);
    	c2hstatusrequest.setTransactionType("save");	
    	when(c2hstatusfacadeImpl.saveC2HStatus(c2hstatusrequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = c2hstatuscontroller.saveC2HStatus(c2hstatusrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	C2HStatusRequest c2hstatusrequest=new C2HStatusRequest();
    		
    	C2HStatus c2hstatus2 = new C2HStatus();
    	c2hstatus2.setC2hstatus("data");
    	c2hstatus2.setStatus(false);
    	
    	List<C2HStatus> c2hlist  = new ArrayList<C2HStatus>();
    	c2hlist.add(c2hstatus2);
    	c2hstatusrequest.setC2hstatuslist(c2hlist);
    	c2hstatusrequest.setTransactionType("save");		
    	when(c2hstatusfacadeImpl.saveC2HStatus(c2hstatusrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = c2hstatuscontroller.saveC2HStatus(c2hstatusrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	C2HStatusRequest c2hstatusrequest=new C2HStatusRequest();
		
    	C2HStatus c2hstatus2 = new C2HStatus();
    	c2hstatus2.setC2hstatus("data");
    	c2hstatus2.setStatus(false);
    	
    	List<C2HStatus> c2hlist  = new ArrayList<C2HStatus>();
    	c2hlist.add(c2hstatus2);
    	c2hstatusrequest.setC2hstatuslist(c2hlist);
    	c2hstatusrequest.setTransactionType("update");	
    	when(c2hstatusfacadeImpl.saveC2HStatus(c2hstatusrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = c2hstatuscontroller.saveC2HStatus(c2hstatusrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	C2HStatusRequest c2hstatusrequest=new C2HStatusRequest();
    		
    	C2HStatus c2hstatus2 = new C2HStatus();
    	c2hstatus2.setC2hstatus("ss");
    	c2hstatus2.setStatus(false);
    	
    	List<C2HStatus> c2hlist  = new ArrayList<C2HStatus>();
    	c2hlist.add(c2hstatus2);
    	c2hstatusrequest.setC2hstatuslist(c2hlist);
    	c2hstatusrequest.setTransactionType("delete");	
    	when(c2hstatusfacadeImpl.saveC2HStatus(c2hstatusrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = c2hstatuscontroller.saveC2HStatus(c2hstatusrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	//getTestcases
	
		@Test
		public void getTransactionEmpty() throws SQLException {
			HttpServletRequest request = null;
	    	HttpServletResponse response = null;
	    	C2HStatusRequest c2hstatusrequest=new C2HStatusRequest();
	    	
	    	c2hstatusrequest.setC2hstatuslist(this.getC2HStatusList());
	    	c2hstatusrequest.setTransactionType(null);
			when(c2hstatusfacadeImpl.saveC2HStatus(c2hstatusrequest)).thenReturn(failureResponse);
			ResponseEntity<Object> setservice = c2hstatuscontroller.getC2HStatus(c2hstatusrequest, request, response);
			HttpStatus unitCode = setservice.getStatusCode();
			assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
		}
		
		@Test
		public void getByIdsuccesscheck() throws SQLException {
			HttpServletRequest request = null;
	    	HttpServletResponse response = null;
	    	C2HStatusRequest c2hstatusrequest=new C2HStatusRequest();
	    
	    	c2hstatusrequest.setC2hstatuslist(this.getC2HStatusList());
	    	c2hstatusrequest.setTransactionType("getById");
			
	    	c2hstatusrequest.getC2hstatuslist().get(0).getC2hstatusId();
			
			when(c2hstatusfacadeImpl.getC2HStatus(c2hstatusrequest)).thenReturn(successResponse);
			ResponseEntity<Object> setservice = c2hstatuscontroller.getC2HStatus(c2hstatusrequest, request, response);
			HttpStatus unitCode = setservice.getStatusCode();
			assertEquals(HttpStatus.OK, unitCode);
		}
		
		@Test
		public void getByIdcheck() throws SQLException {
			HttpServletRequest request = null;
	    	HttpServletResponse response = null;
	    	C2HStatusRequest c2hstatusrequest=new C2HStatusRequest();
	    
	    	c2hstatusrequest.setC2hstatuslist(this.getC2HStatusList());
	    	c2hstatusrequest.setTransactionType("getById");
			
	    	c2hstatusrequest.getC2hstatuslist().get(0).setC2hstatusId(null);
			
			when(c2hstatusfacadeImpl.getC2HStatus(c2hstatusrequest)).thenReturn(failureResponse);
			ResponseEntity<Object> setservice = c2hstatuscontroller.getC2HStatus(c2hstatusrequest, request, response);
			HttpStatus unitCode = setservice.getStatusCode();
			assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
		}
		
		@Test
		public void getExceptionTest() throws Exception {
			HttpServletRequest request = null;
	    	HttpServletResponse response = null;
	    	C2HStatusRequest c2hstatusrequest=new C2HStatusRequest();
	  
	    	c2hstatusrequest.setC2hstatuslist(this.getC2HStatusList());
	    	c2hstatusrequest.setTransactionType("getAll");	
	    	when(c2hstatusfacadeImpl.getC2HStatus(c2hstatusrequest)).thenThrow(RuntimeException.class);
	    
			ResponseEntity<Object> setBus = c2hstatuscontroller.getC2HStatus(c2hstatusrequest, request, response);
			HttpStatus unitCode = setBus.getStatusCode();
			assertEquals(HttpStatus.CONFLICT, unitCode);
		}
}
