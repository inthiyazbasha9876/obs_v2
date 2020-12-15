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

import com.ojas.obs.controller.DeliverylocationController;
import com.ojas.obs.facade.DeliverylocationFacade;
import com.ojas.obs.facadeimpl.DeliverylocationFacadeImpl;
import com.ojas.obs.model.DeliveryLocation;
import com.ojas.obs.request.DeliverylocationRequest;
import com.ojas.obs.response.DeliverylocationResponse;
import com.ojas.obs.response.ErrorResponse;

public class DeliverylocationControllerTest
{
	@InjectMocks
	DeliverylocationController deliverylocationcontroller;
	
	@Mock
	DeliverylocationFacade cmsfacadeImpl;
	
	@Spy
	DeliverylocationRequest deliverylocationreq;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	DeliverylocationResponse deliverylocationresponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(deliverylocationresponse, HttpStatus.OK);
	
	@Spy
	DeliveryLocation deliverylocation;
	
	@Before
	public void init() throws Exception 
	{
		deliverylocationcontroller=new DeliverylocationController();
		cmsfacadeImpl = mock(DeliverylocationFacadeImpl.class);
		setCollaborator(deliverylocationcontroller, "cmsfacadeImpl", cmsfacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	
	public List<DeliveryLocation> getDeliverylocationList() {
		List<DeliveryLocation> deliverylist = new ArrayList<DeliveryLocation>();
		DeliveryLocation deliverydatalist = new DeliveryLocation();
		deliverydatalist.setDeliverylocationId(1);
		deliverydatalist.setDeliverylocationName("clientodc");
		
		
		DeliveryLocation deliverydatalist1 = new DeliveryLocation();
		deliverydatalist1.setDeliverylocationId(2);
		deliverydatalist1.setDeliverylocationName("ojasodc");
		
		deliverylist.add(deliverydatalist);
		deliverylist.add(deliverydatalist1);
		return deliverylist;
	}
	
	
	@Test
	public void deliverylocationRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DeliverylocationRequest deliveryrequest=new DeliverylocationRequest();
    	
    	deliveryrequest.setDeliverylocationList(this.getDeliverylocationList());
    	deliveryrequest.setTransactionType(null);
		when(cmsfacadeImpl.saveDetails(deliveryrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = deliverylocationcontroller.saveDetails(deliveryrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void servicecategoryRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DeliverylocationRequest deliveryrequest=new DeliverylocationRequest();
    	deliveryrequest.setDeliverylocationList(this.getDeliverylocationList());
    	deliveryrequest.setTransactionType("save");
		when(cmsfacadeImpl.saveDetails(deliveryrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = deliverylocationcontroller.saveDetails(deliveryrequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	
	@Test
	public void servicecategoryRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DeliverylocationRequest deliveryrequest=new DeliverylocationRequest();
    	deliveryrequest.setDeliverylocationList(this.getDeliverylocationList());
    	deliveryrequest.setTransactionType("update");
		when(cmsfacadeImpl.saveDetails(deliveryrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = deliverylocationcontroller.saveDetails(deliveryrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DeliverylocationRequest deliveryrequest=new DeliverylocationRequest();
    	deliveryrequest.setDeliverylocationList(this.getDeliverylocationList());
    	deliveryrequest.setTransactionType("delete");
		when(cmsfacadeImpl.saveDetails(deliveryrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = deliverylocationcontroller.saveDetails(deliveryrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DeliverylocationRequest deliveryrequest=new DeliverylocationRequest();
    		
    	DeliveryLocation deliverylocation2 = new DeliveryLocation();
    	deliverylocation2.setDeliverylocationName("any cato");
    	deliverylocation2.setStatus(true);
    	
    	List<DeliveryLocation> deliverylocation  = new ArrayList<DeliveryLocation>();
    	deliverylocation.add(deliverylocation2);
    	deliveryrequest.setDeliverylocationList(deliverylocation);
    	deliveryrequest.setTransactionType("save");	
    	
    	when(cmsfacadeImpl.saveDetails(deliveryrequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));   
		ResponseEntity<Object> setBus = deliverylocationcontroller.saveDetails(deliveryrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DeliverylocationRequest deliveryrequest=new DeliverylocationRequest();
    		
    	DeliveryLocation deliverylocation2 = new DeliveryLocation();
    	deliverylocation2.setDeliverylocationName("any cato");
    	deliverylocation2.setStatus(false);
    	
    	List<DeliveryLocation> deliverylocation  = new ArrayList<DeliveryLocation>();
    	deliverylocation.add(deliverylocation2);
    	deliveryrequest.setDeliverylocationList(deliverylocation);
    	deliveryrequest.setTransactionType("save");	
    	when(cmsfacadeImpl.saveDetails(deliveryrequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = deliverylocationcontroller.saveDetails(deliveryrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	
	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DeliverylocationRequest deliveryrequest=new DeliverylocationRequest();
    		
    	DeliveryLocation deliverylocation2 = new DeliveryLocation();
    	deliverylocation2.setDeliverylocationName("data");
    	deliverylocation2.setStatus(false);
    	
    	List<DeliveryLocation> deliverylocation  = new ArrayList<DeliveryLocation>();
    	deliverylocation.add(deliverylocation2);
    	deliveryrequest.setDeliverylocationList(deliverylocation);
    	deliveryrequest.setTransactionType("save");		
    	when(cmsfacadeImpl.saveDetails(deliveryrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = deliverylocationcontroller.saveDetails(deliveryrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DeliverylocationRequest deliveryrequest=new DeliverylocationRequest();
		
    	DeliveryLocation deliverylocation2 = new DeliveryLocation();
    	deliverylocation2.setDeliverylocationName("data");
    	deliverylocation2.setStatus(false);
    	
    	List<DeliveryLocation> deliverylocation  = new ArrayList<DeliveryLocation>();
    	deliverylocation.add(deliverylocation2);
    	deliveryrequest.setDeliverylocationList(deliverylocation);
    	deliveryrequest.setTransactionType("update");	
    	when(cmsfacadeImpl.saveDetails(deliveryrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = deliverylocationcontroller.saveDetails(deliveryrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DeliverylocationRequest deliveryrequest=new DeliverylocationRequest();
    		
    	DeliveryLocation deliverylocation2 = new DeliveryLocation();
    	deliverylocation2.setDeliverylocationName("ss");
    	deliverylocation2.setStatus(false);
    	
    	List<DeliveryLocation> deliverylocation  = new ArrayList<DeliveryLocation>();
    	deliverylocation.add(deliverylocation2);
    	deliveryrequest.setDeliverylocationList(deliverylocation);
    	deliveryrequest.setTransactionType("delete");	
    	when(cmsfacadeImpl.saveDetails(deliveryrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = deliverylocationcontroller.saveDetails(deliveryrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	
	//getTestcases
	
	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DeliverylocationRequest deliveryrequest=new DeliverylocationRequest();
    	
    	deliveryrequest.setDeliverylocationList(this.getDeliverylocationList());
    	deliveryrequest.setTransactionType(null);
		when(cmsfacadeImpl.saveDetails(deliveryrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = deliverylocationcontroller.getDetails(deliveryrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DeliverylocationRequest deliveryrequest=new DeliverylocationRequest();
    
    	deliveryrequest.setDeliverylocationList(this.getDeliverylocationList());
    	deliveryrequest.setTransactionType("getById");
		
    	deliveryrequest.getDeliverylocationList().get(0).getDeliverylocationId();
		
		when(cmsfacadeImpl.getDetails(deliveryrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = deliverylocationcontroller.getDetails(deliveryrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	
	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DeliverylocationRequest deliveryrequest=new DeliverylocationRequest();
    
    	deliveryrequest.setDeliverylocationList(this.getDeliverylocationList());
    	deliveryrequest.setTransactionType("getById");
		
    	deliveryrequest.getDeliverylocationList().get(0).setDeliverylocationId(null);
		
		when(cmsfacadeImpl.getDetails(deliveryrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = deliverylocationcontroller.getDetails(deliveryrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	
	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	DeliverylocationRequest deliveryrequest=new DeliverylocationRequest();
  
    	deliveryrequest.setDeliverylocationList(this.getDeliverylocationList());
    	deliveryrequest.setTransactionType("getAll");	
    	when(cmsfacadeImpl.getDetails(deliveryrequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = deliverylocationcontroller.getDetails(deliveryrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	
}
